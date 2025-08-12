package grafos.pesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;
import grafos.utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FordFulkerson<G extends Comparable<G>> {
    private static class DiGrafoPesadoFM<G extends Comparable<G>> extends DiGrafoPesado<G> {
        private final Comparator<AdyacenteConPeso> comparadorAdyacentePorPeso=new Comparator<AdyacenteConPeso>() {
            @Override
            public int compare(AdyacenteConPeso o1, AdyacenteConPeso o2) {
                if(o1.getPeso()<o2.getPeso()) return 1;
                if (o1.getPeso()>o2.getPeso()) return -1;
                return 0;
            }
        };

        public DiGrafoPesadoFM(Iterable<G> vertices){
            super(vertices);
        }

        @Override
        public void insertarArista(G verticeOrigen,G verticeDestino,double peso)throws ExcepcionAristaYaExiste {
            if(this.existeAdyacencia(verticeOrigen,verticeDestino)){
                throw new ExcepcionAristaYaExiste();
            }
            int nroOrigen=nroVertice(verticeOrigen);
            int nroDestino=nroVertice(verticeDestino);
            List<AdyacenteConPeso> adyacentesDelOrigen=this.listasDeAdyacencias.get(nroOrigen);
            adyacentesDelOrigen.add(new AdyacenteConPeso(nroDestino,peso));
            Collections.sort(adyacentesDelOrigen,comparadorAdyacentePorPeso);
        }

        private void setPesoArista(G verticeOrigen,G verticeDestino, double peso){
            int nroOrigen=nroVertice(verticeOrigen);
            int nroDestino=nroVertice(verticeDestino);
            List<AdyacenteConPeso> adyacentesDelOrigen=this.listasDeAdyacencias.get(nroOrigen);
            AdyacenteConPeso adyacenteDelOrigen=new AdyacenteConPeso(nroDestino,peso);
            int posicionAdyacente=adyacentesDelOrigen.indexOf(adyacenteDelOrigen);
            adyacentesDelOrigen.set(posicionAdyacente,adyacenteDelOrigen);
            Collections.sort(adyacentesDelOrigen,comparadorAdyacentePorPeso);
        }

    }

    private DiGrafoPesado<G> diGrafoOriginal;
    private DiGrafoPesadoFM<G> diGrafoPesadoFM;
    private ControlMarcados controlMarcados;
    private double flujoMaximo;
    private G verticeFuente;
    private G verticeSumidero;

    public FordFulkerson(DiGrafoPesado<G> diGrafoBase){
        try{
            this.diGrafoOriginal=diGrafoBase;
            diGrafoPesadoFM=new DiGrafoPesadoFM<>(diGrafoOriginal.getVertices());
            this.armarGrafoFMAuxiliar(diGrafoBase);
            this.controlMarcados=new ControlMarcados(diGrafoPesadoFM.cantidadDeVertices());
            this.ejecutarFordFulkerson();
        }catch (ExcepcionAristaYaExiste | ExcepcionAristaNoExiste e){
            throw new RuntimeException(e);
        }
    }

    private void armarGrafoFMAuxiliar(DiGrafoPesado<G> diGrafoBase)throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste{
        List<G> fuentes=new ArrayList<>();
        List<G> sumideros=new ArrayList<>();
        for(G verticeEnTurno:diGrafoBase.getVertices()){
            for(G adyacente:diGrafoBase.getAdyacentesDelVertice(verticeEnTurno)){
                double peso=diGrafoBase.peso(verticeEnTurno,adyacente);

                if(diGrafoPesadoFM.existeAdyacencia(verticeEnTurno,adyacente)){
                    diGrafoPesadoFM.setPesoArista(verticeEnTurno,adyacente,peso);
                }else{
                    diGrafoPesadoFM.insertarArista(verticeEnTurno,adyacente,peso);
                }

                if(diGrafoPesadoFM.existeAdyacencia(adyacente,verticeEnTurno)){
                    if(diGrafoBase.existeAdyacencia(adyacente,verticeEnTurno)){
                        peso=diGrafoBase.peso(adyacente,verticeEnTurno);
                        diGrafoPesadoFM.setPesoArista(adyacente,verticeEnTurno,peso);
                    }
                }else{
                    if(diGrafoBase.existeAdyacencia(adyacente,verticeEnTurno)){
                        peso=diGrafoBase.peso(adyacente,verticeEnTurno);
                        diGrafoPesadoFM.insertarArista(adyacente,verticeEnTurno,peso);
                    }else{
                        diGrafoPesadoFM.insertarArista(adyacente,verticeEnTurno,0.0);
                    }
                }
            }
            if(diGrafoOriginal.gradoDeEntradaDelVertice(verticeEnTurno)==0){
                System.out.println(verticeEnTurno);
                fuentes.add(verticeEnTurno);
            }
            if(diGrafoOriginal.gradoDeSalidaDelVertice(verticeEnTurno)==0){
                System.out.println(verticeEnTurno);
                sumideros.add(verticeEnTurno);
            }
        }
        if(fuentes.size()>1){
            verticeFuente= (G) "Fount";
            this.diGrafoPesadoFM.insertarVertice(verticeFuente);
            for(int i=0;i<fuentes.size();i++){
                this.diGrafoPesadoFM.insertarArista(verticeFuente,fuentes.get(i),Double.POSITIVE_INFINITY);
                this.diGrafoPesadoFM.insertarArista(fuentes.get(i),verticeFuente,0.0);
            }
        }else{
            verticeFuente=fuentes.getFirst();
        }
        if(sumideros.size()>1){
            verticeSumidero=(G) "Summit";
            this.diGrafoPesadoFM.insertarVertice(verticeSumidero);
            for(G sumidero:sumideros){
                this.diGrafoPesadoFM.insertarArista(sumidero,verticeSumidero,Double.POSITIVE_INFINITY);
                this.diGrafoPesadoFM.insertarArista(verticeSumidero,sumidero,0.0);
            }
        }else{
            verticeSumidero=sumideros.getFirst();
        }
        System.out.println("Auxiliar inicial: "+this.diGrafoPesadoFM);
    }

    private void ejecutarFordFulkerson() throws ExcepcionAristaNoExiste{
        List<G> camino=new ArrayList<>();
        while(this.existeFlujoResidual(verticeFuente,camino)){
            double flujoIteracion=this.flujoMinimoDelCamino(camino);
            this.actualizarFlujosResiduales(camino,flujoIteracion);
            flujoMaximo+=flujoIteracion;
            camino.removeAll(camino);
            this.controlMarcados.desmarcarTodos();
        }
    }

    private boolean existeFlujoResidual(G verticeEnTurno, List<G> camino) throws ExcepcionAristaNoExiste{
        int posVerticeEnTurno=this.diGrafoPesadoFM.nroVertice(verticeEnTurno);
        controlMarcados.marcar(posVerticeEnTurno);
        camino.add(verticeEnTurno);
        if(verticeEnTurno.compareTo(verticeSumidero)==0){
            return true;
        }
        Iterable<G> adysDeVerticeEnTurno=diGrafoPesadoFM.getAdyacentesDelVertice(verticeEnTurno);
        for(G adyacente:adysDeVerticeEnTurno){
            double pesoAlAdyacente=diGrafoPesadoFM.peso(verticeEnTurno,adyacente);
            int nroVerticeAdyacente=diGrafoPesadoFM.nroVertice(adyacente);
            if(!controlMarcados.estaMarcadoVertice(nroVerticeAdyacente)){
                if(pesoAlAdyacente>0.0){
                    boolean hayFlujoResidual=this.existeFlujoResidual(adyacente,camino);
                    if(hayFlujoResidual){
                        return true;
                    }
                    camino.remove(adyacente);
                }
            }
        }
        return false;
    }

    private void actualizarFlujosResiduales(List<G> camino,double flujoIteracion) throws ExcepcionAristaNoExiste{
        G verticePrevio= camino.getFirst();
        for(int i=1;i<camino.size();i++){
            G verticeSiguiente= camino.get(i);
            double pesoDeIda=diGrafoPesadoFM.peso(verticePrevio,verticeSiguiente);
            double pesoDeVuelta=diGrafoPesadoFM.peso(verticeSiguiente,verticePrevio);
            diGrafoPesadoFM.setPesoArista(verticePrevio,verticeSiguiente,pesoDeIda-flujoIteracion);
            diGrafoPesadoFM.setPesoArista(verticeSiguiente,verticePrevio,pesoDeVuelta+flujoIteracion);
            verticePrevio=verticeSiguiente;
        }
    }

    private double flujoMinimoDelCamino(List<G> camino) throws ExcepcionAristaNoExiste{
        double flujoIt=Double.POSITIVE_INFINITY;
        G verticeInicial=camino.getFirst();
        for(int i=1;i<camino.size();i++){
            G verticeSiguiente=camino.get(i);
            double pesoArista=diGrafoPesadoFM.peso(verticeInicial,verticeSiguiente);
            if(flujoIt>pesoArista){
                flujoIt=pesoArista;
            }
            verticeInicial=verticeSiguiente;
        }
        return flujoIt;
    }

    public double getFlujoMaximo(){return flujoMaximo;}

    public void imprimirFlujos() throws ExcepcionAristaNoExiste{
        System.out.println("Flujos a usar:");
        for(G verticeEnTurno:diGrafoOriginal.getVertices()){
            Iterable<G> adyacentesEnTurnoOriginal=diGrafoOriginal.getAdyacentesDelVertice(verticeEnTurno);
            for(G adyacente:adyacentesEnTurnoOriginal){
                double costoOriginal=diGrafoOriginal.peso(verticeEnTurno,adyacente);
                double costoAcualizado=diGrafoPesadoFM.peso(verticeEnTurno,adyacente);
                if(costoAcualizado<costoOriginal){
                    System.out.println(" # Flujo de "+verticeEnTurno+" a "+adyacente+
                            " => " +(costoOriginal-costoAcualizado));
                }
            }
        }
    }


}
