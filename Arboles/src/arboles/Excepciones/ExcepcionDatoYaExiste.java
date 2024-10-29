package arboles.Excepciones;

public class ExcepcionDatoYaExiste extends RuntimeException{
    public ExcepcionDatoYaExiste(){
        super("Dato ya existe en el arbol");
    }
    public ExcepcionDatoYaExiste(String message){
        super(message);
    }
}
