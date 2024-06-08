package infraccion;


import inspector.Inspector;
import zona.Zona;

import java.time.LocalDate;
import java.time.LocalTime;

public class Infraccion {
    private String patente;
    private LocalDate fecha;
    private LocalTime hora;
    private Zona zona;
    private Inspector inspector;

    public Infraccion(String patente, LocalDate fecha, LocalTime hora, Zona zona, Inspector inspector){
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

    public Zona getZona(){
        return this.zona;
    }

    public Inspector getInspector(){
        return this.inspector;
    }
}
