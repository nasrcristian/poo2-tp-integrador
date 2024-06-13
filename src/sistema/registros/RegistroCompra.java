package sistema.registros;

import java.time.LocalDate;
import java.time.LocalTime;

import sistema.zona.PuntoVenta;

public abstract class RegistroCompra {
    private Integer numeroControl;
    private PuntoVenta puntoVenta;
    private LocalDate fecha;
    private LocalTime hora;

    public RegistroCompra(Integer numeroControl, PuntoVenta puntoVenta, LocalDate fecha, LocalTime hora){
        this.numeroControl = numeroControl;
        this.puntoVenta = puntoVenta;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getNumeroControl(){
        return this.numeroControl;
    }

    public PuntoVenta getPuntoVenta(){
        return this.puntoVenta;
    }

    public LocalDate getFecha(){
        return this.fecha;
    }

    public LocalTime getHora(){
        return this.hora;
    }
}
