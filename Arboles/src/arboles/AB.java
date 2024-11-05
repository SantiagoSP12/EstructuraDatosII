package arboles;

import arboles.Excepciones.ExcepcionDatoNoExiste;
import arboles.Excepciones.ExcepcionDatoYaExiste;
import arboles.Excepciones.ExcepcionOrdenInvalido;

import javax.swing.plaf.synth.SynthSpinnerUI;
import java.util.Stack;

public class AB <T extends Comparable<T>> extends AMV<T>{
    private final int nroMaximoDeDatos;
    private final int nroMinimoDeDatos;
    private final int nroMinimoDeHijos;

    public AB(){
        super();//setea ya el orden, o lo que es lo mismo nroMaximoDeHijos en 3
        nroMaximoDeDatos=2;
        nroMinimoDeDatos=1;
        nroMinimoDeHijos=2;
    }

    public AB(int orden) throws ExcepcionOrdenInvalido{
        super(orden);
        nroMaximoDeDatos=super.orden-1;
        nroMinimoDeDatos=nroMaximoDeDatos/2;
        nroMinimoDeHijos=nroMinimoDeDatos+1;//puede que no lo usen
    }

    @Override
    public void insertar(T datoAInsertar) {
        if (datoAInsertar==null){
            throw new IllegalArgumentException("Dato a insertar no puede ser nulo");
        }
        if(this.esArbolVacio()){
            this.raiz=new NodoMVias<>(this.orden,datoAInsertar);
        }else{
            NodoMVias<T> nodoAux= this.raiz;
            Stack<NodoMVias<T>> pilaDeAncestros=new Stack<>();
            do{
                int posicionDeDatoEnNodo=this.buscarPosicionDeDatoEnNodo(nodoAux,datoAInsertar);
                if(posicionDeDatoEnNodo!=POSICION_INVALIDA){
                    throw new ExcepcionDatoYaExiste();
                }
                //En este punto punto sabemos que el datoAInsertar no está en el nodoAux
                if(nodoAux.esHoja()){
                    this.insertarDatoOrdenadoEnNodo(nodoAux,datoAInsertar);
                    if(nodoAux.nroDeDatosNoVacios()>this.nroMaximoDeDatos){
                        dividirNodo(nodoAux,pilaDeAncestros);
                    }
                    nodoAux=NodoMVias.nodoVacio();
                }else{
                    //en este punto el nodoAux no es hoja
                    int posicionPorDondeBajar=this.buscarPosicionParaBajar(nodoAux,datoAInsertar);
                    pilaDeAncestros.push(nodoAux);
                    nodoAux=nodoAux.getHijo(posicionPorDondeBajar);
                }
            }while(!NodoMVias.esNodoVacio(nodoAux));
        }//fin else
    }

    private void dividirNodo(NodoMVias<T> nodoAux, Stack<NodoMVias<T>> pilaDeAncestros) {
        T datoDelMedio=nodoAux.getDato((orden-1)/2);
        NodoMVias nodoIzquierdo=new NodoMVias<>(this.orden);
        NodoMVias nodoDerecho=new NodoMVias<>(this.orden);
        int division=(orden-1)/2;
        int contador=0;
        for(int i=0;i<division;i++){
            this.insertarDatoOrdenadoEnNodo(nodoIzquierdo,nodoAux.getDato(i));
            nodoIzquierdo.setHijo(i,nodoAux.getHijo(i));
        }
        nodoIzquierdo.setHijo(division,nodoAux.getHijo(division));
        for(int i=division+1;i<orden;i++){
            this.insertarDatoOrdenadoEnNodo(nodoDerecho,nodoAux.getDato(i));
            nodoDerecho.setHijo(contador,nodoAux.getHijo(i));
            contador++;
        }
        nodoDerecho.setHijo(contador,nodoAux.getHijo(orden));
        if(!pilaDeAncestros.isEmpty()){
            NodoMVias<T> nodo=pilaDeAncestros.pop();
            this.insertarDatoOrdenadoEnNodo(nodo,datoDelMedio);
            int posicion=this.buscarPosicionDeDatoEnNodo(nodo,datoDelMedio);
            nodo.setHijo(posicion,nodoIzquierdo);
            nodo.setHijo(posicion+1,nodoDerecho);
            if(nodo.nroDeDatosNoVacios()>this.nroMaximoDeDatos){
                dividirNodo(nodo,pilaDeAncestros);
            }
        }else{
            NodoMVias<T> nuevaRaiz= new NodoMVias<>(orden,datoDelMedio);
            nuevaRaiz.setHijo(0,nodoIzquierdo);
            nuevaRaiz.setHijo(1,nodoDerecho);
            this.raiz=nuevaRaiz;
        }
    }

    @Override
    public void eliminar(T datoAEliminar){
        if(datoAEliminar==null){
            throw new IllegalArgumentException("Dato a eliminar no puede ser nulo");
        }
        NodoMVias<T> nodoAux=this.raiz;
        NodoMVias nodoDelDatoAEliminar=NodoMVias.nodoVacio();
        Stack<NodoMVias<T>> pilaDeAncestros=new Stack<>();
        int posicionDeDatoAEliminar=POSICION_INVALIDA;
        do{
            posicionDeDatoAEliminar=this.buscarPosicionDeDatoEnNodo(nodoAux,datoAEliminar);
            if(posicionDeDatoAEliminar!=POSICION_INVALIDA){
                nodoDelDatoAEliminar=nodoAux;
                nodoAux=NodoMVias.nodoVacio();
            }else{
                int posicionPorDondeBajar=buscarPosicionParaBajar(nodoAux,datoAEliminar);
                pilaDeAncestros.push(nodoAux);
                nodoAux=nodoAux.getHijo(posicionPorDondeBajar);
            }
        }while(!NodoMVias.esNodoVacio(nodoAux));
        if(NodoMVias.esNodoVacio(nodoDelDatoAEliminar)){
            throw new ExcepcionDatoNoExiste();
        }
        if(nodoDelDatoAEliminar.esHoja()){
            super.eliminarElDatoDelNodo(nodoDelDatoAEliminar,posicionDeDatoAEliminar);
            if(nodoDelDatoAEliminar.nroDeDatosNoVacios()<nroMinimoDeDatos){
                prestarseOFusionar(nodoDelDatoAEliminar,pilaDeAncestros);
            }
        }else{ // el dato a eliminar no está en una hoja
            // entonces buscamos el predecesor inOrden
            pilaDeAncestros.push(nodoDelDatoAEliminar);
            NodoMVias<T> nodoDelPredecesor=buscarNodoDelPredecesor(
                    nodoDelDatoAEliminar.getHijo(posicionDeDatoAEliminar),pilaDeAncestros);
            int posicionDelReempazo=nodoDelPredecesor.nroDeDatosNoVacios()-1;
            T datoDeReemplazo=nodoDelPredecesor.getDato(posicionDelReempazo);;
            nodoDelPredecesor.setDato(posicionDelReempazo,(T)NodoMVias.datoVacio());
            nodoDelDatoAEliminar.setDato(posicionDeDatoAEliminar,datoDeReemplazo);

            if(nodoDelPredecesor.nroDeDatosNoVacios()<this.nroMinimoDeDatos){
                prestarseOFusionar(nodoDelPredecesor,pilaDeAncestros);
            }
        }

    }

    private void prestarseOFusionar(NodoMVias<T> nodoDelDatoAEliminar, Stack<NodoMVias<T>> pilaDeAncestros) {
        if(!pilaDeAncestros.isEmpty()){
            NodoMVias<T> padre = pilaDeAncestros.pop();
            int posicionRelativa = getPosicionDeHijo(padre,nodoDelDatoAEliminar);
            if (!NodoMVias.esNodoVacio(padre.getHijo(posicionRelativa+1))){
                if(padre.getHijo(posicionRelativa+1).nroDeDatosNoVacios()>nroMinimoDeDatos){
                    prestarDerecho(nodoDelDatoAEliminar, padre, posicionRelativa);
                }else if(posicionRelativa>0 && padre.getHijo(posicionRelativa-1).nroDeDatosNoVacios()>nroMinimoDeDatos){
                    prestarIzquierdo(nodoDelDatoAEliminar, padre, posicionRelativa);
                }else{
                    fusionar(padre.getHijo(posicionRelativa+1),padre,posicionRelativa);
                    if(padre.nroDeDatosNoVacios()<nroMinimoDeDatos){
                        prestarseOFusionar(padre,pilaDeAncestros);
                    }
                }
            } else if (posicionRelativa>0) {
                if(padre.getHijo(posicionRelativa-1).nroDeDatosNoVacios()>nroMinimoDeDatos){
                    prestarIzquierdo(nodoDelDatoAEliminar, padre, posicionRelativa);
                }else{
                    fusionar(padre.getHijo(posicionRelativa),padre,posicionRelativa-1);
                    if(padre.nroDeDatosNoVacios()<nroMinimoDeDatos){
                        prestarseOFusionar(padre,pilaDeAncestros);
                    }
                }
            }
        }else{
            raiz=nodoDelDatoAEliminar.getHijo(0);
        }
    }

    private int getPosicionDeHijo(NodoMVias<T> padre, NodoMVias<T> hijo){
        int i=0;
        while(i<padre.nroDeDatosNoVacios()){
            if(padre.getHijo(i)==hijo){
                return i;
            }
            i++;
        }
        return i;
    }

    private void fusionar(NodoMVias<T> nodoDerecho, NodoMVias<T> padre, int posicionHermanoIzquierdo) {
        T datoPadre=padre.getDato(posicionHermanoIzquierdo);
        NodoMVias<T> nodoIzquierdo=padre.getHijo(posicionHermanoIzquierdo);
        int i=0;
        while(i<nodoIzquierdo.nroDeDatosNoVacios()){
            insertarDatoOrdenadoEnNodo(nodoDerecho,nodoIzquierdo.getDato(i));
            nodoDerecho.setHijo(i,nodoIzquierdo.getHijo(i));
            i++;
        }

        insertarDatoOrdenadoEnNodo(nodoDerecho,datoPadre);
        nodoDerecho.setHijo(i,nodoIzquierdo.getHijo(i));
        eliminarElDatoDelNodo(padre,posicionHermanoIzquierdo);
    }

    private void prestarIzquierdo(NodoMVias<T> nodoDelDatoAEliminar, NodoMVias<T> padre, int posicionRelativa) {
        NodoMVias<T> hermano = padre.getHijo(posicionRelativa - 1);
        T datoDelHermano = hermano.getDato(hermano.nroDeDatosNoVacios() - 1);
        eliminarElDatoDelNodo(hermano, hermano.nroDeDatosNoVacios() - 1);
        T datoDelPadre = padre.getDato(posicionRelativa - 1);
        padre.setDato(posicionRelativa - 1, datoDelHermano);
        insertarDatoOrdenadoEnNodo(nodoDelDatoAEliminar, datoDelPadre);
    }

    private void prestarDerecho(NodoMVias<T> nodoDelDatoAEliminar, NodoMVias<T> padre, int posicionRelativa) {
        NodoMVias<T> hermano= padre.getHijo(posicionRelativa +1);
        T datoDelHermano=hermano.getDato(0);
        NodoMVias<T> hijoAPerder=hermano.getHijo(0);
        eliminarElDatoDelNodo(hermano,0);
        T datoDelPadre= padre.getDato(posicionRelativa);
        padre.setDato(posicionRelativa,datoDelHermano);
        insertarDatoOrdenadoEnNodo(nodoDelDatoAEliminar,datoDelPadre);
        nodoDelDatoAEliminar.setHijo(nodoDelDatoAEliminar.nroDeDatosNoVacios(),hijoAPerder);
    }


    private NodoMVias<T> buscarNodoDelPredecesor(NodoMVias hijo,Stack<NodoMVias<T>> pilaDeAncestros) {
        if(hijo.esHoja()){
            return hijo;
        }
        pilaDeAncestros.push(hijo);
        return buscarNodoDelPredecesor(hijo.getHijo(hijo.nroDeDatosNoVacios()),pilaDeAncestros);
    }

}
