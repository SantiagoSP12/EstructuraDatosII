package grafos.pesados;

import grafos.excepciones.ExcepcionAristaNoExiste;
import grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal<G extends Comparable<G>> extends GrafoPesado<G>{
    private final List<TriadaOrigenDestinoPeso<G>> listaDeAristas;

    private record TriadaOrigenDestinoPeso<G extends Comparable<G>>(G origen, G destino, double costo)
                implements Comparable<TriadaOrigenDestinoPeso<G>> {

            @Override
            public int compareTo(TriadaOrigenDestinoPeso o) {
                if (this.costo < o.costo) return -1;
                if (this.costo > o.costo) return 1;
                return 0;
            }

            @Override
            public boolean equals(Object otro) {
                if (otro == null) {
                    return false;
                }
                if (getClass() != otro.getClass()) {
                    return false;
                }
                TriadaOrigenDestinoPeso<G> aristaSinCosto = (TriadaOrigenDestinoPeso<G>) otro;
                return (this.origen.compareTo(aristaSinCosto.origen) == 0) &&
                        (this.destino.compareTo(aristaSinCosto.destino) == 0);
            }

            @Override
            public String toString() {
                return "[" + (String.format("%3d", origen)) + "->" + (String.format("%3d", destino)) + ":" + (String.format("%6.1f", costo)) + "]";
            }
        }


    public Kruskal(GrafoPesado<G> unGrafoPesado){
        super(unGrafoPesado.getVertices());
        try {
            this.listaDeAristas = new ArrayList<>();
            for (int i = 0; i < listaDeVertices.size(); i++) {
                List<AdyacenteConPeso> adyacentesDeUnVertice = unGrafoPesado.listasDeAdyacencias.get(i);
                G origen = listaDeVertices.get(i);
                for (int j = 0; j < adyacentesDeUnVertice.size(); j++) {
                    G destino = listaDeVertices.get(adyacentesDeUnVertice.get(j).getNroVertice());
                    double costo = adyacentesDeUnVertice.get(j).getPeso();
                    TriadaOrigenDestinoPeso<G> comprobante = new TriadaOrigenDestinoPeso<>(destino, origen, 0.0);
                    if (!listaDeAristas.contains(comprobante)) {
                        listaDeAristas.add(new TriadaOrigenDestinoPeso<>(origen, destino, costo));
                    }
                }
            }
            Collections.sort(listaDeAristas);
            ejecutarKruskal();
        }catch (ExcepcionAristaYaExiste | ExcepcionAristaNoExiste e){
            throw new RuntimeException(e);
        }
    }

    private void ejecutarKruskal() throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        for(TriadaOrigenDestinoPeso<G> arista:listaDeAristas){
            this.insertarArista(arista.origen,arista.destino,arista.costo);
            if(this.hayCiclo()){
                this.eliminarArista(arista.origen,arista.destino);
            }
        }
    }

    public void imprimirAristas(){
        for(TriadaOrigenDestinoPeso<G> triada:listaDeAristas){
            System.out.println(triada.toString());
        }
    }

}
