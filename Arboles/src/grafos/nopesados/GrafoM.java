package grafos.nopesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GrafoM<G extends Comparable<G>>{
    protected List<G> listaDeVertices;
    protected List<List<Boolean>> matrizDeAdyacencias;
    public static final int NRO_DE_VERTICE_INVALIDO = -1;

    public GrafoM(){
        listaDeVertices=new ArrayList<>();
        matrizDeAdyacencias =new ArrayList<>();
    }

    public GrafoM(Iterable<G> vertices){
        this();
        for(G vertice : listaDeVertices){
            insertarVertice(vertice);
        }
    }

    public int nroVertice(G vertice){
        for(int i=0;i<listaDeVertices.size();i++){
            G verticeEnTurno= listaDeVertices.get(i);
            if(vertice.compareTo(verticeEnTurno)==0){
                return i;
            }
        }
        return NRO_DE_VERTICE_INVALIDO;
    }

    public void validarVertice(G vertice){
        int nroDelVertice=nroVertice(vertice);
        if(nroDelVertice==NRO_DE_VERTICE_INVALIDO){
            throw new IllegalArgumentException("Vértice no pertence al grafo");
        }
    }

    public void insertarVertice(G vertice) {
        validarVertice(vertice);
        listaDeVertices.add(vertice);
        for(List<Boolean> filaDeAdyacencias:matrizDeAdyacencias){
            filaDeAdyacencias.add(Boolean.FALSE);
        }
        matrizDeAdyacencias.add(new ArrayList<>());
        List<Boolean> nuevaFila=matrizDeAdyacencias.getLast();
        for(int i=0; i<listaDeVertices.size();i++){
            nuevaFila.add(Boolean.FALSE);
        }
    }

    public int cantidadDeVertices(){
        return listaDeVertices.size();
    }

    public int cantidadDeAristas(){
        int contador=0;
        for(int i=0;i<matrizDeAdyacencias.size();i++){
            for(int j=i;i<matrizDeAdyacencias.size();j++){
                if(matrizDeAdyacencias.get(i).get(j)){
                    contador++;
                }
            }
        }
        return contador;
    }

    public Iterable<G> getVertices(){
        return listaDeVertices;
    }

    public Iterable<G> getAdyacentesDelVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        List<Boolean> adyacentesDelVerticeXNro=matrizDeAdyacencias.get(nroDelVertice);
        List<G> listaDeAdyacentesDelVertice=new ArrayList<>();
        for(int i=0;i<adyacentesDelVerticeXNro.size();i++){
            if(adyacentesDelVerticeXNro.get(i)){
                listaDeAdyacentesDelVertice.add(listaDeVertices.get(i));
            }
        }
        return listaDeAdyacentesDelVertice;
    }

    public boolean existeAdyacencia(G verticeOrigen, G verticeDestino){
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        return matrizDeAdyacencias.get(nroDelVerticeOrigen).get(nroDelVerticeDestino);
    }

    public void insertarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaYaExiste{
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        matrizDeAdyacencias.get(nroDelVerticeOrigen).set(nroDelVerticeDestino,Boolean.TRUE);
        if(nroDelVerticeOrigen!=nroDelVerticeDestino){
            matrizDeAdyacencias.get(nroDelVerticeDestino).set(nroDelVerticeOrigen,Boolean.TRUE);
        }
    }

    public void eliminarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaNoExiste {

    }

    public void eliminarVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        listaDeVertices.remove(nroDelVertice);
        matrizDeAdyacencias.remove(nroDelVertice);
        for(List<Boolean> adyacentesDeUnVertice: matrizDeAdyacencias){
            adyacentesDeUnVertice.remove(nroDelVertice);
        }
    }

    public int gradoDelVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        List<Boolean> adyacentesDelVertice= matrizDeAdyacencias.get(nroDelVertice);
        int grado=0;
        for(Boolean adyacente:adyacentesDelVertice){
            if(adyacente) grado++;
        }
        return grado;
    }

}
