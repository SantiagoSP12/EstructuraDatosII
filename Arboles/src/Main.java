import arboles.*;
import arboles.Excepciones.*;
import grafos.nopesados.*;
import grafos.pesados.*;

//robertovacapinto@uagrm.edu.bo
//Asunto: Proyecto 1 - INF310 - 202402 - Santiago Contreras Fuentes
public class Main {

    public static void main(String[] args) throws Exception {

        //Test 1 Recorridos DFS y BFS
//        DiGrafo<String> unGrafo=new DiGrafo<>();
//        unGrafo.insertarVertice("A");
//        unGrafo.insertarVertice("B");
//        unGrafo.insertarVertice("C");
//        unGrafo.insertarVertice("D");
//        unGrafo.insertarVertice("E");
//        unGrafo.insertarVertice("F");
//        unGrafo.insertarVertice("G");
//        unGrafo.insertarVertice("H");
//        unGrafo.insertarVertice("I");
//        unGrafo.insertarVertice("K");
//        unGrafo.insertarArista("A","B");
//        unGrafo.insertarArista("B","D");
//        unGrafo.insertarArista("D","E");
//        unGrafo.insertarArista("E","B");
//        unGrafo.insertarArista("A","C");
//        unGrafo.insertarArista("A","G");
//        unGrafo.insertarArista("C","E");
//        unGrafo.insertarArista("F","G");
//        unGrafo.insertarArista("F","D");
//        unGrafo.insertarArista("G","E");
//        unGrafo.insertarArista("G","H");
//        unGrafo.insertarArista("G","I");
//        unGrafo.insertarArista("I","F");
//        unGrafo.insertarArista("K","G");
//
//        System.out.println(unGrafo.toString());
//        DFS recorridoD=new DFS<>(unGrafo,"A");
//        System.out.println(recorridoD.getRecorrido());
//        BFS recorridoB=new BFS<>(unGrafo,"A");
//        System.out.println(recorridoB.getRecorrido());

//        //Test 2 Warshall
//        Grafo<String> unDiGrafo=new DiGrafo<>();
//        unDiGrafo.insertarVertice("A");
//        unDiGrafo.insertarVertice("B");
//        unDiGrafo.insertarVertice("C");
//        unDiGrafo.insertarVertice("D");
//        unDiGrafo.insertarVertice("E");
//
//        unDiGrafo.insertarArista("A","B");
//        unDiGrafo.insertarArista("B","D");
//        unDiGrafo.insertarArista("B","E");
//        unDiGrafo.insertarArista("C","C");
//        unDiGrafo.insertarArista("C","E");
//        unDiGrafo.insertarArista("D","B");
//        unDiGrafo.insertarArista("E","C");
//
//        System.out.println(unDiGrafo);
//        Warshall<String> warshall=new Warshall<>(unDiGrafo);
//        System.out.println(warshall);


//        //test 3 Ordenamiento Topoligico 1
//        DiGrafo<Integer> unGrafo=new DiGrafo<>();
//
//        unGrafo.insertarVertice(1);
//        unGrafo.insertarVertice(2);
//        unGrafo.insertarVertice(3);
//        unGrafo.insertarVertice(4);
//        unGrafo.insertarVertice(5);
//
//        unGrafo.insertarArista(1,2);
//        unGrafo.insertarArista(1,3);
//        unGrafo.insertarArista(1,4);
//        unGrafo.insertarArista(1,5);
//        unGrafo.insertarArista(2,4);
//        unGrafo.insertarArista(3,2);
//        unGrafo.insertarArista(3,5);
//        unGrafo.insertarArista(4,5);
//
//        System.out.println(unGrafo);
//        System.out.println(unGrafo.hayCiclo());
//        OrdenamientoTopológico<String> ot=new OrdenamientoTopológico(unGrafo);
//        System.out.println(ot.getRecorrido());
//
//        //test 4 Ordenamiento Topologico 2
//        DiGrafo<Integer> unGrafo2=new DiGrafo<>();
//
//        unGrafo2.insertarVertice(1);
//        unGrafo2.insertarVertice(2);
//        unGrafo2.insertarVertice(3);
//
//        unGrafo2.insertarArista(1,3);
//        unGrafo2.insertarArista(2,3);
//
//        System.out.println(unGrafo2);
//        System.out.println(unGrafo2.hayCiclo());
//
//        OrdenamientoTopológico<String> ot2=new OrdenamientoTopológico(unGrafo2);
//        System.out.println(ot2.getRecorrido());


//        //Test 5 Grafos Pesados
//        DiGrafoPesado<Integer> unGrafoPesado=new DiGrafoPesado<>();
//
//        unGrafoPesado.insertarVertice(0);
//        unGrafoPesado.insertarVertice(1);
//        unGrafoPesado.insertarVertice(2);
//        unGrafoPesado.insertarVertice(3);
//
//        unGrafoPesado.insertarArista(0,2,20);
//        unGrafoPesado.insertarArista(0,3,30);
//        unGrafoPesado.insertarArista(1,2,40);
//        unGrafoPesado.insertarArista(2,1,5);
//        unGrafoPesado.insertarArista(2,3,100);
//
//        System.out.println(unGrafoPesado+"\n grafo impreso");
//        unGrafoPesado.eliminarVertice(1);
//        System.out.println(unGrafoPesado+"\n grafo impreso");

//        //Test 6 Dijkstra
//        DiGrafoPesado<String> unDiGrafoPesado=new DiGrafoPesado<>();
//        unDiGrafoPesado.insertarVertice("M");
//        unDiGrafoPesado.insertarVertice("H");
//        unDiGrafoPesado.insertarVertice("T");
//        unDiGrafoPesado.insertarVertice("A");
//        unDiGrafoPesado.insertarVertice("E");
//        unDiGrafoPesado.insertarVertice("K");
//
//        unDiGrafoPesado.insertarArista("M","H",50.0);
//        unDiGrafoPesado.insertarArista("M","T",10.0);
//        unDiGrafoPesado.insertarArista("M","E",60.0);
//        unDiGrafoPesado.insertarArista("M","K",100.0);
//        unDiGrafoPesado.insertarArista("H","A",50.0);
//        unDiGrafoPesado.insertarArista("H","E",15.0);
//        unDiGrafoPesado.insertarArista("T","H",5.0);
//        unDiGrafoPesado.insertarArista("A","M",80.0);
//        unDiGrafoPesado.insertarArista("A","K",20.0);
//        unDiGrafoPesado.insertarArista("E","K",20.0);
//        unDiGrafoPesado.insertarArista("K","H",40.0);
//        unDiGrafoPesado.insertarArista("K","T",70.0);
//
//        System.out.println(unDiGrafoPesado);
//
//        Dijkstra<String> dijkstra=new Dijkstra<>(unDiGrafoPesado);
//        dijkstra.ejecutarDijkstra("M","A");
//
//        System.out.println(dijkstra.getCostoMinimo());
//        System.out.println(dijkstra.getRecorrido().toString());
//

//        //Test 7 Kruskal y Prim
//        GrafoPesado<Integer> unGrafoPesado = new GrafoPesado<>();
//        unGrafoPesado.insertarVertice(1);
//        unGrafoPesado.insertarVertice(2);
//        unGrafoPesado.insertarVertice(3);
//        unGrafoPesado.insertarVertice(4);
//        unGrafoPesado.insertarVertice(5);
//        unGrafoPesado.insertarVertice(6);
//        unGrafoPesado.insertarVertice(7);
//        unGrafoPesado.insertarVertice(8);
//        unGrafoPesado.insertarVertice(9);
//        unGrafoPesado.insertarVertice(10);
//
//        unGrafoPesado.insertarArista(1,2,5);
//        unGrafoPesado.insertarArista(1,3,10);
//        unGrafoPesado.insertarArista(1,4,8);
//        unGrafoPesado.insertarArista(2,4,6);
//        unGrafoPesado.insertarArista(2,6,5);
//        unGrafoPesado.insertarArista(3,4,7);
//        unGrafoPesado.insertarArista(3,5,8);
//        unGrafoPesado.insertarArista(3,8,15);
//        unGrafoPesado.insertarArista(4,5,5);
//        unGrafoPesado.insertarArista(4,6,11);
//        unGrafoPesado.insertarArista(5,7,4);
//        unGrafoPesado.insertarArista(5,8,3);
//        unGrafoPesado.insertarArista(6,7,9);
//        unGrafoPesado.insertarArista(6,9,7);
//        unGrafoPesado.insertarArista(7,8,12);
//        unGrafoPesado.insertarArista(7,9,4);
//        unGrafoPesado.insertarArista(7,10,6);
//        unGrafoPesado.insertarArista(8,10,12);
//        unGrafoPesado.insertarArista(9,10,7);
//
//        System.out.println(unGrafoPesado);
//        Kruskal<Integer> kruskal=new Kruskal<>(unGrafoPesado);
//        kruskal.imprimirAristas();
//        System.out.println(kruskal);
//
//        Prim<Integer> prim=new Prim<>(unGrafoPesado,1);
//        System.out.println(prim);

//        //Test 8 Floyd
//        GrafoPesado<String> unDiGrafoPesado=new DiGrafoPesado<>();
//        unDiGrafoPesado.insertarVertice("V1");
//        unDiGrafoPesado.insertarVertice("V2");
//        unDiGrafoPesado.insertarVertice("V3");
//        unDiGrafoPesado.insertarVertice("V4");
//        unDiGrafoPesado.insertarVertice("V5");
//
//        unDiGrafoPesado.insertarArista("V1","V2",1.0);
//        unDiGrafoPesado.insertarArista("V2","V4",4.0);
//        unDiGrafoPesado.insertarArista("V2","V5",7.0);
//        unDiGrafoPesado.insertarArista("V3","V1",3.0);
//        unDiGrafoPesado.insertarArista("V3","V2",2.0);
//        unDiGrafoPesado.insertarArista("V3","V5",4.0);
//        unDiGrafoPesado.insertarArista("V4","V1",6.0);
//        unDiGrafoPesado.insertarArista("V4","V5",2.0);
//        unDiGrafoPesado.insertarArista("V5","V4",3.0);
//
//        System.out.println(unDiGrafoPesado);
//        Floyd<String> floyd=new Floyd<>(unDiGrafoPesado);
//        System.out.println(floyd);
//        System.out.println(floyd.costoMinimo("V1","V5"));
//        System.out.println(floyd.caminoCostoMinimo("V1","V5").toString());
//
//        GrafoPesado<String> unDiGrafoPesado2=new DiGrafoPesado<>();
//        unDiGrafoPesado2.insertarVertice("C");
//        unDiGrafoPesado2.insertarVertice("B");
//        unDiGrafoPesado2.insertarVertice("X");
//        unDiGrafoPesado2.insertarVertice("F");
//        unDiGrafoPesado2.insertarVertice("Y");
//
//        unDiGrafoPesado2.insertarArista("C","F",30.0);
//        unDiGrafoPesado2.insertarArista("B","C",70.0);
//        unDiGrafoPesado2.insertarArista("B","X",40.0);
//        unDiGrafoPesado2.insertarArista("B","Y",70.0);
//        unDiGrafoPesado2.insertarArista("X","Y",20.0);
//        unDiGrafoPesado2.insertarArista("F","B",25.0);
//        unDiGrafoPesado2.insertarArista("F","X",100.0);
//        unDiGrafoPesado2.insertarArista("Y","C",5.0);
//
//        System.out.println(unDiGrafoPesado2);
//        Floyd<String> floyd2=new Floyd<>(unDiGrafoPesado2);
//        System.out.println(floyd2);
//        System.out.println(floyd2.costoMinimo("B","F"));
//        System.out.println(floyd2.caminoCostoMinimo("B","F").toString());
//
//        System.out.println(floyd2.costoMinimo("F","Y"));
//        System.out.println(floyd2.caminoCostoMinimo("F","Y").toString());
        //Test 9 Ford-Fulkerson
        DiGrafoPesado<String> unDiGrafoPesado = new DiGrafoPesado<>();
        unDiGrafoPesado.insertarVertice("G");
        unDiGrafoPesado.insertarVertice("A");
        unDiGrafoPesado.insertarVertice("B");
        unDiGrafoPesado.insertarVertice("C");
        unDiGrafoPesado.insertarVertice("D");
        unDiGrafoPesado.insertarVertice("E");
        unDiGrafoPesado.insertarVertice("F");
        unDiGrafoPesado.insertarVertice("H");

        unDiGrafoPesado.insertarArista("A","D",50.0);
        unDiGrafoPesado.insertarArista("B","A",20.0);
        unDiGrafoPesado.insertarArista("B","F",20.0);
        unDiGrafoPesado.insertarArista("C","E",20.0);
        unDiGrafoPesado.insertarArista("C","F",30.0);
        unDiGrafoPesado.insertarArista("D","E",15.0);
        unDiGrafoPesado.insertarArista("D","H",70.0);
        unDiGrafoPesado.insertarArista("E","D",45.0);
        unDiGrafoPesado.insertarArista("E","H",40.0);
        unDiGrafoPesado.insertarArista("F","H",10.0);
        unDiGrafoPesado.insertarArista("G","A",20.0);
        unDiGrafoPesado.insertarArista("G","B",40.0);
        unDiGrafoPesado.insertarArista("G","C",10.0);

        System.out.println(unDiGrafoPesado);
        FordFulkerson<String> fordFulkerson=new FordFulkerson<>(unDiGrafoPesado);
        System.out.println(fordFulkerson.getFlujoMaximo());
        fordFulkerson.imprimirFlujos();

    }


    public void arbolesExecute() throws ExcepcionOrdenInvalido{
        AMV<Integer> arbolBusqueda=new AMV<>(4);

        /*test run Insertar como en el Documento del inge
        NodoMVias<Integer> nodoAct=new NodoMVias<>(4);
        nodoAct.setDato(0,90);
        nodoAct.setDato(1,850);

        NodoMVias<Integer> hijo0=new NodoMVias<>(4);
        NodoMVias<Integer> hijo1=new NodoMVias<>(4);
        NodoMVias<Integer> hijo2=new NodoMVias<>(4);
        NodoMVias<Integer> hijo0hijo0=new NodoMVias<>(4);
        NodoMVias<Integer> hijo0hijo1=new NodoMVias<>(4);
        NodoMVias<Integer> hijo1hijo0=new NodoMVias<>(4);
        NodoMVias<Integer> hijo1hijo1=new NodoMVias<>(4);
        NodoMVias<Integer> hijo1hijo2=new NodoMVias<>(4);
        NodoMVias<Integer> hijo2hijo0=new NodoMVias<>(4);
        NodoMVias<Integer> hijo2hijo1=new NodoMVias<>(4);
        NodoMVias<Integer> hijo2hijo2=new NodoMVias<>(4);

        hijo0.setDato(0,70);
        hijo0hijo0.setDato(0,50);
        hijo0hijo1.setDato(0,75);

        hijo0.setHijo(0,hijo0hijo0);
        hijo0.setHijo(1,hijo0hijo1);
        nodoAct.setHijo(0,hijo0);


        hijo1.setDato(0,100);
        hijo1.setDato(1,400);
        hijo1hijo0.setDato(0,91);
        hijo1hijo0.setDato(1,99);
        hijo1hijo1.setDato(0,300);
        hijo1hijo2.setDato(0,500);
        hijo1hijo2.setDato(1,800);

        hijo1.setHijo(0,hijo1hijo0);
        hijo1.setHijo(1,hijo1hijo1);
        hijo1.setHijo(2,hijo1hijo2);
        nodoAct.setHijo(1,hijo1);


        hijo2.setDato(0,870);
        hijo2.setDato(1,920);
        hijo2hijo0.setDato(0,855);
        hijo2hijo0.setDato(1,862);
        hijo2hijo0.setDato(2,868);
        hijo2hijo1.setDato(0,890);
        hijo2hijo2.setDato(0,950);
        hijo2hijo2.setDato(1,960);

        hijo2.setHijo(0,hijo2hijo0);
        hijo2.setHijo(1,hijo2hijo1);
        hijo2.setHijo(2,hijo2hijo2);
        nodoAct.setHijo(2,hijo2);

        arbol.testRun(nodoAct);*/


        /*arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(15);
        arbol.eliminar(15);
        System.out.println(arbol);
        arbol.insertar(1);
        arbol.insertar(22);
        arbol.insertar(25);
        arbol.insertar(40);
        arbol.insertar(32);
        arbol.insertar(90);
        arbol.insertar(12);
        arbol.insertar(2);
        arbol.insertar(3);
        arbol.insertar(95);
        arbol.insertar(50);
        arbol.insertar(6);
        arbol.insertar(77);
        arbol.insertar(58);
        arbol.insertar(88);
        arbol.insertar(19);
        arbol.insertar(92);
        arbol.insertar(49);
        arbol.insertar(33);
        arbol.insertar(65);
        arbol.insertar(74);
        arbol.insertar(89);
        arbol.insertar(98);

        arbol.eliminar(95);
        System.out.println(arbol.toString());


        arbol.eliminar(58);
        System.out.println(arbol.toString());
        arbol.eliminar(19);*/
        //System.out.println(arbolBusqueda.toString());
        /*test run eliminar como en el Documento del inge
        arbol.eliminar(862);
        System.out.println(arbol.toString());
        arbol.eliminar(300);
        System.out.println(arbol.toString());
        arbol.eliminar(400);
        System.out.println(arbol.toString());
        arbol.eliminar(91);
        System.out.println(arbol.toString());
        arbol.eliminar(870);
        System.out.println(arbol.toString());
        arbol.eliminar(70);
        System.out.println(arbol.toString());*/
        arbolBusqueda.insertar(313);
        arbolBusqueda.insertar(314);
        arbolBusqueda.insertar(321);
        arbolBusqueda.insertar(322);
        arbolBusqueda.insertar(304);
        arbolBusqueda.insertar(300);
        arbolBusqueda.insertar(301);
        arbolBusqueda.insertar(302);
        arbolBusqueda.insertar(303);
        arbolBusqueda.insertar(305);
        arbolBusqueda.insertar(306);
        arbolBusqueda.insertar(307);
        arbolBusqueda.insertar(308);
        arbolBusqueda.insertar(309);
        arbolBusqueda.insertar(310);

        arbolBusqueda.insertar(318);
        arbolBusqueda.insertar(319);
        arbolBusqueda.insertar(320);
        arbolBusqueda.insertar(323);

        arbolBusqueda.insertar(400);
        arbolBusqueda.insertar(401);
        arbolBusqueda.insertar(402);
        arbolBusqueda.insertar(311);
        arbolBusqueda.insertar(312);

        arbolBusqueda.insertar(315);
        arbolBusqueda.insertar(316);
        arbolBusqueda.insertar(317);
        arbolBusqueda.insertar(403);
        arbolBusqueda.insertar(324);
        arbolBusqueda.insertar(325);
        arbolBusqueda.insertar(326);
        arbolBusqueda.insertar(327);
        arbolBusqueda.insertar(328);

        arbolBusqueda.insertar(404);

        System.out.println(arbolBusqueda.toString());

        System.out.println(arbolBusqueda.buscar(309));

        arbolBusqueda.eliminar(309);
        System.out.println(arbolBusqueda.toString());
        System.out.println(arbolBusqueda.recorridoEnInOrden().toString());
    }

}