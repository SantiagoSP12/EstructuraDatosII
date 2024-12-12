package grafos.nopesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;
import grafos.utileria.ControlMarcados;

import java.util.*;
//Proyecto, usar los metodos
//Malla curricular, usar el Ordenamiento topológico, determinar como debo inscribir si suponemos es modular
//Grafo no dirigido, numero de islas

// * Uso Matriz de Caminos (El algorito de warshall)
//   1.- Para saber entre que vértices hay camino en un grafo dirigido
//   2.- Para saber si hay ciclo(s) en un grafo dirigido
//   3.- Para saber si un grafo dirigido es fuertemente conexo
// * Usos posibles de los reccoridos
//   1.- Para saber a que otros vértices se puede llegar desde un vértice de partida
//   2.- Para saber si el grafo dirigido es fuertemente conexo
//       2.a.1- Realizar un recorrido iniciando por uno de los vértices del grafo
//       2.a.2- Si al finalizar el recorrido se marcaron todos los vértices,
//              realizar de nuevo el reccorrido iniciando por otro vértices,
//              hasta realizar realizar recorrido iniciando por cada vértice
//           -- Si al realizar un recorrido iniciando por cualquier vértice no quedan todos los vértices marcados,
//              me detengo y digo que el grafo dirigido no es fuertemente conexo
//   3.- Para saber si un grado no dirigido es conexo
//       3.1.- Si realizamos un recorrido iniciando por cualquier vértice,
//             si quedan todos los vertices marcados,
//             entonces el grafo es conexo
//   4.- Para saber cuantas islas tiene un grafo no dirigido
//       4.1.- Iniciamos un contador en 1
//       4.2.- Iniciamos un recorrido por cualquier vértice
//       4.3.- Si al finalizar el recorrido, están todos los vértices marcados
//             (seVisitoTodosLosVertices), entonces retornamos el contador
//       4.4.- Si al finalizar el recorrido, no están todos los vértices marcados
//             (!seVisitoTodosLosVertices), entonces elegimos cualquier vértice
//             que no quedó marcado (!seVisitóVertice), aumentamos el contador en 1
//             y continuamos el recorrido a partir de este punto
//       4.5.- Al finalizar el nuevo recorrido volvemos al punto 4.33
//   5.- Para saber si un grafo dirigido es débilmente conexo
//       5.1.- Elegimos un vértice x perteneciente al grafo
//       5.2.- Iniciamos un recorrido por el vertice x electo
//       5.3.- si al terminar el recorrido quedan todos los vértices marcados,
//             entonces retornamos que el digrafo es debilmente conexo
//       5.4.- Si al terminar el recorrido,  no quedan todos los vértices marcados,
//             elegimos otro vértice x no marcado que tenga un adyacente marcado
//       5.5.- Si no existe otro vértice x no marcado con adyacente marcado,
//             entonces retornamos que el digrafo no es conexo
//       5.6.- Si existe otro vértice x no marcado con adyacente marcado,
//             continuamos el recorrido a partir del vértice x
//       5.7.- Volvemos al punto 5.3
//   6.- Para saber si un grafo no dirigido tiene ciclos
//       6.1.- Se crea un grafo Auxiliar comom control de conjunto de aristas que ya se usaron
//       6.2.- Eligo cualquier vértice y lo marco
//       6.3.- Hago un recorrido DFS modificado
//       6.4.- Eligo un adyacente, pregunto si está marcado
//             6.4.a.- Si no está marcado, inserto la arista en el grafo auxiliar, continuo el DFS por ahí
//             6.4.b.- Si está marcado, pregunto si en el grafo auxiliar existe esa arista
//                     6.4.b.a.- Si existe la arista, no continuo por alli, porque por ahi es donde vengo
//                     6.4.b.b.- Si no existe dicha arista, el grafo tiene ciclo
//       6.5.- Si no marque todos al finalizar, eligo un vertice no marcado, y continuo por allí, repito 6.4

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
        for(G vertice : vertices){
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
        int nroVertice=this.nroVertice(vertice);
        if(nroVertice==NRO_DE_VERTICE_INVALIDO) {
            listaDeVertices.add(vertice);
            listasDeAdyacencias.add(new ArrayList<>());
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
        if(!existeAdyacencia(verticeOrigen,verticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        List<Integer> adyacentesDelOrigen=listasDeAdyacencias.get(nroDelVerticeOrigen);
        adyacentesDelOrigen.remove(nroDelVerticeDestino);
        if(nroDelVerticeOrigen!=nroDelVerticeDestino){
            List<Integer> adyacentesDelDestino=listasDeAdyacencias.get(nroDelVerticeDestino);
            adyacentesDelDestino.remove(nroDelVerticeOrigen);
        }
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

//   3.- Para saber si un grado no dirigido es conexo
//       3.1.- Si realizamos un recorrido iniciando por cualquier vértice,
//             si quedan todos los vertices marcados,
//             entonces el grafo es conexo
    public boolean grafoConexo(){
        DFS<G> recorridoDFS=new DFS<>(this,listaDeVertices.getFirst());
        return recorridoDFS.seVisitoTodosLosVertices();
    }

//   4.- Para saber cuantas islas tiene un grafo no dirigido
//       4.1.- Iniciamos un contador en 1
//       4.2.- Iniciamos un recorrido por cualquier vértice
//       4.3.- Si al finalizar el recorrido, están todos los vértices marcados
//             (seVisitoTodosLosVertices), entonces retornamos el contador
//       4.4.- Si al finalizar el recorrido, no están todos los vértices marcados
//             (!seVisitoTodosLosVertices), entonces elegimos cualquier vértice
//             que no quedó marcado (!seVisitóVertice), aumentamos el contador en 1
//             y continuamos el recorrido a partir de este punto
//       4.5.- Al finalizar el nuevo recorrido volvemos al punto 4.3


    public int nroIslas(){
        int contador=0;
        DFS<G> recorridoDFS=new DFS<>(this);
        Iterable<G> vertices=this.getVertices();
        while (!recorridoDFS.seVisitoTodosLosVertices()){
            for(G verticeAEjecutar:vertices) {
                if(!recorridoDFS.seVisitoVertice(verticeAEjecutar)) {
                    contador++;
                    recorridoDFS.ejecutarDFS(verticeAEjecutar);
                }
            }
        }
        return contador;
    }

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
        DFSCiclos dfsCiclos=new DFSCiclos(this);
        for(G vertice:vertices){
            if(!dfsCiclos.controlMarcados.estaMarcadoVertice(nroVertice(vertice))){
                dfsCiclos.ejecutarDFSCiclos(vertice);
            }
        }
        return dfsCiclos.hayCiclo;
    }

    private class DFSCiclos{
        private final ControlMarcados controlMarcados;
        private final Grafo<G> elGrafo;
        private final Grafo<G> auxiliar;
        private boolean hayCiclo=false;

        public DFSCiclos(Grafo<G> unGrafo) {
            elGrafo = unGrafo;
            auxiliar = new Grafo<>(elGrafo.getVertices());
            controlMarcados = new ControlMarcados(elGrafo.cantidadDeVertices());
        }

        public void ejecutarDFSCiclos(G verticeEnTurno) throws ExcepcionAristaYaExiste {
            this.elGrafo.validarVertice(verticeEnTurno);
            controlMarcados.marcar(this.elGrafo.nroVertice(verticeEnTurno));
            Iterable<G> adyacentesDelVertice = this.elGrafo.getAdyacentesDelVertice(verticeEnTurno);
            for (G adyacente : adyacentesDelVertice) {
                int nroDelAdyacente = this.elGrafo.nroVertice(adyacente);
                if (!controlMarcados.estaMarcadoVertice(nroDelAdyacente)) {
                    auxiliar.insertarArista(verticeEnTurno,adyacente);
                    ejecutarDFSCiclos(adyacente);
                } else if(!auxiliar.existeAdyacencia(verticeEnTurno,adyacente)){
                    this.hayCiclo=true;
                }
            }
        }
    }


    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Grafo no pesado{\n");
        for(G vertice:listaDeVertices){
            sb.append(vertice.toString());
            sb.append("->");
            for(G verticeAdyacente:getAdyacentesDelVertice(vertice)) {
                sb.append(verticeAdyacente.toString());
                sb.append("->");
            }
            sb.append("\n");
        }
        return sb.append("}\n").toString();
    }


}
