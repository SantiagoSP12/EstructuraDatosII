package grafos.nopesados;

import grafos.utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS<G extends Comparable<G>>{
    private final Grafo<G> elGrafo;
    private final ControlMarcados controlMarcados;
    private final List<G> recorrido;

    public BFS(Grafo<G> unGrafo, G verticeDePartida){
        elGrafo = unGrafo;
        controlMarcados=new ControlMarcados(elGrafo.cantidadDeVertices());
        recorrido=new ArrayList<>();
        ejecurarBFS(verticeDePartida);
    }

    private void ejecurarBFS(G verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        Queue<G> colaDeVertices=new LinkedList<>();
        colaDeVertices.offer(verticeEnTurno);
        controlMarcados.marcar(elGrafo.nroVertice(verticeEnTurno));
        while(!colaDeVertices.isEmpty()){
            G vertice=colaDeVertices.poll();
            recorrido.add(vertice);
            Iterable<G> adyacentesDelVertice= elGrafo.getAdyacentesDelVertice(vertice);
            for(G adyacente:adyacentesDelVertice){
                int nroDelAdyacente=elGrafo.nroVertice(vertice);
                if(!controlMarcados.estaMarcadoVertice(nroDelAdyacente)){
                    colaDeVertices.offer(adyacente);
                    controlMarcados.marcar(nroDelAdyacente);
                }
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

    public boolean seVisitoTodos(){
        return controlMarcados.estanTodosMarcados();
    }

}
