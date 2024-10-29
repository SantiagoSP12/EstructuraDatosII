package arboles;

public class NodoBinario<T> {
    private T dato;
    private NodoBinario<T> hijoDerecho;
    private NodoBinario<T> hijoIzquierdo;

    public NodoBinario(){};

    public NodoBinario(T dato){ this.dato=dato; }

    public T getDato() { return dato; }

    public void setDato(T dato) { this.dato = dato; }

    public NodoBinario<T> getHijoIzquierdo() { return hijoIzquierdo; }

    public void setHijoIzquierdo(NodoBinario<T> hijoIzquierdo) { this.hijoIzquierdo = hijoIzquierdo; }

    public NodoBinario<T> getHijoDerecho() { return hijoDerecho; }

    public void setHijoDerecho(NodoBinario<T> hijoDerecho) { this.hijoDerecho = hijoDerecho; }

    public static NodoBinario nodoVacio(){ return null;}

    public boolean esVacioHijoIzquierdo(){ return this.hijoIzquierdo==nodoVacio(); }

    public boolean esVacioHijoDerecho(){ return this.hijoDerecho==nodoVacio(); }

    public boolean esHoja(){ return this.esVacioHijoDerecho()&&this.esVacioHijoIzquierdo(); }

    public static boolean esNodoVacio(NodoBinario nodo){ return nodo==NodoBinario.nodoVacio();}


}
