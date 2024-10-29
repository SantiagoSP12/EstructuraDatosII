package arboles;

import java.util.ArrayList;
import java.util.List;

public class NodoMVias <T>{
    private List<T> listaDeDatos;
    private List<NodoMVias<T>> listaDeHijos;

    public static NodoMVias nodoVacio(){ return null; }

    public NodoMVias(int orden ,T dato){
        this(orden);
        listaDeDatos.set(0,dato);
    }

    public static Object datoVacio(){ return null; }

    public static boolean esNodoVacio(NodoMVias nodo){ return nodo==NodoMVias.nodoVacio();}

    public NodoMVias(int orden){
        listaDeDatos =new ArrayList<>();
        listaDeHijos = new ArrayList<NodoMVias<T>>();
        for(int i=0;i<orden;i++){
            listaDeDatos.add((T)NodoMVias.datoVacio());
            listaDeHijos.add(NodoMVias.nodoVacio());
        }
        listaDeHijos.add(NodoMVias.nodoVacio());
    }

    public T getDato(int posicion){
        return listaDeDatos.get(posicion);
    }

    public void setDato(int posicion,T dato){
        listaDeDatos.set(posicion,dato);
    }

    public boolean esDatoVacio(int posicion){
        return listaDeDatos.get(posicion)==NodoMVias.datoVacio();
    }

    public NodoMVias<T> getHijo(int posicion){
        return listaDeHijos.get(posicion);
    }

    public void setHijo(int posicion, NodoMVias<T> hijo){
        listaDeHijos.set(posicion, hijo);
    }

    public boolean esHijoVacio(int posicion){
        return listaDeHijos.get(posicion)==NodoMVias.nodoVacio();
    }

    public boolean esHoja(){
        for(int i=0;i<listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }

    public int nroDeDatosNoVacios(){
        int cantidadDeDatos=0;
        for(int i=0;i<listaDeDatos.size();i++){
            if(!this.esDatoVacio(i)){
                cantidadDeDatos++;
            }
        }
        return cantidadDeDatos;
    }

    public boolean estanDatosLlenos(){
        return nroDeDatosNoVacios()==listaDeDatos.size();
    }

    @Override
    public String toString(){
        String representacion=" [";
        for (int i = 0; i <listaDeDatos.size(); i++) {
            if (!this.esDatoVacio(i)) {
                representacion += this.getDato(i);
                if (i < this.nroDeDatosNoVacios() - 1) {
                    representacion += ", ";
                }
            }else {
                representacion += ", ♠";
            }
        }
        representacion += "]";
        return representacion;
    }

}