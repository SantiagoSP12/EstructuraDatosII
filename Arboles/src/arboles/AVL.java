package arboles;

import arboles.Excepciones.ExcepcionDatoNoExiste;
import arboles.Excepciones.ExcepcionDatoYaExiste;

public class AVL<T extends Comparable<T>> extends ABB<T>{
    private static final int RANGO_INFERIOR=-1;
    private static final int RANGO_SUPERIOR=1;

    @Override
    public void insertar(T datoAInsertar){
        this.raiz=insertar(this.raiz,datoAInsertar);
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodoActual, T datoAInsertar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return new NodoBinario<>(datoAInsertar);
        }
        T datoActual= nodoActual.getDato();
        if(datoAInsertar.compareTo(datoActual)<0){
            NodoBinario<T> supuestoNuevoHI=insertar(nodoActual.getHijoIzquierdo(),datoAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHI);
            return balancear(nodoActual);
        }
        if(datoAInsertar.compareTo(datoActual)>0){
            NodoBinario<T> supuestoNuevoHD=insertar(nodoActual.getHijoDerecho(),datoAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHD);
            return balancear(nodoActual);
        }
        throw new ExcepcionDatoYaExiste();
    }

    private NodoBinario<T> balancear(NodoBinario<T> nodo) {
        int alturaIzq=super.alturaR(nodo.getHijoIzquierdo());
        int alturaDer=super.alturaR(nodo.getHijoDerecho());
        int diferencia=alturaIzq-alturaDer;
        if(diferencia>RANGO_SUPERIOR){
            NodoBinario<T> nodoActHI=nodo.getHijoIzquierdo();
            alturaIzq=super.alturaR(nodoActHI.getHijoIzquierdo());
            alturaDer=super.alturaR(nodoActHI.getHijoDerecho());
            if(alturaDer>alturaIzq) {
                return rotacionDoblePorDerecha(nodo);
            }
            return rotacionSimplePorDerecha(nodo);
        }
        if(diferencia<RANGO_INFERIOR){
            NodoBinario<T> nodoActHD=nodo.getHijoDerecho();
            alturaIzq=super.alturaR(nodoActHD.getHijoIzquierdo());
            alturaDer=super.alturaR(nodoActHD.getHijoDerecho());
            if(alturaIzq>alturaDer){
                return rotacionDoblePorIzquierda(nodo);
            }
            return rotacionSimplePorIzquierda(nodo);
        }
        return nodo;
    }

    private NodoBinario<T> rotacionDoblePorIzquierda(NodoBinario<T> nodo) {
        NodoBinario<T> nodoPadre=rotacionSimplePorDerecha(nodo.getHijoDerecho());
        nodo.setHijoDerecho(nodoPadre);
        return rotacionSimplePorIzquierda(nodo);
    }

    private NodoBinario<T> rotacionDoblePorDerecha(NodoBinario<T> nodo) {
        NodoBinario<T> nodoPadre=rotacionSimplePorIzquierda(nodo.getHijoIzquierdo());
        nodo.setHijoIzquierdo(nodoPadre);
        return rotacionSimplePorDerecha(nodo);
    }

    private NodoBinario<T> rotacionSimplePorIzquierda(NodoBinario<T> nodo){
        NodoBinario<T> nodoPadre=nodo.getHijoDerecho();
        nodo.setHijoDerecho(nodoPadre.getHijoIzquierdo());
        nodoPadre.setHijoIzquierdo(nodo);
        return nodoPadre;
    }

    private NodoBinario<T> rotacionSimplePorDerecha(NodoBinario<T> nodo){
        NodoBinario<T> nodoPadre=nodo.getHijoIzquierdo();
        nodo.setHijoIzquierdo(nodoPadre.getHijoDerecho());
        nodoPadre.setHijoDerecho(nodo);
        return nodoPadre;
    }

    @Override
    public void eliminar(T datoAEliminar) {
        //no es asi de simple//copie y pegue, en casi todo return poner el balancear
        this.raiz=eliminar(this.raiz,datoAEliminar);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodo, T datoAEliminar) {
        if(NodoBinario.esNodoVacio(nodo)){
            throw new ExcepcionDatoNoExiste();
        }
        T datoActual=nodo.getDato();
        if(datoAEliminar.compareTo(datoActual) < 0){
            nodo.setHijoIzquierdo(eliminar(nodo.getHijoIzquierdo(),datoAEliminar));
            return balancear(nodo);
        }
        if (datoAEliminar.compareTo(datoActual) > 0) {
            nodo.setHijoDerecho(eliminar(nodo.getHijoDerecho(),datoAEliminar));
            return balancear(nodo);
        }
        //caso 1: nodoActual es hoja
        if(nodo.esHoja()){
            return NodoBinario.nodoVacio();
        }
        //caso 2: nodoActual tiene al menos un hijo
        //caso 2.1: nodoActual tiene hijo izquierdo
        if(!nodo.esVacioHijoIzquierdo()&& nodo.esVacioHijoDerecho()){
            return balancear(nodo.getHijoIzquierdo());
        }
        //caso 2.2: nodoActual tiene hijo derecho
        if(nodo.esVacioHijoIzquierdo()&&!nodo.esVacioHijoDerecho()){
            return balancear(nodo.getHijoDerecho());
        }
        //caso 3: nodoActual tiene ambos hijos
        T reemplazo=super.obtenerSucesorInOrden(nodo.getHijoDerecho());
        NodoBinario<T> supuestoHD=eliminar(nodo.getHijoDerecho(),reemplazo);
        nodo.setHijoDerecho(supuestoHD);
        nodo.setDato(reemplazo);
        return balancear(nodo);
    }

}
