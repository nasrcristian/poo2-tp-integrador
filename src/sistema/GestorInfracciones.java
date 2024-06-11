package sistema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import infraccion.Infraccion;
import inspector.Inspector;

public class GestorInfracciones {
    private List<Infraccion> infracciones;
    private SistemaCentral sistema;

    public GestorInfracciones(SistemaCentral sistema){
        infracciones = new ArrayList<Infraccion>();
        this.sistema = sistema;
    }

    //consulta al gestor de estacionamientos si tiene guardado algun estacionamiento con esa patente y en el horario que se consulta 
    public boolean tieneEstacionamientoVigente(String patente){
        return this.sistema.tieneEstacionamientoVigente(patente);
    }

    //consulta en la lista de infracciones si hay alguna infraccion con la patente ingresada
    public boolean tieneInfraccion(String patente){
        return this.infracciones.stream().anyMatch(infraccion -> infraccion.getPatente().equals(patente));
    }

    //guarda una infraccion con los datos correspondientes
    public void generarInfraccion(String patente, Inspector inspector){
        infracciones.add(new Infraccion(patente, LocalDate.now(), LocalTime.now(), inspector.getZona(), inspector));
    }

}
