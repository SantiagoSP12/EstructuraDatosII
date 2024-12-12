package grafos.nopesados;

import grafos.utileria.ControlMarcados;

import java.util.*;

public class DFS<G extends Comparable<G>>{
    private final Grafo<G> elGrafo;
    private final ControlMarcados controlMarcados;
    private final List<G> recorrido;

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
//       6.1.- Se crea un grafo Auxiliar como control de conjunto de aristas que ya se usaron
//       6.2.- Eligo cualquier vértice y lo marco
//       6.3.- Hago un recorrido DFS modificado
//       6.4.- Eligo un adyacente, pregunto si está marcado
//             6.4.a.- Si no está marcado, inserto la arista en el grafo auxiliar, continuo el DFS por ahí
//             6.4.b.- Si está marcado, pregunto si en el grafo auxiliar existe esa arista
//                     6.4.b.a.- Si existe la arista, no continuo por alli, porque por ahi es donde vengo
//                     6.4.b.b.- Si no existe dicha arista, el grafo tiene ciclo
//       6.5.- Si no marque todos al finalizar, eligo un vertice no marcado, y continuo por allí, repito 6.4

    public DFS(Grafo<G> unGrafo, G verticeDePartida){
        elGrafo = unGrafo;
        controlMarcados=new ControlMarcados(elGrafo.cantidadDeVertices());
        recorrido=new ArrayList<>();
        ejecutarDFS(verticeDePartida);
    }

    public DFS(Grafo<G> unGrafo){
        elGrafo = unGrafo;
        controlMarcados=new ControlMarcados(elGrafo.cantidadDeVertices());
        recorrido=new ArrayList<>();
    }

    public void ejecutarDFS(G verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        controlMarcados.marcar(elGrafo.nroVertice(verticeEnTurno));
        recorrido.add(verticeEnTurno);
        Iterable<G> adyacentesDelVertice = elGrafo.getAdyacentesDelVertice(verticeEnTurno);
        for (G adyacente : adyacentesDelVertice) {
            int nroDelAdyacente = elGrafo.nroVertice(adyacente);
            if (!controlMarcados.estaMarcadoVertice(nroDelAdyacente)) {
                ejecutarDFS(adyacente);
            }
        }
    }

    public List<G> getRecorrido(){
        return recorrido;
    }

    public boolean seVisitoVertice(G vertice){
        elGrafo.validarVertice(vertice);
        int nroVertice= elGrafo.nroVertice(vertice);
        return controlMarcados.estaMarcadoVertice(nroVertice);
    }

    public boolean seVisitoTodosLosVertices(){
        return controlMarcados.estanTodosMarcados();
    }

    public Iterable<G> verticesNoMarcados(){
        List<G> verticesNoMarcados=new ArrayList<>();
        Iterable<G> vertices= elGrafo.getVertices();
        for(G vertice:vertices){
            if(!seVisitoVertice(vertice)){
                verticesNoMarcados.add(vertice);
            }
        }
        return verticesNoMarcados;
    }

}
