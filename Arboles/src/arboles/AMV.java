package arboles;

import arboles.Excepciones.ExcepcionDatoNoExiste;
import arboles.Excepciones.ExcepcionDatoYaExiste;
import arboles.Excepciones.ExcepcionOrdenInvalido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AMV <T extends Comparable<T>>
    implements IArbolBusqueda<T>{

    protected NodoMVias<T> raiz;
    protected int orden;
    private static final byte ORDEN_MINIMO=3;
    protected static final byte POSICION_INVALIDA=-1;

    public AMV(){
        this.orden=ORDEN_MINIMO;
    }

    public AMV(int orden) throws ExcepcionOrdenInvalido{
        if(orden<ORDEN_MINIMO){
            throw new ExcepcionOrdenInvalido();
        }
        this.orden=orden;
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
            do{
                int posicionDeDatoEnNodo=this.buscarPosicionDeDatoEnNodo(nodoAux,datoAInsertar);
                if(posicionDeDatoEnNodo!=POSICION_INVALIDA){
                    throw new ExcepcionDatoYaExiste();
                }
                //En este punto punto sabemos que el datoAInsertar no está en el nodoAux
                if(nodoAux.esHoja()){
                    if(nodoAux.estanDatosLlenos()){
                        int posicionPorDondeBajar=this.buscarPosicionParaBajar(nodoAux,datoAInsertar);
                        NodoMVias<T> nuevoNodo= new NodoMVias<>(this.orden,datoAInsertar);
                        nodoAux.setHijo(posicionPorDondeBajar,nuevoNodo);
                    }else{
                        this.insertarDatoOrdenadoEnNodo(nodoAux,datoAInsertar);
                    }
                    nodoAux=NodoMVias.nodoVacio();
                }else{
                    //en este punto el nodoAux no es hoja
                    int posicionPorDondeBajar=this.buscarPosicionParaBajar(nodoAux,datoAInsertar);
                    if(nodoAux.esHijoVacio(posicionPorDondeBajar)){
                        NodoMVias<T> nuevoNodo= new NodoMVias<>(this.orden,datoAInsertar);
                        nodoAux.setHijo(posicionPorDondeBajar,nuevoNodo);
                        nodoAux=NodoMVias.nodoVacio();
                    }else{
                        nodoAux=nodoAux.getHijo(posicionPorDondeBajar);
                    }
                }
            }while(!NodoMVias.esNodoVacio(nodoAux));
        }
    }

    protected void insertarDatoOrdenadoEnNodo(NodoMVias<T> nodoAux, T datoAInsertar) {
        T datoACambiar=datoAInsertar;
        NodoMVias<T> nodoIzquierdo=NodoMVias.nodoVacio();
        NodoMVias<T> nodoDerecho=NodoMVias.nodoVacio();
        int i=0;

        while(i<nodoAux.nroDeDatosNoVacios()){
            if(datoACambiar.compareTo(nodoAux.getDato(i))<0){
                nodoDerecho=nodoAux.getHijo(i);
                T datoSalvar=nodoAux.getDato(i);
                nodoAux.setHijo(i,nodoIzquierdo);
                nodoIzquierdo=nodoDerecho;
                nodoAux.setDato(i,datoACambiar);
                datoACambiar=datoSalvar;
            }
            i++;
        }
        nodoDerecho=nodoAux.getHijo(i);
        nodoAux.setDato(i,datoACambiar);
        nodoAux.setHijo(i,nodoIzquierdo);
        nodoAux.setHijo(i++,nodoDerecho);
    }

    protected int buscarPosicionParaBajar(NodoMVias<T> nodoAux, T datoAInsertar) {
        for(int i=0;i<nodoAux.nroDeDatosNoVacios();i++){
            if(datoAInsertar.compareTo(nodoAux.getDato(i))<0){
                return i;
            }
        }
        return nodoAux.nroDeDatosNoVacios();
    }

    protected int buscarPosicionDeDatoEnNodo(NodoMVias<T> nodoAux, T datoAInsertar) {
        for(int i = 0; i<nodoAux.nroDeDatosNoVacios(); i++){
            if(nodoAux.getDato(i)==datoAInsertar){
                return i;
            }
        }
        return POSICION_INVALIDA;
    }

    @Override
    public void eliminar(T datoAEliminar) {
        if (datoAEliminar==null){
            throw new IllegalArgumentException("Dato a insertar no puede ser nulo");
        }
        this.raiz=eliminar(this.raiz,datoAEliminar);
    }

    private NodoMVias<T> eliminar(NodoMVias<T> nodoActual, T datoAEliminar) {
        //caso base
        if(NodoMVias.esNodoVacio(nodoActual)){
            throw new ExcepcionDatoNoExiste();
        }
        int posicionDeDatoEnNodo=this.buscarPosicionDeDatoEnNodo(nodoActual,datoAEliminar);
        if(posicionDeDatoEnNodo!=POSICION_INVALIDA){
            if(nodoActual.esHoja()){
                this.eliminarElDatoDelNodo(nodoActual,posicionDeDatoEnNodo);
                if(nodoActual.nroDeDatosNoVacios()==0){
                    return NodoMVias.nodoVacio();
                }
                return nodoActual;
            }
            //nodoActual no es hoja, pero el dato está ahí, entonces
            //debo buscar un reemplazo y eliminar antes de usarlo
            T datoDeRemplazo;
            if(hayHijoEnNodoDelanteDePosicion(nodoActual,posicionDeDatoEnNodo)){
                datoDeRemplazo=this.obtenerDatoSucesorInOrden(nodoActual,posicionDeDatoEnNodo+1);
            }else{
                datoDeRemplazo=this.obtenerDatoPredecesorInOrden(nodoActual,posicionDeDatoEnNodo);
            }
            nodoActual=eliminar(nodoActual,datoDeRemplazo);
            nodoActual.setDato(posicionDeDatoEnNodo,datoDeRemplazo);
            return nodoActual;
        }
        //en este punto, el datoAEliminar no está en el nodoActual
        int posicionPorDondeBajar= buscarPosicionParaBajar(nodoActual,datoAEliminar);
        NodoMVias<T> supuestoNuevoHijo=eliminar(
                nodoActual.getHijo(posicionPorDondeBajar),datoAEliminar);
        nodoActual.setHijo(posicionPorDondeBajar,supuestoNuevoHijo);
        return nodoActual;
    }

    protected T obtenerDatoPredecesorInOrden(NodoMVias<T> nodoActual, int posicionDatoPosible) {
        if(nodoActual.esHijoVacio(posicionDatoPosible)){
            return nodoActual.getDato(posicionDatoPosible-1);
        }
        NodoMVias<T> hijoPredecesor=nodoActual.getHijo(posicionDatoPosible);
        return obtenerDatoSucesorInOrden(hijoPredecesor,hijoPredecesor.nroDeDatosNoVacios()-1);
    }

    protected T obtenerDatoSucesorInOrden(NodoMVias<T> nodoActual, int posicionDatoPosible) {
        if(nodoActual.esHijoVacio(posicionDatoPosible)){
            return nodoActual.getDato(posicionDatoPosible);
        }
        return obtenerDatoSucesorInOrden(nodoActual.getHijo(posicionDatoPosible),0);
    }

    private boolean hayHijoEnNodoDelanteDePosicion(NodoMVias<T> nodoActual, int posicionDeDatoEnNodo) {
        for(int i=posicionDeDatoEnNodo+1;i<=nodoActual.nroDeDatosNoVacios();i++){
            if(!nodoActual.esHijoVacio(i)){
                return true;
            }
        }
        return false;
    }

    protected void eliminarElDatoDelNodo(NodoMVias<T> nodoActual, int posicionDeDatoEnNodo) {

    }

    @Override
    public T buscar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Dato a buscar no puede ser nulo");
        }
        NodoMVias<T> nodoAux = this.raiz;
        while(!NodoMVias.esNodoVacio(nodoAux)){
            boolean cambioElNodo= false;
            for(int i = 0; i<nodoAux.nroDeDatosNoVacios()&&!cambioElNodo; i++) {
                T datoDelNodoAux = nodoAux.getDato(i);
                if(dato.compareTo(datoDelNodoAux)==0) {
                    return datoDelNodoAux;
                }
                if(dato.compareTo(datoDelNodoAux)<0){
                    nodoAux=nodoAux.getHijo(i);
                    cambioElNodo=true;
                }
            }//end for
            if(!cambioElNodo){
                nodoAux=nodoAux.getHijo(nodoAux.nroDeDatosNoVacios());
            }
        }//end while
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
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoMVias<T> nodoQueTocaSacar;
            do {
                nodoQueTocaSacar=colaDeNodos.poll();
                for(int i=0;i<nodoQueTocaSacar.nroDeDatosNoVacios();i++){
                    listaDeDatos.add(nodoQueTocaSacar.getDato(i));
                    if(!nodoQueTocaSacar.esHijoVacio(i)){
                        colaDeNodos.offer(nodoQueTocaSacar.getHijo(i));
                    }
                }
                if(!nodoQueTocaSacar.esHijoVacio(nodoQueTocaSacar.nroDeDatosNoVacios())){
                    colaDeNodos.offer(nodoQueTocaSacar.getHijo(nodoQueTocaSacar.nroDeDatosNoVacios()));
                }
            }while(!colaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> listaRecorrido=new ArrayList<>();
        this.recorridoEnPreOrden(this.raiz,listaRecorrido);
        return listaRecorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<T> nodoActual, List<T> listaRecorrido) {
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        for(int i=0;i<nodoActual.nroDeDatosNoVacios();i++){

            listaRecorrido.add(nodoActual.getDato(i));
            recorridoEnPreOrden(nodoActual.getHijo(i),listaRecorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.nroDeDatosNoVacios()),listaRecorrido);
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> listaRecorrido=new ArrayList<>();
        this.recorridoEnInOrden(this.raiz,listaRecorrido);
        return listaRecorrido;
    }

    private void recorridoEnInOrden(NodoMVias<T> nodoActual, List<T> listaRecorrido) {
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        for(int i=0;i<nodoActual.nroDeDatosNoVacios();i++){
            recorridoEnInOrden(nodoActual.getHijo(i),listaRecorrido);
            listaRecorrido.add(nodoActual.getDato(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.nroDeDatosNoVacios()),listaRecorrido);
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> listaRecorrido=new ArrayList<>();
        this.recorridoEnPostOrden(this.raiz,listaRecorrido);
        return listaRecorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<T> nodoActual, List<T> listaRecorrido) {
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0),listaRecorrido);
        for(int i=0;i<nodoActual.nroDeDatosNoVacios();i++){
            recorridoEnPostOrden(nodoActual.getHijo(i+1),listaRecorrido);
            listaRecorrido.add(nodoActual.getDato(i));
        }
    }

    @Override
    public int size() {
        return size(this.raiz);
    }

    private int size(NodoMVias<T> nodoActual) {
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int sizeTotal=0;
        for(int i=0;i<=nodoActual.nroDeDatosNoVacios();i++){
            int sizeHijoEnTurno=size(nodoActual.getHijo(i));
            sizeTotal+=sizeHijoEnTurno;
        }
        return sizeTotal+1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    private int altura(NodoMVias<T> nodoActual) {
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayor=0;
        for(int i=0;i<=nodoActual.nroDeDatosNoVacios();i++){
            int alturaHijoEnTurno=altura(nodoActual.getHijo(i));
            if(alturaHijoEnTurno>alturaMayor){
                alturaMayor=alturaHijoEnTurno;
            }
        }
        return alturaMayor+1;
    }

    @Override
    public void vaciar() {
        this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(raiz);
    }

    @Override
    public int nivel() {
        return 0;
    }

    //cuantos nodos tienen k hijos, a partir del nivel n
    public int ejercicio1(int n, int k){
        if(!this.esArbolVacio()) {
            int nivel=0;
            int cantidadDeNodos = 0;
            int cantidadDeHijos;
            Queue<NodoMVias<T>> colaDeNodos= new LinkedList<>();
            Queue<NodoMVias<T>> colaDeNodosTemp= new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoMVias<T> nodoAux;
            do{
                nodoAux=colaDeNodos.poll();
                cantidadDeHijos=0;
                for(int i=0;i<=nodoAux.nroDeDatosNoVacios();i++){
                    if(!nodoAux.esHijoVacio(i)){
                        colaDeNodosTemp.offer(nodoAux.getHijo(i));
                        cantidadDeHijos++;
                    }
                }
                if(nivel>=n){
                    if(cantidadDeHijos>=k){
                        cantidadDeNodos++;
                    }
                }
                if(colaDeNodos.isEmpty()){
                    nivel++;
                    while(!colaDeNodosTemp.isEmpty()){
                        colaDeNodos.offer(colaDeNodosTemp.poll());
                    }
                }
            }while(!colaDeNodos.isEmpty());
            return cantidadDeNodos;
        }
        return 0;
    }

    //hacerlo con una sola cola
    public int ejercicio1_2(int n, int k){
        if(!this.esArbolVacio()) {
            int nivel=0;
            int cantidadDeNodos = 0;
            int cantidadDeHijos;
            Queue<NodoMVias<T>> colaDeNodos= new LinkedList<>();
            Queue<NodoMVias<T>> colaDeNodosTemp= new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoMVias<T> nodoAux;
            do{
                nodoAux=colaDeNodos.poll();
                cantidadDeHijos=0;
                for(int i=0;i<=nodoAux.nroDeDatosNoVacios();i++){
                    if(!nodoAux.esHijoVacio(i)){
                        colaDeNodosTemp.offer(nodoAux.getHijo(i));
                        cantidadDeHijos++;
                    }
                }
                if(nivel>=n){
                    if(cantidadDeHijos>=k){
                        cantidadDeNodos++;
                    }
                }
                if(colaDeNodos.isEmpty()){
                    nivel++;
                    while(!colaDeNodosTemp.isEmpty()){
                        colaDeNodos.offer(colaDeNodosTemp.poll());
                    }
                }
            }while(!colaDeNodos.isEmpty());
            return cantidadDeNodos;
        }
        return 0;
    }

    public int ejercicio1R(int n, int k){
        return ejercicio1R(this.raiz, n,k);
    }

    private int ejercicio1R(NodoMVias<T> nodoActual, int n, int k) {
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidadDeNodos=0;
        int cantidadDeHijos=0;
        for(int i=0;i<=nodoActual.nroDeDatosNoVacios();i++){
            cantidadDeNodos=cantidadDeNodos+ejercicio1R(nodoActual.getHijo(i),n-1,k);
            if(!nodoActual.esHijoVacio(i)){
                cantidadDeHijos++;
            };
        }
        if(n<=0 && cantidadDeHijos>=k){
            return cantidadDeNodos+1;
        }
        return cantidadDeNodos;
    }

    @Override
    public String toString() {
        return crearRepresentacion(raiz, "", "", -1);
    }

    private String crearRepresentacion(NodoMVias<T> nodo, String representacion, String prefijo, int indice) {
        if (nodo == null) {
            return representacion + prefijo + ((indice<orden&&indice>=0) ? "├── " : (indice==orden ? "└── " : "")) + "null\n";
        }

        representacion += prefijo +  (indice==-1 ? "" : ((indice==orden) ? "└── ":"├── " ))  + "(" + (indice>=0?indice:"R") + ")" + nodo.toString() + "\n";

        String nuevoPrefijo = prefijo + (indice==-1? " " : ((indice<orden&&indice>=0) ? "│    " : "     "));
        for(int i=0;i<=this.orden;i++){
            representacion = crearRepresentacion(nodo.getHijo(i), representacion, nuevoPrefijo, i);
        }

        return representacion;
    }
}
