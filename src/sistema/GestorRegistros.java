package sistema;

import registroCompras.RegistroCompra;

import java.util.ArrayList;
import java.util.List;

public class GestorRegistros {
    private List<RegistroCompra> registros;

    public GestorRegistros(){
        this.registros = new ArrayList<RegistroCompra>();
    }

    public void agregarRegistro(RegistroCompra registro){
        this.registros.add(registro);
    }

    public void eliminarRegistro(Integer nroControl){
        this.registros.removeIf(registro -> registro.getNumeroControl().equals(nroControl));
    }

    public boolean tieneRegistro(Integer nroControl){
        return this.registros.stream().anyMatch(registro -> registro.getNumeroControl().equals(nroControl));
    }
}
