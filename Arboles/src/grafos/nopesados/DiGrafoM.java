package grafos.nopesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.Collections;
import java.util.List;

public class DiGrafoM<G extends Comparable<G>>extends GrafoM<G>{

    public DiGrafoM() {
    }

    public DiGrafoM(Iterable<G> vertices) {
        super(vertices);
    }

    @Override
    public int cantidadDeVertices() {
        return super.cantidadDeVertices();
    }

    @Override
    public void insertarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaYaExiste {
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        matrizDeAdyacencias.get(nroDelVerticeOrigen).set(nroDelVerticeDestino,Boolean.TRUE);
    }

    @Override
    public int gradoDelVertice(G vertice) {
        throw new UnsupportedOperationException("Operacion no soportada en grafo");
    }

    @Override
    public void eliminarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroVerticeOrigen=nroVertice(verticeOrigen);
        int nroVerticeDestino=nroVertice(verticeDestino);
        if(!matrizDeAdyacencias.get(nroVerticeOrigen).get(nroVerticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
        matrizDeAdyacencias.get(nroVerticeOrigen).set(nroVerticeDestino,Boolean.FALSE);
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
