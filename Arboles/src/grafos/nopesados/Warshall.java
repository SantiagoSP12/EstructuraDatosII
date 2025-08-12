package grafos.nopesados;

public class Warshall<G extends Comparable<G>> extends GrafoM<G>{

    // * Uso Matriz de Caminos (El algorito de warshall)
    //   1.- Para saber entre que vértices hay camino en un grafo dirigido
    //   2.- Para saber si hay ciclo(s) en un grafo dirigido
    //   3.- Para saber si un grafo dirigido es fuertemente conexo
    public Warshall(Grafo<G> grafoInicial) {
        super(grafoInicial);
        ejecutarWarshall();
    }

    private void ejecutarWarshall(){
        for(int k=0;k<this.listaDeVertices.size();k++){
            for(int i=0;i<this.listaDeVertices.size();i++){
                for(int j=0;j<this.listaDeVertices.size();j++){
                    if(i!=k&&j!=k&&matrizDeAdyacencias.get(i).get(k)&&
                        matrizDeAdyacencias.get(k).get(j)){
                        matrizDeAdyacencias.get(i).set(j,Boolean.TRUE);
                    }
                }
            }
        }
    }

    public boolean hayCamino(G verticeOrigen,G verticeDestino){
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroOrigen=nroVertice(verticeOrigen);
        int nroDestino=nroVertice(verticeDestino);
        return matrizDeAdyacencias.get(nroOrigen).get(nroDestino);
    }

    public boolean hayCiclos(){
        for(int i=0;i<this.listaDeVertices.size();i++){
            if(matrizDeAdyacencias.get(i).get(i)){
                return true;
            }
        }
        return false;
    }

    public boolean fuertementeConexo(){
        for (int i = 0; i < this.listaDeVertices.size(); i++) {
            for (int j = 0; j < this.listaDeVertices.size(); j++) {
                if(!matrizDeAdyacencias.get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Matriz de Caminos{\n   ");
        for(G vertice:listaDeVertices){
            sb.append(vertice+"  ");
        }
        sb.append("\n");
        int i=0;
        for(G vertice:listaDeVertices){
            sb.append(vertice.toString());
            sb.append("[ ");
            for(Boolean adyacencia:matrizDeAdyacencias.get(i)){
                if(adyacencia){
                    sb.append("1  ");
                }else{
                    sb.append("0  ");
                }
            }
            i++;
            sb.append("\b]\n");
        }
        return sb.append("}\n").toString();
    }
}
