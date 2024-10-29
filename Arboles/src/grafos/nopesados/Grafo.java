package grafos.nopesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.*;

public class Grafo <G extends Comparable<G>>{
    protected List<G> listaDeVertices;
    protected List<List<Integer>> listasDeAdyacencias;
    public static final int NRO_DE_VERTICE_INVALIDO = -1;

    public Grafo(){
        listaDeVertices=new ArrayList<G>();
        listasDeAdyacencias=new ArrayList<>();
    }

    public Grafo(Iterable<G> vertices){
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
        listasDeAdyacencias.add(new ArrayList<>());
    }

    public int cantidadDeVertices(){
        return listaDeVertices.size();
    }

    public int cantidadDeAristas(){
        int contador=0;
        Iterable<G> listaDeVertices=getVertices();
        for(G verticeEnTurno:listaDeVertices){
            Iterable<G> adyacentesEnTurno=getAdyacentesDelVertice(verticeEnTurno);
            for (G adyacente:adyacentesEnTurno){
                contador++;
                if(verticeEnTurno.compareTo(adyacente)==0){
                    contador++;
                }
            }
        }
        return contador/2;
    }

    public Iterable<G> getVertices(){
        return listaDeVertices;
    }

    public Iterable<G> getAdyacentesDelVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        List<Integer> adyacentesDelVerticeXNro=listasDeAdyacencias.get(nroDelVertice);
        List<G> listaDeAdyacentesDelVertice=new ArrayList<>();
        for(Integer nroVerticeEnTurno:adyacentesDelVerticeXNro){
            listaDeAdyacentesDelVertice.add(listaDeVertices.get(nroVerticeEnTurno));
        }
        return listaDeAdyacentesDelVertice;
    }

    public boolean existeAdyacencia(G verticeOrigen, G verticeDestino){
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        List<Integer> adyacenciasDelVerticeOrigen=listasDeAdyacencias.get(nroDelVerticeOrigen);
        return adyacenciasDelVerticeOrigen.contains(nroDelVerticeDestino);
    }

    public void insertarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaYaExiste{
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        List<Integer> adyacentesDelOrigen=listasDeAdyacencias.get(nroDelVerticeOrigen);
        adyacentesDelOrigen.add(nroDelVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        if(nroDelVerticeOrigen!=nroDelVerticeDestino){
            List<Integer> adyacentesDelDestino=listasDeAdyacencias.get(nroDelVerticeDestino);
            adyacentesDelDestino.add(nroDelVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public void eliminarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaNoExiste {

    }

    public void eliminarVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        listaDeVertices.remove(nroDelVertice);
        listasDeAdyacencias.remove(nroDelVertice);
        for(List<Integer> adyacentesDeUnVertice:listasDeAdyacencias){
            adyacentesDeUnVertice.remove((Integer)nroDelVertice);
            for(int i=0;i<adyacentesDeUnVertice.size();i++){
                int nroAdyacenteEnTurno=adyacentesDeUnVertice.get(i);
                if(nroAdyacenteEnTurno>nroDelVertice){
                    nroAdyacenteEnTurno--;
                    adyacentesDeUnVertice.set(i,nroAdyacenteEnTurno);
                }
            }
        }
    }

    public int gradoDelVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        List<Integer> adyacentesDelVertice=listasDeAdyacencias.get(nroDelVertice);
        return adyacentesDelVertice.size();
    }

}
