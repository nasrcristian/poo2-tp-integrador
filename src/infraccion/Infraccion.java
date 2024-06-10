package infraccion;


import inspector.Inspector;
import zona.ZonaDeEstacionamiento;
import java.time.LocalDate;
import java.time.LocalTime;

public class Infraccion {
    private String patente;
    private LocalDate fecha;
    private LocalTime hora;
    private ZonaDeEstacionamiento zona;
    private Inspector inspector;

    public Infraccion(String patente, LocalDate fecha, LocalTime hora, ZonaDeEstacionamiento zona, Inspector inspector){
        this.patente = patente;
        this.fecha = fecha;
        this.hora = hora;
        this.zona = zona;
        this.inspector = inspector;
    }

    public String getPatente(){
        return this.patente;
    }

    public LocalDate getFecha(){
        return this.fecha;
    }

    public LocalTime getHora(){
        return this.hora;
    }

    public ZonaDeEstacionamiento getZona(){
        return this.zona;
    }

    public Inspector getInspector(){
        return this.inspector;
    }
}
