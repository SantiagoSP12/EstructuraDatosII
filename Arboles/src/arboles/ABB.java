package arboles;

import arboles.Excepciones.ExcepcionDatoNoExiste;
import arboles.Excepciones.ExcepcionDatoYaExiste;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ABB<T extends Comparable<T>>
        implements IArbolBusqueda<T> {
    protected NodoBinario<T> raiz;

    public ABB(){}

    public ABB reconstruirConPreOrden(List<T> listaEnPreOrden, List<T> listaEnInOrden){return null;}

    public ABB reconstruirConPostOrden(List<T> listaEnPreOrden, List<T> listaEnInOrden){return null;}

    @Override
    public void insertar(T dato) {
        if(this.esArbolVacio()){
            this.raiz=new NodoBinario<>(dato);
        }else{
            NodoBinario<T> nodoAux=this.raiz;
            NodoBinario<T> nodoAnterior=NodoBinario.nodoVacio();
            do{
                T datoDelNodoAux= nodoAux.getDato();
                nodoAnterior=nodoAux;
                if(dato.compareTo(datoDelNodoAux)<0){
                    nodoAux=nodoAux.getHijoIzquierdo();
                }else if(dato.compareTo(datoDelNodoAux)>0){
                    nodoAux=nodoAux.getHijoDerecho();
                }else{
                    throw new ExcepcionDatoYaExiste();
                }
            }while(!NodoBinario.esNodoVacio(nodoAux));
            NodoBinario<T> nuevoNodo=new NodoBinario<>(dato);
            if(dato.compareTo(nodoAnterior.getDato())<0){
                nodoAnterior.setHijoIzquierdo(nuevoNodo);
            }else{
                nodoAnterior.setHijoDerecho(nuevoNodo);
            }
        }
    }

    @Override
    public void eliminar(T datoAEliminar) {
        if(datoAEliminar==null){
            throw new RuntimeException("Dato no puede ser nulo");
        }
        if(!this.esArbolVacio()){
            NodoBinario<T> nodoAux=this.raiz;
            NodoBinario<T> nodoAnterior=NodoBinario.nodoVacio();
            do{
                if(datoAEliminar.compareTo(nodoAux.getDato())<0){
                    nodoAnterior=nodoAux;
                    nodoAux=nodoAux.getHijoIzquierdo();
                }else if(datoAEliminar.compareTo(nodoAux.getDato())>0){
                    nodoAnterior=nodoAux;
                    nodoAux=nodoAux.getHijoDerecho();
                }
            }while(datoAEliminar.compareTo(nodoAux.getDato())!=0);

        }
    }

    public void eliminarR(T datoAEliminar){
        this.raiz=eliminarR(this.raiz,datoAEliminar);
    }

    protected NodoBinario<T> eliminarR(NodoBinario<T> nodoActual, T datoAEliminar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            throw new ExcepcionDatoNoExiste();
        }
        T datoActual=nodoActual.getDato();
        if(datoAEliminar.compareTo(datoActual) < 0){
            nodoActual.setHijoIzquierdo(eliminarR(nodoActual.getHijoIzquierdo(),datoAEliminar));
            return nodoActual;
        }
        if (datoAEliminar.compareTo(datoActual) > 0) {
            nodoActual.setHijoDerecho(eliminarR(nodoActual.getHijoDerecho(),datoAEliminar));
            return nodoActual;
        }
        //caso 1: nodoActual es hoja
        if(nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        //caso 2: nodoActual tiene al menos un hijo
        //caso 2.1: nodoActual tiene hijo izquierdo
        if(!nodoActual.esVacioHijoIzquierdo()&& nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        //caso 2.2: nodoActual tiene hijo derecho
        if(nodoActual.esVacioHijoIzquierdo()&&!nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoDerecho();
        }
        //caso 3: nodoActual tiene ambos hijos
        T reemplazo=obtenerSucesorInOrden(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoHD=eliminarR(nodoActual.getHijoDerecho(),reemplazo);
        nodoActual.setHijoDerecho(supuestoHD);
        nodoActual.setDato(reemplazo);
        return nodoActual;
    }

    protected T obtenerSucesorInOrden(NodoBinario<T> hijoDerecho) {
        NodoBinario<T> nodoActual=hijoDerecho;
        while(!nodoActual.esVacioHijoIzquierdo()){
            nodoActual=nodoActual.getHijoIzquierdo();
        }
        return nodoActual.getDato();
    }

    @Override
    public T buscar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser nulo");
        }
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<T> nodoActual = this.raiz;
        do {
            T datoActual = nodoActual.getDato();
            int comparacion = dato.compareTo(datoActual);
            if (comparacion < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (comparacion > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getDato();
            }
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return null;
    }

    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato)!=null;
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> listaDeDatos=new LinkedList<>();
        if(!this.esArbolVacio()){
            Queue<NodoBinario<T>> colaDeNodos=new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoBinario<T> nodoQueTocaSacar;
            do{
                nodoQueTocaSacar=colaDeNodos.poll();
                listaDeDatos.add(nodoQueTocaSacar.getDato());
                if(!nodoQueTocaSacar.esVacioHijoIzquierdo()){
                    colaDeNodos.offer(nodoQueTocaSacar.getHijoIzquierdo());
                }
                if(!nodoQueTocaSacar.esVacioHijoDerecho()){
                    colaDeNodos.offer(nodoQueTocaSacar.getHijoDerecho());
                }
            }while(!colaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> listaDeDatos=new LinkedList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<T>> pilaDeNodos=new Stack<>();
            pilaDeNodos.push(this.raiz);
            NodoBinario<T> nodoQueTocaSacar;
            do{
                nodoQueTocaSacar=pilaDeNodos.pop();
                listaDeDatos.add(nodoQueTocaSacar.getDato());
                if(!nodoQueTocaSacar.esVacioHijoDerecho()){
                    pilaDeNodos.push(nodoQueTocaSacar.getHijoDerecho());
                }
                if(!nodoQueTocaSacar.esVacioHijoIzquierdo()){
                    pilaDeNodos.push(nodoQueTocaSacar.getHijoIzquierdo());
                }
            }while(!pilaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> listaDeDatos = new LinkedList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            NodoBinario<T> nodoAux=this.raiz;
            meterEnLaPilaParaInOrden(nodoAux,pilaDeNodos);
            do{
                nodoAux=pilaDeNodos.pop();
                listaDeDatos.add(nodoAux.getDato());
                if (!nodoAux.esVacioHijoDerecho()){
                    meterEnLaPilaParaInOrden(nodoAux.getHijoDerecho(),pilaDeNodos);
                }
            }while(!pilaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    private void meterEnLaPilaParaInOrden(NodoBinario<T> nodoAux, Stack<NodoBinario<T>> pilaDeNodos) {
        while(!NodoBinario.esNodoVacio(nodoAux)){
            pilaDeNodos.push(nodoAux);
            if(!nodoAux.esVacioHijoIzquierdo()){
                nodoAux=nodoAux.getHijoIzquierdo();
            }
        }
    }

    public List<T> recorridoEnInOrdenR(){
        List<T> recorrido=new LinkedList<>();
        recorridoEnInOrdenR(this.raiz,recorrido);
        return recorrido;
    }

    private void recorridoEnInOrdenR(NodoBinario<T> nodoActual, List<T> recorrido) {
        if(NodoBinario.esNodoVacio(nodoActual)){
            return;
        }
        recorridoEnInOrdenR(nodoActual.getHijoIzquierdo(),recorrido);
        recorrido.add(nodoActual.getDato());
        recorridoEnInOrdenR(nodoActual.getHijoDerecho(),recorrido);
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> listaDeDatos=new LinkedList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<T>> pilaDeNodos=new Stack<>();
            NodoBinario<T> nodoAux=this.raiz;
            meterEnLaPilaParaPostOrden(nodoAux,pilaDeNodos);
            do{
                nodoAux=pilaDeNodos.pop();
                listaDeDatos.add(nodoAux.getDato());
                if(!pilaDeNodos.isEmpty()) {
                    NodoBinario<T> nodoTope=pilaDeNodos.peek();
                    if(!nodoTope.esVacioHijoDerecho()
                        && nodoAux != nodoTope.getHijoDerecho()){
                        nodoAux = nodoTope.getHijoDerecho();
                        meterEnLaPilaParaPostOrden(nodoAux,pilaDeNodos);
                    }
                }
            }while(!pilaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    private void meterEnLaPilaParaPostOrden(NodoBinario<T> nodoAux,
                                            Stack<NodoBinario<T>> pilaDeNodos) {
        while(!NodoBinario.esNodoVacio(nodoAux)){
            pilaDeNodos.push(nodoAux);
            if(!nodoAux.esVacioHijoIzquierdo()){
                nodoAux=nodoAux.getHijoIzquierdo();
            }else{
                nodoAux=nodoAux.getHijoDerecho();
            }
        }
    }

    @Override
    public int size() {
        int cantidadNodos=0;
        if(!this.esArbolVacio()){
            Stack<NodoBinario<T>> pilaDeNodos=new Stack<>();
            pilaDeNodos.push(this.raiz);
            do{
                NodoBinario<T> nodoAux=pilaDeNodos.pop();
                cantidadNodos++;
                if(!nodoAux.esVacioHijoDerecho()){
                    pilaDeNodos.push(nodoAux.getHijoDerecho());
                }
                if(!nodoAux.esVacioHijoIzquierdo()){
                    pilaDeNodos.push(nodoAux.getHijoIzquierdo());
                }
            }while(!pilaDeNodos.isEmpty());
        }
        return cantidadNodos;
    }

    public int sizeR() {
        return sizeR(this.raiz);
    }

    //el método amigo siempre lleva al menos un parámetro
    private int sizeR(NodoBinario<T> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int sizePorIzquierda=sizeR(nodoActual.getHijoIzquierdo());
        int sizePorDerecha=sizeR(nodoActual.getHijoDerecho());
        return sizePorIzquierda+sizePorDerecha+1;
    }

    @Override
    public int altura() {
        int altura=0;
        if(!this.esArbolVacio()){
            Queue<NodoBinario<T>> colaDeNodos=new LinkedList<>();
            Queue<NodoBinario<T>> colaDeNodosTemp=new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoBinario<T> nodoAux;
            do{
                nodoAux=colaDeNodos.poll();
                if(!nodoAux.esVacioHijoIzquierdo()){
                    colaDeNodosTemp.offer(nodoAux.getHijoIzquierdo());
                }
                if(!nodoAux.esVacioHijoDerecho()){
                    colaDeNodosTemp.offer(nodoAux.getHijoDerecho());
                }
                if(colaDeNodos.isEmpty()){
                    altura++;
                    while(!colaDeNodosTemp.isEmpty()){
                        colaDeNodos.offer(colaDeNodosTemp.poll());
                    }
                }
            }while(!colaDeNodos.isEmpty());
        }
        return altura;
    }

    public int alturaR(){
        return alturaR(this.raiz);
    }

    protected int alturaR(NodoBinario<T> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaPorIzquierda=alturaR(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha=alturaR(nodoActual.getHijoDerecho());
        return alturaPorIzquierda>alturaPorDerecha?alturaPorIzquierda+1:alturaPorDerecha+1;
    }

    @Override
    public void vaciar() {
        this.raiz=NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        return 0;
    }

    // nodos con un solo hijo en determinado nivel
    private int nodosConUnSoloHijoEnNivel(NodoBinario<T> nodo, int nivelObjetivo, int nivelActual){
        if(NodoBinario.esNodoVacio(nodo)){
            return 0;
        }

        if(nivelObjetivo==nivelActual){
            if((nodo.esVacioHijoIzquierdo()&&!nodo.esVacioHijoDerecho())
                    || (!nodo.esVacioHijoIzquierdo()&&nodo.esVacioHijoDerecho())){
                return 1;
            }
            return 0;
        }

        int nodosConUnHijoALaIzq=nodosConUnSoloHijoEnNivel(nodo.getHijoIzquierdo(),nivelObjetivo,
                nivelActual+1);
        int nodosConUnHijoALaDerecha=nodosConUnSoloHijoEnNivel(nodo.getHijoDerecho(),nivelObjetivo,
                nivelActual+1);
        return nodosConUnHijoALaIzq+nodosConUnHijoALaDerecha;
    }

    @Override
    public String toString() {
        return crearRepresentacion(raiz, "", "", "R");
    }

    private String crearRepresentacion(NodoBinario<T> nodo, String representacion, String prefijo, String tipo) {
        if (nodo == null) {
            return representacion + prefijo + (tipo.equals("I") ? "├── " : (tipo.equals("D") ? "└── " : "")) + "null\n";
        }

        representacion += prefijo + (tipo.equals("R") ? "" : (tipo.equals("I") ? " ├── " : " └── ")) + nodo.getDato() + " (" + tipo + ")\n";

        String nuevoPrefijo = prefijo + (tipo.equals("I") ? "│   " : "    ");
        representacion = crearRepresentacion(nodo.getHijoIzquierdo(), representacion, nuevoPrefijo, "I");
        representacion = crearRepresentacion(nodo.getHijoDerecho(), representacion, nuevoPrefijo, "D");

        return representacion;
    }
}
