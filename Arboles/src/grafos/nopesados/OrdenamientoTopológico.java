package grafos.nopesados;

import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.*;

public class OrdenamientoTopológico<G extends Comparable<G>> {

    private final List<G> recorrido;

    private static class ParVerticeGrado<G> implements Comparable<ParVerticeGrado<G>>{
        private G vertice;
        private int gradoDeEntrada;

        public ParVerticeGrado(G vertice,int gradoDeEntrada){
            this.vertice=vertice;
            this.gradoDeEntrada=gradoDeEntrada;
        }

        public G getVertice(){
            return this.vertice;
        }

        public int getGradoDeEntrada(){
            return this.gradoDeEntrada;
        }

        public void setGradoDeEntrada(int gradoDeEntrada){
            this.gradoDeEntrada=gradoDeEntrada;
        }

        @Override
        public int compareTo(ParVerticeGrado o) {
            if(this.gradoDeEntrada>o.gradoDeEntrada){
                return 1;
            }
            if(this.gradoDeEntrada<o.gradoDeEntrada){
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "["+vertice.toString()+":"+gradoDeEntrada+"]";
        }
    }

    public OrdenamientoTopológico(DiGrafo<G> unDiGrafo) throws Exception {
        if(unDiGrafo.hayCiclo()){
            throw new Exception("El grafo tiene que ser acíclico");
        }
        List<ParVerticeGrado<G>> listaVerticesGrado = new ArrayList<>();
        this.recorrido=new ArrayList<>();
        Iterable<G> vertices= unDiGrafo.listaDeVertices;
        for(G vertice:vertices){
            int gradoDeEntrada= unDiGrafo.gradoDeEntradaDelVertice(vertice);
            ParVerticeGrado<G> nuevoPar=new ParVerticeGrado<>(vertice, gradoDeEntrada);
            listaVerticesGrado.add(nuevoPar);
        }
        Collections.sort(listaVerticesGrado);
        System.out.println(listaVerticesGrado);
        Queue<G> colaDeVertices=new LinkedList<>();
        for (int i = 0; i < listaVerticesGrado.size(); i++) {
            if (listaVerticesGrado.get(i).getGradoDeEntrada() == 0) {
                colaDeVertices.add(listaVerticesGrado.get(i).getVertice());
            }
            else if (listaVerticesGrado.get(i).getGradoDeEntrada() == 1) {
                break;
            }
        }
        do {
            G verticeEnTurno=colaDeVertices.poll();
            recorrido.add(verticeEnTurno);
            Iterable<G> adyacentes= unDiGrafo.getAdyacentesDelVertice(verticeEnTurno);
            for(G ady:adyacentes){
                for(int i = 0; i< listaVerticesGrado.size(); i++){
                    if(listaVerticesGrado.get(i).getVertice().compareTo(ady)==0){
                        int grado= listaVerticesGrado.get(i).getGradoDeEntrada();
                        if(grado==1){
                            colaDeVertices.offer(ady);
                            listaVerticesGrado.get(i).setGradoDeEntrada(0);
                        }
                        listaVerticesGrado.get(i).setGradoDeEntrada(grado-1);
                        Collections.sort(listaVerticesGrado);
                        break;
                    }
                }
            }
        }while (!colaDeVertices.isEmpty());
    }

    public List<G> getRecorrido(){
        return this.recorrido;
    }

}
