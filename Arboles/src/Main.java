import arboles.*;
import arboles.Excepciones.ExcepcionOrdenInvalido;

public class Main {

    public static void main(String[] args) throws ExcepcionOrdenInvalido {
        AMV<Integer> arbol=new AB<>(4);
        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(15);
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

        arbol.eliminar(2);
        arbol.eliminar(25);
        arbol.eliminar(32);
        arbol.eliminar(20);
        arbol.eliminar(19);
        arbol.eliminar(15);
        System.out.println(arbol.toString());

        System.out.println(arbol.recorridoEnInOrden().toString());
    }
}