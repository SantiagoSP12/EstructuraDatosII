package grafos.nopesados;

import grafos.utileria.ControlMarcados;

import java.util.*;

public class DFS<G extends Comparable<G>>{
    private final Grafo<G> elGrafo;
    private final ControlMarcados controlMarcados;
    private final List<G> recorrido;

    public DFS(Grafo<G> unGrafo, G verticeDePartida){
        elGrafo = unGrafo;
        controlMarcados=new ControlMarcados(elGrafo.cantidadDeVertices());
        recorrido=new ArrayList<>();
        ejecurarDFS(verticeDePartida);
    }

    private void ejecurarDFS(G verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        controlMarcados.marcar(elGrafo.nroVertice(verticeEnTurno));
        recorrido.add(verticeEnTurno);
        Iterable<G> adyacentesDelVertice = elGrafo.getAdyacentesDelVertice(verticeEnTurno);
        for (G adyacente : adyacentesDelVertice) {
            int nroDelAdyacente = elGrafo.nroVertice(verticeEnTurno);
            if (!controlMarcados.estaMarcadoVertice(nroDelAdyacente)) {
                ejecurarDFS(adyacente);
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
