package registroCompras;

import appCliente.AppCliente;
import puntoVenta.PuntoVenta;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroRecarga extends RegistroCompra {
    private int celular;
    private Float monto;

    public RegistroRecarga(Integer numeroControl, PuntoVenta puntoVenta, LocalDate fecha, LocalTime hora, int numeroDeCelular, Float monto) {
        super(numeroControl, puntoVenta, fecha, hora);
        this.celular = numeroDeCelular;
        this.monto = monto;
    }

    public int getCelular() {
        return celular;
    }

    public Float getMonto() {
        return monto;
    }
}
