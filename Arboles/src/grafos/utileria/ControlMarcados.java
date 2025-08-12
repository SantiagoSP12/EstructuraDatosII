package grafos.utileria;

import java.util.ArrayList;
import java.util.List;

public class ControlMarcados {
    private final List<Boolean> listaDeMarcados;

    public ControlMarcados(int nroDeVertices) {
        this.listaDeMarcados=new ArrayList<Boolean>();
        for (int i=0;i<nroDeVertices;i++){
            listaDeMarcados.add(Boolean.FALSE);
        }
    }

    public void desmarcarTodos(){
        for(int i=0;i<listaDeMarcados.size();i++){
            listaDeMarcados.set(i,Boolean.FALSE);
        }
    }

    public boolean estaMarcadoVertice(int nroVertice){
        return listaDeMarcados.get(nroVertice);
    }

    public void marcar(int nroVertice){
        listaDeMarcados.set(nroVertice,Boolean.TRUE);
    }

    public void desmarcarVertice(int nroVertice){
        listaDeMarcados.set(nroVertice,Boolean.FALSE);
    }

    public boolean estanTodosMarcados(){
        return !listaDeMarcados.contains(Boolean.FALSE);
    }

    @Override
    public String toString(){
        return this.listaDeMarcados.toString();
    }

}
