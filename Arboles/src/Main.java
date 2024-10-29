import arboles.*;
import arboles.Excepciones.ExcepcionOrdenInvalido;

public class Main {

    public static void main(String[] args) throws ExcepcionOrdenInvalido {
        AMV<Integer> arbol=new AMV<>();
        arbol.insertar(10);
        arbol.insertar(20);//did not
        System.out.println(arbol.toString());
        arbol.insertar(15);
        System.out.println(arbol.toString());
        arbol.insertar(1);
        System.out.println(arbol.toString());
        arbol.insertar(22);
        System.out.println(arbol.toString());
        arbol.insertar(25);
        System.out.println(arbol.toString());
        arbol.insertar(40);
        System.out.println(arbol.toString());
        arbol.insertar(32);
        System.out.println(arbol.toString());
        arbol.insertar(90);
        System.out.println(arbol.toString());
        arbol.insertar(12);
        System.out.println(arbol.toString());/*
        arbol.insertar(2);
        System.out.println(arbol.toString());
        arbol.insertar(3);
        System.out.println(arbol.toString());
        arbol.insertar(95);
        System.out.println(arbol.toString());
        arbol.insertar(50);
        System.out.println(arbol.toString());
        arbol.insertar(6);
        System.out.println(arbol.toString());/*
        arbol.insertar(77);
        System.out.println(arbol.toString());
        arbol.insertar(58);
        System.out.println(arbol.toString());
        arbol.insertar(88);
        System.out.println(arbol.toString());/*
        arbol.insertar(19);
        System.out.println(arbol.toString());
        arbol.insertar(92);
        System.out.println(arbol.toString());
        arbol.insertar(49);
        System.out.println(arbol.toString());
        arbol.insertar(33);
        System.out.println(arbol.toString());
        arbol.insertar(65);
        System.out.println(arbol.toString());
        arbol.insertar(74);
        System.out.println(arbol.toString());
        arbol.insertar(89);
        System.out.println(arbol.toString());
        arbol.insertar(98);
        System.out.println(arbol.toString());*/

    }
}