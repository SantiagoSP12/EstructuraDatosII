package grafos.pesados;

import grafos.nopesados.Grafo;
import grafos.utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.List;

public class DFSPesados<G extends Comparable<G>>{
    private final GrafoPesado<G> elGrafoPesado;
    private final ControlMarcados controlMarcados;
    private final List<G> recorrido;

    public DFSPesados(GrafoPesado<G> unGrafoPesado, G verticeDePartida){
        elGrafoPesado = unGrafoPesado;
        controlMarcados=new ControlMarcados(elGrafoPesado.cantidadDeVertices());
        recorrido=new ArrayList<>();
        ejecutarDFS(verticeDePartida);
    }

    public DFSPesados(GrafoPesado<G> unGrafoPesado){
        elGrafoPesado = unGrafoPesado;
        controlMarcados=new ControlMarcados(elGrafoPesado.cantidadDeVertices());
        recorrido=new ArrayList<>();
    }

    public void ejecutarDFS(G verticeEnTurno) {
        elGrafoPesado.validarVertice(verticeEnTurno);
        controlMarcados.marcar(elGrafoPesado.nroVertice(verticeEnTurno));
        recorrido.add(verticeEnTurno);
        Iterable<G> adyacentesDelVertice = elGrafoPesado.getAdyacentesDelVertice(verticeEnTurno);
        for (G adyacente : adyacentesDelVertice) {
            int nroDelAdyacente = elGrafoPesado.nroVertice(adyacente);
            if (!controlMarcados.estaMarcadoVertice(nroDelAdyacente)) {
                ejecutarDFS(adyacente);
            }
        }
    }

    public List<G> getRecorrido(){
        return recorrido;
    }

    public boolean seVisitoVertice(G vertice){
        elGrafoPesado.validarVertice(vertice);
        int nroVertice= elGrafoPesado.nroVertice(vertice);
        return controlMarcados.estaMarcadoVertice(nroVertice);
    }

    public boolean seVisitoTodosLosVertices(){
        return controlMarcados.estanTodosMarcados();
    }

    public Iterable<G> verticesNoMarcados(){
        List<G> verticesNoMarcados=new ArrayList<>();
        Iterable<G> vertices= elGrafoPesado.getVertices();
        for(G vertice:vertices){
            if(!seVisitoVertice(vertice)){
                verticesNoMarcados.add(vertice);
            }
        }
        return verticesNoMarcados;
    }

}

