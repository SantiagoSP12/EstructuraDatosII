package grafos.pesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.List;

public class DiGrafoPesadoM<G extends Comparable<G>> extends GrafoPesadoM<G> {
    public DiGrafoPesadoM() {
    }

    public DiGrafoPesadoM(Iterable<G> vertices) {
        super(vertices);
    }

    public DiGrafoPesadoM(GrafoPesado<G> unGrafoPesado){ super(unGrafoPesado); }

    @Override
    public int cantidadDeVertices() {
        return super.cantidadDeVertices();
    }

    @Override
    public void insertarArista(G verticeOrigen, G verticeDestino, double peso) throws ExcepcionAristaYaExiste {
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        matrizDeAdyacencias.get(nroDelVerticeOrigen).set(nroDelVerticeDestino,peso);
    }

    @Override
    public int gradoDelVertice(G vertice) {
        throw new UnsupportedOperationException("Operacion no soportada en grafo");
    }

    @Override
    public void eliminarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaNoExiste {
        if(!existeAdyacencia(verticeOrigen,verticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroVerticeOrigen=nroVertice(verticeOrigen);
        int nroVerticeDestino=nroVertice(verticeDestino);
        matrizDeAdyacencias.get(nroVerticeOrigen).set(nroVerticeDestino,0.0);
    }

    public int gradoDeSalidaDelVertice(G vertice){
        return super.gradoDelVertice(vertice);
    }

    public int gradoDeEntradaDelVertice(G vertice){
        validarVertice(vertice);
        int grado=0;
        Iterable<G> vertices=this.getVertices();
        for(G verticeEnTurno:vertices){
            List<G> adyacentes=(List<G>) this.getAdyacentesDelVertice(verticeEnTurno);
            if(adyacentes.contains(vertice)) grado++;
        }
        return grado;
    }
}
