package registroCompras;

import appCliente.AppCliente;
import puntoVenta.PuntoVenta;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroRecarga extends RegistroCompra {
    private AppCliente celular;
    private Float monto;

    public RegistroRecarga(Integer numeroControl, PuntoVenta puntoVenta, LocalDate fecha, LocalTime hora, AppCliente celular, Float monto) {
        super(numeroControl, puntoVenta, fecha, hora);
        this.celular = celular;
        this.monto = monto;
    }

    public AppCliente getCelular() {
        return celular;
    }

    public Float getMonto() {
        return monto;
    }
}
