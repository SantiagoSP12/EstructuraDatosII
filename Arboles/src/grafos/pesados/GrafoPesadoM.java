package grafos.pesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;
import grafos.nopesados.Grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoPesadoM<G extends Comparable<G>> {
    protected List<G> listaDeVertices;
    protected List<List<Double>> matrizDeAdyacencias;
    public static final int NRO_DE_VERTICE_INVALIDO=-1;

    public GrafoPesadoM(){
        listaDeVertices=new ArrayList<>();
        matrizDeAdyacencias =new ArrayList<>();
    }

    public GrafoPesadoM(Iterable<G> vertices){
        this();
        for(G vertice:vertices){
            insertarVertice(vertice);
        }
    }

    public GrafoPesadoM(GrafoPesado<G> unGrafoPesado){
        listaDeVertices=new ArrayList<>();
        matrizDeAdyacencias =new ArrayList<>();
        Iterable<G> vertices=unGrafoPesado.getVertices();
        for(G vertice:vertices){
            insertarVertice(vertice);
        }
        for(G vertice:vertices){
            int nroOrigen=unGrafoPesado.nroVertice(vertice);
            List<AdyacenteConPeso> adyacentes=unGrafoPesado.listasDeAdyacencias.get(nroOrigen);
            for(AdyacenteConPeso adyacente: adyacentes){
                matrizDeAdyacencias.get(nroOrigen).set(adyacente.getNroVertice(),adyacente.getPeso());
            }
        }
    }

    public void insertarVertice(G vertice){
        int nroDelVertice=nroVertice(vertice);
        if(nroDelVertice==NRO_DE_VERTICE_INVALIDO) {
            listaDeVertices.add(vertice);
            for (List<Double> filaDeAdyacencias : matrizDeAdyacencias) {
                filaDeAdyacencias.add(0.0);
            }
            matrizDeAdyacencias.add(new ArrayList<>());
            List<Double> nuevaFila = matrizDeAdyacencias.getLast();
            for (int i = 0; i < listaDeVertices.size(); i++) {
                nuevaFila.add(0.0);
            }
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

    public boolean existeAdyacencia(G verticeOrigen, G verticeDestino){
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroVerticeOrigen=nroVertice(verticeOrigen);
        int nroVerticeDestino=nroVertice(verticeDestino);
        return matrizDeAdyacencias.get(nroVerticeOrigen).get(nroVerticeDestino)!=0.0;
    }

    public void insertarArista(G verticeOrigen, G verticeDestino, double peso) throws ExcepcionAristaYaExiste {
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        matrizDeAdyacencias.get(nroDelVerticeOrigen).set(nroDelVerticeDestino,peso);
        if(nroDelVerticeOrigen!=nroDelVerticeDestino){
            matrizDeAdyacencias.get(nroDelVerticeDestino).set(nroDelVerticeOrigen,peso);
        }
    }

    public void eliminarVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        listaDeVertices.remove(nroDelVertice);
        matrizDeAdyacencias.remove(nroDelVertice);
        for(List<Double> adyacentesDeUnVertice: matrizDeAdyacencias){
            adyacentesDeUnVertice.remove(nroDelVertice);
        }
    }

    public int gradoDelVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        List<Double> pesosAdyacentesAlVertice= matrizDeAdyacencias.get(nroDelVertice);
        int grado=0;
        for(Double peso:pesosAdyacentesAlVertice){
            if(peso!=0.0) grado++;
        }
        return grado;
    }

    public void eliminarArista(G origen,G destino) throws ExcepcionAristaNoExiste {
        if(!existeAdyacencia(origen, destino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroVerticeOrigen=nroVertice(origen);
        int nroVerticeDestino=nroVertice(destino);
        matrizDeAdyacencias.get(nroVerticeOrigen).set(nroVerticeDestino,0.0);
        if(nroVerticeOrigen!=nroVerticeDestino){
            matrizDeAdyacencias.get(nroVerticeDestino).set(nroVerticeOrigen,0.0);
        }
    }

    public int cantidadDeVertices(){
        return listaDeVertices.size();
    }

    public int cantidadDeAristas(){
        int contador=0;
        for(int i=0;i<matrizDeAdyacencias.size();i++){
            for(int j=i;j<matrizDeAdyacencias.size();j++){
                if(matrizDeAdyacencias.get(i).get(j)!=0.0){
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
        List<Double> adyacentesDelVerticeXNro=matrizDeAdyacencias.get(nroDelVertice);
        List<G> listaDeAdyacentesDelVertice=new ArrayList<>();
        for(int i=0;i<adyacentesDelVerticeXNro.size();i++){
            if(adyacentesDelVerticeXNro.get(i)!=0.0){
                listaDeAdyacentesDelVertice.add(listaDeVertices.get(i));
            }
        }
        return listaDeAdyacentesDelVertice;
    }

    public double peso(G origen,G destino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(origen,destino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroOrigen=this.nroVertice(origen);
        int nroDestino=this.nroVertice(destino);
        return matrizDeAdyacencias.get(nroOrigen).get(nroDestino);
    }


    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Grafo no pesado{\n        ");
        for(G vertice:listaDeVertices){
            sb.append(vertice);
            for(int i=vertice.toString().length();i<=6;i++){
                sb.append(" ");
            }
        }
        sb.append("\n");
        int i=0;
        for(G vertice:listaDeVertices){
            sb.append(vertice.toString());
            sb.append("[ ");
            for(Double adyacencia:matrizDeAdyacencias.get(i)){
                sb.append(String.format("%6.1f", adyacencia)).append(" ");
            }
            i++;
            sb.append("\b]\n");
        }
        return sb.append("}\n").toString();
    }

}