package grafos.nopesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;
import grafos.utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiGrafo<G extends Comparable<G>> extends Grafo<G>{

    public DiGrafo() {
    }

    public DiGrafo(Iterable<G> vertices) {
        super(vertices);
    }


    @Override
    public void insertarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaYaExiste {
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        List<Integer> adyacentesDelOrigen=listasDeAdyacencias.get(nroDelVerticeOrigen);
        adyacentesDelOrigen.add(nroDelVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }

    @Override
    public int gradoDelVertice(G vertice) {
        throw new UnsupportedOperationException("Operacion no soportada en grafo");
    }

    @Override
    public void eliminarArista(G verticeOrigen, G verticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroDelVerticeOrigen=nroVertice(verticeOrigen);
        int nroDelVerticeDestino=nroVertice(verticeDestino);
        List<Integer> adyacentesDelOrigen=listasDeAdyacencias.get(nroDelVerticeOrigen);
        if(!adyacentesDelOrigen.contains(nroDelVerticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
        adyacentesDelOrigen.remove(nroDelVerticeDestino);
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

    // * Usos posibles de los reccoridos
//   1.- Para saber a que otros vértices se puede llegar desde un vértice de partida
//   2.- Para saber si el grafo dirigido es fuertemente conexo
//       2.a.1- Realizar un recorrido iniciando por uno de los vértices del grafo
//       2.a.2- Si al finalizar el recorrido se marcaron todos los vértices,
//              realizar de nuevo el reccorrido iniciando por otro vértices,
//              hasta realizar realizar recorrido iniciando por cada vértice
//           -- Si al realizar un recorrido iniciando por cualquier vértice no quedan todos los vértices marcados,
//              me detengo y digo que el grafo dirigido no es fuertemente conexo
    public boolean fuertementeConexo(){
        Iterable<G> vertices=this.getVertices();
        for(G vertice:vertices){
            DFS<G> recorridoDFS=new DFS<>(this,vertice);
            if (!recorridoDFS.seVisitoTodosLosVertices()){
                return false;
            }
        }
        return true;
    }
//   5.- Para saber si un grafo dirigido es débilmente conexo
//       5.1.- Elegimos un vértice x perteneciente al grafo
//       5.2.- Iniciamos un recorrido por el vertice x electo
//       5.3.- si al terminar el recorrido quedan todos los vértices marcados,
//             entonces retornamos que el digrafo es debilmente conexo
//       5.4.- Si al terminar el recorrido,  no quedan todos los vértices marcados,
//             elegimos otro vértice x no marcado que tenga un adyacente marcado
//       5.5.- Si no existe otro vértice x no marcado con adyacente marcado,
//             entonces retornamos que el digrafo no es conexo
//       5.6.- Si existe otro vértice x no marcado con adyacente marcado,
//             continuamos el recorrido a partir del vértice x
//       5.7.- Volvemos al punto 5.3

    public boolean debilmenteConexo() {
        DFS<G> recorridoDFS = new DFS<>(this);
        G verticeEnTurno=this.listaDeVertices.getFirst();
        boolean cambioElVertice;
        do{
            recorridoDFS.ejecutarDFS(verticeEnTurno);
            if(recorridoDFS.seVisitoTodosLosVertices())break;
            Iterable<G> verticesNoMarcados= recorridoDFS.verticesNoMarcados();
            cambioElVertice=false;
            for(G verticeNoMarcado:verticesNoMarcados){
                Iterable<G> adyacentes=this.getAdyacentesDelVertice(verticeNoMarcado);
                for(G adyacente:adyacentes){
                    if(recorridoDFS.seVisitoVertice(adyacente)){
                        verticeEnTurno=verticeNoMarcado;
                        cambioElVertice=true;
                        break;
                    }
                    if(cambioElVertice)break;
                }
                if(!cambioElVertice)break;
            }
        }while(!recorridoDFS.seVisitoTodosLosVertices()&&cambioElVertice);
        return recorridoDFS.seVisitoTodosLosVertices();
    }

    @Override
    public boolean hayCiclo() throws ExcepcionAristaYaExiste {
        Warshall<G> matrizDeCaminos=new Warshall<>(this);
        return matrizDeCaminos.hayCiclos();
    }

    public void imprimirIslas(){
        Islas<G> islas=new Islas<>(this);
        islas.imprimirIslas();
    }

    public final class Islas<G extends Comparable<G>>{
        private final DiGrafo<G> elDiGrafo;
        private final ControlMarcados controlMarcados;
        private List<List<G>> recorrido;

        public Islas(DiGrafo<G> unDigrafo){
            this.elDiGrafo=unDigrafo;
            this.controlMarcados=new ControlMarcados(elDiGrafo.cantidadDeVertices());
            this.recorrido=new ArrayList<>();
            int i=0;
            List<G> vertices=(List<G>)elDiGrafo.getVertices();
            while(!controlMarcados.estanTodosMarcados()) {
                recorrido.add(new ArrayList<>());
                ejecutarIslas(vertices.getFirst());
                vertices=this.verticesNoMarcados();
            }
        }

        private void ejecutarIslas(G verticeEnTurno){
            elDiGrafo.validarVertice(verticeEnTurno);
            int nroVerticeEnTurno=elDiGrafo.nroVertice(verticeEnTurno);
            controlMarcados.marcar(nroVerticeEnTurno);
            recorrido.getLast().add(verticeEnTurno);
            Iterable<G> adysDelVertice= elDiGrafo.getAdyacentesDelVertice(verticeEnTurno);
            for(G adyEnTurno: adysDelVertice){
                int nroAdy=elDiGrafo.nroVertice(adyEnTurno);
                if(!controlMarcados.estaMarcadoVertice(nroAdy)){
                    ejecutarIslas(adyEnTurno);
                }
            }
            if(!controlMarcados.estanTodosMarcados()){
                List<G> vertices=(ArrayList<G>) elDiGrafo.getVertices();
                for(int i=0;i<elDiGrafo.cantidadDeVertices()&&!controlMarcados.estanTodosMarcados();i++) {
                    int nro = elDiGrafo.nroVertice(vertices.get(i));
                    if (!controlMarcados.estaMarcadoVertice(nro)) {
                        List<G> adysPosibles = (ArrayList<G>) elDiGrafo.getAdyacentesDelVertice(vertices.get(i));
                        for (int j=0;j<adysPosibles.size();j++) {
                            int nroAdy = elDiGrafo.nroVertice(adysPosibles.get(j));
                            if (controlMarcados.estaMarcadoVertice(nroAdy)) {
                                ejecutarIslas(vertices.get(i));
                            }
                        }
                    }
                }
            }
        }

        public List<G> verticesNoMarcados(){
            List<G> verts=new ArrayList<>();
            for(G vertices:elDiGrafo.getVertices()){
                if(!controlMarcados.estaMarcadoVertice(elDiGrafo.nroVertice(vertices))){
                    verts.add(vertices);
                }
            }
            return verts;
        }

        public void imprimirIslas() {
            for (int isla = 0; isla < recorrido.size(); isla++){
                System.out.println("Isla nro (" +(isla+1) + ") :" + recorrido.get(isla).toString());
            }
        }
    }


}
