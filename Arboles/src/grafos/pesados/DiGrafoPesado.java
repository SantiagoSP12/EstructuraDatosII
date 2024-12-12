package grafos.pesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.Collections;
import java.util.List;

public class DiGrafoPesado<G extends Comparable<G>> extends GrafoPesado<G> {
    public DiGrafoPesado(){
    }

    public DiGrafoPesado(Iterable<G> vertices){
        super(vertices);
    }

    @Override
    public void insertarArista(G origen, G destino, double peso) throws ExcepcionAristaYaExiste {
        if(existeAdyacencia(origen,destino)){
            throw new ExcepcionAristaYaExiste();
        }
        int nroOrigen=nroVertice(origen);
        int nroDestino=nroVertice(destino);
        AdyacenteConPeso nuevoAdyacente=new AdyacenteConPeso(nroDestino,peso);
        List<AdyacenteConPeso> adyacentesDelOrigen=listasDeAdyacencias.get(nroOrigen);
        adyacentesDelOrigen.add(nuevoAdyacente);
        Collections.sort(adyacentesDelOrigen);
    }

    @Override
    public void eliminarArista(G origen, G destino) throws ExcepcionAristaNoExiste {
        if(!this.existeAdyacencia(origen,destino)){
            throw new ExcepcionAristaNoExiste();
        }
        int nroVerticeOrigen=this.nroVertice(origen);
        int nroVerticeDestino=this.nroVertice(destino);
        AdyacenteConPeso adyacenteSinPeso=new AdyacenteConPeso(nroVerticeDestino);
        listasDeAdyacencias.get(nroVerticeOrigen).remove(adyacenteSinPeso);
    }

    @Override
    public int gradoDelVertice(G vertice){
        throw new UnsupportedOperationException("Operación no soportada en Digrafo");
    }

    public int gradoDeSalidaDelVertice(G vertice){
        return super.gradoDelVertice(vertice);
    }

    public int gradoDeEntradaDelVertice(G vertice){
        validarVertice(vertice);
        int i=0;
        int nroVertice=nroVertice(vertice);
        AdyacenteConPeso verticeSinPeso= new AdyacenteConPeso(nroVertice);
        for(List<AdyacenteConPeso> adyacentes:listasDeAdyacencias){
            if(adyacentes.contains(verticeSinPeso)){
                i++;
            }
        }
        return i;
    }

}
