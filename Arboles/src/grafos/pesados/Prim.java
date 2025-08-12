package grafos.pesados;

import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Prim<G extends Comparable<G>> {
    private final GrafoPesado<G> grafoAuxiliar;
    private final GrafoPesado<G> elGrafoPesado;

    public Prim(GrafoPesado<G> unGrafoPesado,G verticeInicial){
        try {
            unGrafoPesado.validarVertice(verticeInicial);
            this.elGrafoPesado = unGrafoPesado;
            this.grafoAuxiliar = new GrafoPesado<>();
            this.grafoAuxiliar.insertarVertice(verticeInicial);
            ejecutarPrim();
        }catch(ExcepcionAristaYaExiste e){
            throw new RuntimeException(e);
        }
    }

    public void ejecutarPrim() throws ExcepcionAristaYaExiste {
        AdyacenteConPeso control;
        do {
            control = new AdyacenteConPeso(GrafoPesado.NRO_DE_VERTICE_INVALIDO, Double.MAX_VALUE);
            G origen = null;
            for (G vertice : grafoAuxiliar.getVertices()) {
                int nroOrigen = elGrafoPesado.nroVertice(vertice);
                for (AdyacenteConPeso adyacente:elGrafoPesado.listasDeAdyacencias.get(nroOrigen)) {
                    G ady = elGrafoPesado.listaDeVertices.get(adyacente.getNroVertice());
                    if (grafoAuxiliar.nroVertice(ady) == GrafoPesado.NRO_DE_VERTICE_INVALIDO) {
                        if (adyacente.getPeso() < control.getPeso()) {
                            origen = vertice;
                            control = adyacente;
                        }
                    }
                }
            }
            if (control.getNroVertice() == GrafoPesado.NRO_DE_VERTICE_INVALIDO) {
                break;
            }
            G verticeDestino = elGrafoPesado.listaDeVertices.get(control.getNroVertice());
            grafoAuxiliar.insertarVertice(verticeDestino);
            grafoAuxiliar.insertarArista(origen, verticeDestino, control.getPeso());
        }while(control.getNroVertice()!=GrafoPesado.NRO_DE_VERTICE_INVALIDO);
    }

    @Override
    public String toString(){
        return grafoAuxiliar.toString();
    }
}
