package grafos.pesados;

//Floyd es la alternativa mas elegante y mas directa que el algoritmo de disjkstra, se asemeja a warshall
//ocupa una matriz de adyacencias on peso, con ciertos detalles
//  la diagonal principal se rellena con ceros, (no se usa)
//  se respetan las demas adyacencias, y donde no hay adyacencia se rellena con infinitos(o el valor maximo posible del integer)
//  esa es la matriz inicial
//toca pivotear, Matriz Po(fila 0, columna 0),
//if(M[1,2]>(M[1,0]+M[0,2]))M[1,2]=M[1,0]+[0,2]
//si se quiere saber por que camino se toma el costo minimo, se crea otra matriz inicial (Predecesores)
//  las que no se usan se marcan con indice inválido (M[i,i]=-1), los demas se marcan con su columna
//si se cumple el if, entonces Predecesores[i,j]= pivote
//ejemplo
//   0  1  2  3  4        0  1  2  3  4
//0[ 0  1  n  n  n ]  0 [ -  1  2  3  4 ]
//1[ n  0  n  4  7 ]  1 [ 0  -  2  3  4 ]
//2[ 3  2  0  n  4 ]  2 [ 0  1  -  3  4 ]
//3[ 6  n  n  0  2 ]  3 [ 0  1  2  -  4 ]
//4[ n  n  n  3  0 ]  4 [ 0  1  2  3  - ]
//P0, P[1,3]=7
//   0  1  2  3  4        0  1  2  3  4
//0[ 0  1  n  n  n ]  0 [ -  1  2  3  4 ]
//1[ n  0  n  4  7 ]  1 [ 0  -  2  3  4 ]
//2[ 3  2  0  n  4 ]  2 [ 0  1  -  3  4 ]
//3[ 6  7  n  0  2 ]  3 [ 0  0  2  -  4 ]
//4[ n  n  n  3  0 ]  4 [ 0  1  2  3  - ]
//P1, P[0,3]=5, P[0,4]=8, P[2,3]=6
//   0  1  2  3  4        0  1  2  3  4
//0[ 0  1  n  5  8 ]  0 [ -  1  2  1  1 ]
//1[ n  0  n  4  7 ]  1 [ 0  -  2  3  4 ]
//2[ 3  2  0  6  4 ]  2 [ 0  1  -  1  4 ]
//3[ 6  7  n  0  2 ]  3 [ 0  0  2  -  4 ]
//4[ n  n  n  3  0 ]  4 [ 0  1  2  3  - ]
//P2, nil
//   0  1  2  3  4        0  1  2  3  4
//0[ 0  1  n  5  8 ]  0 [ -  1  2  1  1 ]
//1[ n  0  n  4  7 ]  1 [ 0  -  2  3  4 ]
//2[ 3  2  0  6  4 ]  2 [ 0  1  -  1  4 ]
//3[ 6  7  n  0  2 ]  3 [ 0  0  2  -  4 ]
//4[ n  n  n  3  0 ]  4 [ 0  1  2  3  - ]
//P3, P[0,4]=7, P[1,0]=10, P[1,4]=6, P[4,0]=9, P[4,1]=10
//   0  1  2  3  4        0  1  2  3  4
//0[ 0  1  n  5  7 ]  0 [ -  1  2  1  3 ]
//1[10  0  n  4  6 ]  1 [ 3  -  2  3  3 ]
//2[ 3  2  0  6  4 ]  2 [ 0  1  -  1  4 ]
//3[ 6  7  n  0  2 ]  3 [ 0  0  2  -  4 ]
//4[ 9 10  n  3  0 ]  4 [ 3  3  2  3  - ]
//P4, nil
//   0  1  2  3  4        0  1  2  3  4
//0[ 0  1  n  5  7 ]  0 [-1  1  2  1  3 ]
//1[10  0  n  4  6 ]  1 [ 3 -1  2  3  3 ]
//2[ 3  2  0  6  4 ]  2 [ 0  1 -1  1  4 ]
//3[ 6  7  n  0  2 ]  3 [ 0  0  2 -1  4 ]
//4[ 9 10  n  3  0 ]  4 [ 3  3  2  3 -1 ]

//hay camino entre dos vertices
//V1->V5? 0->4=7
//0->4
//0->3->4
//(0->3)^(3->4)
//(0->1->3)->4
//((0->1)^(1->3))->4
//(0->1->3)->4
//0->1->3->4

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//0 C
//1 B
//2 X
//3 F
//4 Y
//   0  1  2  3  4        0  1  2  3  4
//0[ 0 55 95 30 115]  0 [-1  1  2  1  3 ]
//1[65  0 40 95 60 ]  1 [ 3 -1  2  3  3 ]
//2[ 3  2  0  6  4 ]  2 [ 0  1 -1  1  4 ]
//3[ 6  7  n  0  2 ]  3 [ 0  0  2 -1  4 ]
//4[ 9 10  n  3  0 ]  4 [ 3  3  2  3 -1 ]
public class Floyd<G extends Comparable<G>> extends DiGrafoPesadoM<G>{

    private final List<List<Integer>> predecesores;

    public Floyd(GrafoPesado<G> unGrafo){
        super(unGrafo);
        for(int i=0;i<matrizDeAdyacencias.size();i++){
            List<Double> fila=matrizDeAdyacencias.get(i);
            for (int j=0;j<fila.size();j++){
                if(i==j){
                    matrizDeAdyacencias.get(i).set(j,0.0);
                } else if (matrizDeAdyacencias.get(i).get(j)==0.0) {
                    matrizDeAdyacencias.get(i).set(j,Double.POSITIVE_INFINITY);
                }
            }
        }
        this.predecesores=new ArrayList<>();
        iniciarPredecesores();
        ejecutarFloyd();
    }

    private void ejecutarFloyd(){
        for(int k=0;k<cantidadDeVertices();k++){
            for(int i=0;i<cantidadDeVertices();i++){
                for(int j=0;j<cantidadDeVertices();j++){
                    if(i!=k&&j!=k) {
                        double peso = matrizDeAdyacencias.get(i).get(k);
                        peso += matrizDeAdyacencias.get(k).get(j);
                        if (matrizDeAdyacencias.get(i).get(j) > (peso)) {
                            matrizDeAdyacencias.get(i).set(j, peso);
                            predecesores.get(i).set(j, k);
                        }
                    }
                }
            }
        }
    }

    private void iniciarPredecesores(){
        int size=this.cantidadDeVertices();
        for(int i=0;i<size;i++){
            List<Integer> nuevaFila=new ArrayList<>();
            for(int j=0;j<size;j++){
                if(i==j){
                    nuevaFila.add(-1);
                }else{
                    nuevaFila.add(j);
                }
            }
            this.predecesores.add(nuevaFila);
        }
    }

    public double costoMinimo(G origen,G destino){
        validarVertice(origen);
        validarVertice(destino);
        int nroOrigen=this.nroVertice(origen);
        int nroDestino=this.nroVertice(destino);
        Stack<Integer> pilaDePredecesores=new Stack<>();
        Double costoMinimo=0.0;
        pilaDePredecesores.push(nroDestino);
        pilaDePredecesores.push(nroOrigen);
        do {
            nroOrigen=pilaDePredecesores.pop();
            nroDestino=pilaDePredecesores.pop();
            if(predecesores.get(nroOrigen).get(nroDestino)!=nroDestino){
                pilaDePredecesores.push(nroDestino);
                pilaDePredecesores.push(predecesores.get(nroOrigen).get(nroDestino));
                pilaDePredecesores.push(nroOrigen);
            }else{
                costoMinimo+=matrizDeAdyacencias.get(nroOrigen).get(nroDestino);
                if(!pilaDePredecesores.isEmpty()){
                    pilaDePredecesores.push(nroDestino);
                }
            }
        }while(!pilaDePredecesores.isEmpty());
        return costoMinimo;
    }

    public List<G> caminoCostoMinimo(G origen, G destino){
        validarVertice(origen);
        validarVertice(destino);
        int nroOrigen=this.nroVertice(origen);
        int nroDestino=this.nroVertice(destino);
        Stack<Integer> pilaDePredecesores=new Stack<>();
        List<G> recorrido=new ArrayList<>();
        pilaDePredecesores.push(nroDestino);
        pilaDePredecesores.push(nroOrigen);
        do {
            nroOrigen=pilaDePredecesores.pop();
            nroDestino=pilaDePredecesores.pop();
            if(predecesores.get(nroOrigen).get(nroDestino)!=nroDestino){
                pilaDePredecesores.push(nroDestino);
                pilaDePredecesores.push(predecesores.get(nroOrigen).get(nroDestino));
                pilaDePredecesores.push(nroOrigen);
            }else{
                recorrido.add(listaDeVertices.get(nroOrigen));
                if(pilaDePredecesores.isEmpty()){
                    recorrido.add(listaDeVertices.get(nroDestino));
                }else{
                    pilaDePredecesores.push(nroDestino);
                }
            }
        }while(!pilaDePredecesores.isEmpty());
        return recorrido;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Grafo no pesado{\n        ");
        for(G vertice:listaDeVertices){
            sb.append(vertice);
            for(int i=vertice.toString().length();i<=6;i++){
                sb.append(" ");
            }
        }
        for(G vertice:listaDeVertices){
            sb.append(vertice);
            for(int i=vertice.toString().length();i<=2;i++){
                sb.append(" ");
            }
        }
        sb.append("\n");
        int i=0;
        for(G vertice:listaDeVertices){
            sb.append(vertice.toString());
            sb.append("[ ");
            for(Double adyacencia:matrizDeAdyacencias.get(i)){
                if(adyacencia!=Double.POSITIVE_INFINITY){
                    sb.append(String.format("%6.1f", adyacencia)).append(" ");
                }else{
                    sb.append("   Inf ");
                }
            }
            sb.append("\b]  [");
            for(Integer predecesor:predecesores.get(i)){
                sb.append(String.format("%3d",predecesor));
            }
            sb.append("]\n");
            i++;
        }
        return sb.append("}\n").toString();
    }
}
