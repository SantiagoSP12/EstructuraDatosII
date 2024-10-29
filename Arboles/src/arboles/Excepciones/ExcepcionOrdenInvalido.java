package arboles.Excepciones;

public class ExcepcionOrdenInvalido extends Exception{
    public ExcepcionOrdenInvalido(){
        super("Orden del árbol debe ser al menos 3");
    }

    public ExcepcionOrdenInvalido(String message){
        super(message);
    }

}
