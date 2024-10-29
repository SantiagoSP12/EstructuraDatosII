package arboles;

import java.util.List;

public interface IArbolBusqueda<T extends Comparable<T>>{
    public void insertar(T dato);
    public void eliminar(T dato);
    public T buscar(T dato);
    public boolean contiene(T dato);
    public List<T> recorridoPorNiveles();
    public List<T> recorridoEnPreOrden();
    public List<T> recorridoEnInOrden();
    public List<T> recorridoEnPostOrden();
    public int size();
    public int altura();
    public void vaciar();
    public boolean esArbolVacio();
    public int nivel();

}
