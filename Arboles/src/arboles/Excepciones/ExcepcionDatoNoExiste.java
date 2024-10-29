package arboles.Excepciones;

public class ExcepcionDatoNoExiste extends RuntimeException {
    public ExcepcionDatoNoExiste(){
        super("Dato no existe en el arbol");
    }
    public ExcepcionDatoNoExiste(String message){
        super(message);
    }
}
