package grafos.pesados;

import grafos.nopesados.OrdenamientoTopológico;
import grafos.utileria.ControlMarcados;

import java.util.*;

public class Dijkstra<G extends Comparable<G>>{
    private final GrafoPesado<G> elGrafoPesado;
    private final ControlMarcados controlMarcados;
    private final List<AdyacenteConPeso> costos;
    private final List<ParVerticePredecesor> predecesores;
    private double costoMinimo;
    private List<G> recorrido;

    private static class ParVerticePredecesor implements Comparable<ParVerticePredecesor>{
        private final int nroVertice;
        private int nroPredecesor;

        public ParVerticePredecesor(int nroVertice,int nroPredecesor){
            this.nroVertice=nroVertice;
            this.nroPredecesor=nroPredecesor;
        }

        public int getVertice(){
            return this.nroVertice;
        }

        public int getPredecesor(){
            return this.nroPredecesor;
        }

        public void setPredecesor(int nroPredecesor){
            this.nroPredecesor=nroPredecesor;
        }

        @Override
        public int compareTo(ParVerticePredecesor o) {
            if(this.nroVertice>o.nroVertice){
                return 1;
            }
            if(this.nroVertice<o.nroVertice){
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "["+nroVertice+":"+nroPredecesor+"]";
        }
    }

    private void iniciarListas(){
        this.controlMarcados.desmarcarTodos();
        for(int i=0;i<costos.size();i++){
            this.costos.get(i).setPeso(Double.POSITIVE_INFINITY);
            this.predecesores.get(i).setPredecesor(-1);
        }
    }

    public Dijkstra(GrafoPesado<G> unGrafoPesado){
        this.elGrafoPesado=unGrafoPesado;
        this.costos=new ArrayList<>();
        this.predecesores=new ArrayList<>();
        this.recorrido=new ArrayList<>();
        this.controlMarcados=new ControlMarcados(elGrafoPesado.cantidadDeVertices());
        for(int i=0;i<elGrafoPesado.cantidadDeVertices();i++){
            this.costos.add(new AdyacenteConPeso(i,Double.POSITIVE_INFINITY));
            this.predecesores.add(new ParVerticePredecesor(i,-1));
        }
    }

    public void ejecutarDijkstra(G verticeInicial, G verticeFinal) {
        elGrafoPesado.validarVertice(verticeInicial);
        elGrafoPesado.validarVertice(verticeFinal);
        this.iniciarListas();
        this.costos.get(elGrafoPesado.nroVertice(verticeInicial)).setPeso(0.0);
        AdyacenteConPeso verticeDeMenorCosto=new AdyacenteConPeso(elGrafoPesado.nroVertice(verticeInicial),0.0);
        while(verticeDeMenorCosto.getPeso()!=Double.POSITIVE_INFINITY){
            int nroInicial=verticeDeMenorCosto.getNroVertice();
            controlMarcados.marcar(nroInicial);
            if(controlMarcados.estaMarcadoVertice(elGrafoPesado.nroVertice(verticeFinal))){
                break;
            }
            List<AdyacenteConPeso> adyacentesDelVertice=elGrafoPesado.listasDeAdyacencias.get(nroInicial);
            for(int i=0;i<adyacentesDelVertice.size();i++){
                int nroAdy=adyacentesDelVertice.get(i).getNroVertice();
                double pesoAdy=adyacentesDelVertice.get(i).getPeso();
                if(costos.get(nroAdy).getPeso()>(costos.get(nroInicial).getPeso()+pesoAdy)){
                    costos.get(nroAdy).setPeso(costos.get(nroInicial).getPeso()+pesoAdy);
                    predecesores.get(nroAdy).setPredecesor(nroInicial);
                }
            }
            verticeDeMenorCosto=new AdyacenteConPeso(-1,Double.POSITIVE_INFINITY);
            for(int i=0;i<elGrafoPesado.cantidadDeVertices();i++){
                if(!controlMarcados.estaMarcadoVertice(i)){
                    if(costos.get(i).getPeso()<verticeDeMenorCosto.getPeso()){
                        verticeDeMenorCosto=costos.get(i);
                    }
                }
            }
        }
        costoMinimo=costos.get(elGrafoPesado.nroVertice(verticeFinal)).getPeso();
        if(costoMinimo!=Double.POSITIVE_INFINITY) {
            recorrido=new Stack<>();
            int actual=elGrafoPesado.nroVertice(verticeFinal);
            do{
                recorrido.addFirst(elGrafoPesado.listaDeVertices.get(actual));
                actual=predecesores.get(actual).getPredecesor();
            }while(actual!=-1);
        }
    }

    public List<G> getRecorrido(){
        return this.recorrido;
    }

    public double getCostoMinimo(){
        return this.costoMinimo;
    }

}
