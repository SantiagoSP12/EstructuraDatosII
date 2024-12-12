package grafos.pesados;

//disjkstra

//kruskal se aplica sobre pesados no dirigidos conexos
//es un arbol de expansion de costo minimo
//se crea un nuevo grafo no dirigido auxiliar con todos los vertices del grafo original
//En una estructura auxiliar A, ordena las aristas del grafo original ascendentemente por su peso
//A (origen,destino,peso) comparable por peso
//Por cada valor de A

//Prim
//si se ejecuta sobre un grafo dirigido no conexo, obtiene suv-arbol de expansion de costo minimo
//crea un nuevo grafo no dirigido auxiliar W con solo uno de los vertuces del grado original(vertice inicial)
//A partir del vértice inicial, el arbol de expansion crecce añadiendo a W en cada pasada otro vertice z todavia no incluido en W
//

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;
import grafos.nopesados.DFS;
import grafos.nopesados.Grafo;
import grafos.utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoPesado<G extends Comparable<G>> {
    protected List<G> listaDeVertices;
    protected List<List<AdyacenteConPeso>> listasDeAdyacencias;
    public static final int NRO_DE_VERTICE_INVALIDO=-1;

    public GrafoPesado(){
        listaDeVertices=new ArrayList<>();
        listasDeAdyacencias =new ArrayList<>();
    }

    public GrafoPesado(Iterable<G> vertices){
        this();
        for(G vertice:vertices){
            insertarVertice(vertice);
        }
    }

    public void insertarVertice(G vertice){
        int nroVertice=nroVertice(vertice);
        if(nroVertice==NRO_DE_VERTICE_INVALIDO) {
            listaDeVertices.add(vertice);
            this.listasDeAdyacencias.add(new ArrayList<>());
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
        List<AdyacenteConPeso> adyacentesDelOrigen= listasDeAdyacencias.get(nroVerticeOrigen);
        AdyacenteConPeso adyacenteSinPeso=new AdyacenteConPeso(nroVerticeDestino);
        return adyacentesDelOrigen.contains(adyacenteSinPeso);
    }

    public void insertarArista(G verticeOrigen, G verticeDestino, double peso) throws ExcepcionAristaYaExiste {
        if(existeAdyacencia(verticeOrigen,verticeDestino)){
            throw new ExcepcionAristaYaExiste();
        }
        int nroVerticeOrigen=nroVertice(verticeOrigen);
        int nroVerticeDestino=nroVertice(verticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen= listasDeAdyacencias.get(nroVerticeOrigen);
        AdyacenteConPeso adyDelOrigen=new AdyacenteConPeso(nroVerticeDestino,peso);
        adyacentesDelOrigen.add(adyDelOrigen);
        Collections.sort(adyacentesDelOrigen);
        if(nroVerticeOrigen!=nroVerticeDestino){
            List<AdyacenteConPeso> adyacentesDelDestino= listasDeAdyacencias.get(nroVerticeDestino);
            AdyacenteConPeso adyDelDestino=new AdyacenteConPeso(nroVerticeOrigen,peso);
            adyacentesDelDestino.add(adyDelDestino);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public void eliminarVertice(G vertice){
        validarVertice(vertice);
        int nroDelVertice=nroVertice(vertice);
        listasDeAdyacencias.remove(nroDelVertice);
        listaDeVertices.remove(nroDelVertice);
        AdyacenteConPeso adyacenteSinPeso=new AdyacenteConPeso(nroDelVertice);
        for(List<AdyacenteConPeso> adyacentesDeUnVertice:listasDeAdyacencias){
            adyacentesDeUnVertice.remove(adyacenteSinPeso);
        }
        for(List<AdyacenteConPeso> adyacentesDeUnVertice:listasDeAdyacencias){
            for(AdyacenteConPeso adyacenteConPeso:adyacentesDeUnVertice){
                if(adyacenteConPeso.getNroVertice()>nroDelVertice){
                    int nroAdyacente=adyacenteConPeso.getNroVertice();
                    adyacenteConPeso.setNroVertice(nroAdyacente-1);
                }
            }
        }
    }

    public int gradoDelVertice(G vertice){
        validarVertice(vertice);
        int nroDeVertice=nroVertice(vertice);
        return listasDeAdyacencias.get(nroDeVertice).size();
    }

    public void eliminarArista(G origen,G destino) throws ExcepcionAristaNoExiste{
        if(!this.existeAdyacencia(origen,destino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroVerticeOrigen=this.nroVertice(origen);
        int nroVerticeDestino=this.nroVertice(destino);
        AdyacenteConPeso adyacenteSinPeso=new AdyacenteConPeso(nroVerticeDestino);
        listasDeAdyacencias.get(nroVerticeOrigen).remove(adyacenteSinPeso);
        if(nroVerticeOrigen!=nroVerticeDestino){
            adyacenteSinPeso=new AdyacenteConPeso(nroVerticeOrigen);
            listasDeAdyacencias.get(nroVerticeDestino).remove(adyacenteSinPeso);
        }
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
        List<AdyacenteConPeso> adyacentesDelVerticeXNro= listasDeAdyacencias.get(nroDelVertice);
        List<G> listaDeAdyacentesDelVertice=new ArrayList<>();
        for(AdyacenteConPeso verticeEnTurno:adyacentesDelVerticeXNro){
            listaDeAdyacentesDelVertice.add(listaDeVertices.get(verticeEnTurno.getNroVertice()));
        }
        return listaDeAdyacentesDelVertice;
    }

    public double peso(G origen,G destino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(origen,destino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroOrigen=this.nroVertice(origen);
        int nroDestino=this.nroVertice(destino);
        int nroAdy=listasDeAdyacencias.get(nroOrigen).indexOf(new AdyacenteConPeso(nroDestino));
        return listasDeAdyacencias.get(nroOrigen).get(nroAdy).getPeso();
    }

    //conectividad
    public boolean grafoConexo(){
        DFSPesados<G> recorridoDFS=new DFSPesados<>(this,listaDeVertices.getFirst());
        return recorridoDFS.seVisitoTodosLosVertices();
    }

    //ciclos
    //   6.- Para saber si un grafo no dirigido tiene ciclos
//       6.1.- Se crea un grafo Auxiliar como control de conjunto de aristas que ya se usaron
//       6.2.- Eligo cualquier vértice y lo marco
//       6.3.- Hago un recorrido DFS modificado
//       6.4.- Eligo un adyacente, pregunto si está marcado
//             6.4.a.- Si no está marcado, inserto la arista en el grafo auxiliar, continuo el DFS por ahí
//             6.4.b.- Si está marcado, pregunto si en el grafo auxiliar existe esa arista
//                     6.4.b.a.- Si existe la arista, no continuo por alli, porque por ahi es donde vengo
//                     6.4.b.b.- Si no existe dicha arista, el grafo tiene ciclo
//       6.5.- Si no marque todos al finalizar, eligo un vertice no marcado, y continuo por allí, repito 6.4
    public boolean hayCiclo() throws ExcepcionAristaYaExiste {
        Iterable<G> vertices=this.getVertices();
        Grafo<G> grafoNoPesado=new Grafo<>(vertices);
        for(G origen:listaDeVertices){
            Iterable<G> adyacentesDeUnVertice=this.getAdyacentesDelVertice(origen);
            for(G ady:adyacentesDeUnVertice){
                if(!grafoNoPesado.existeAdyacencia(origen,ady)){
                    grafoNoPesado.insertarArista(origen,ady);
                }
            }
        }
        return grafoNoPesado.hayCiclo();
    }


    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Grafo Pesado{\n");
        for(G vertice:listaDeVertices){
            sb.append(vertice.toString()+"->");
            int nroOrigen=nroVertice(vertice);
            for(int i=0;i<listasDeAdyacencias.get(nroOrigen).size();i++){
                AdyacenteConPeso elAdyacente=listasDeAdyacencias.get(nroOrigen).get(i);
                sb.append("["+listaDeVertices.get(elAdyacente.getNroVertice()).toString()+","
                        +elAdyacente.getPeso()+"]->");
            }
            sb.append("\n");
        }
        return sb.append("}\n").toString();
    }

}
