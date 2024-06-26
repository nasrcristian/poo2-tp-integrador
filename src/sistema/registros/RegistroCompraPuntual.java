package sistema.registros;

import java.time.LocalDate;
import java.time.LocalTime;

import sistema.zona.PuntoVenta;

public class RegistroCompraPuntual extends RegistroCompra {
    private String patente;
    private Integer cantHoras;

    public RegistroCompraPuntual(Integer numeroControl, PuntoVenta puntoVenta, LocalDate fecha, LocalTime hora, String patente, Integer cantHoras) {
        super(numeroControl, puntoVenta, fecha, hora);
        this.patente = patente;
        this.cantHoras = cantHoras;
    }

    public String getPatente() {
        return patente;
    }

    public Integer getCantHoras() {
        return cantHoras;
    }

}
