package registroComprasTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import puntoVenta.PuntoVenta;
import registroCompras.RegistroRecarga;

public class RegistroRecargaTest {
    private RegistroRecarga registroCompra;
    private PuntoVenta puntoDeVentaMock;
    private Integer nroControl = 111;
    private int nroCelular = 1122334455;
    private LocalDate dia = LocalDate.now();
    private LocalTime hora = LocalTime.now();
    private float monto = 10f;

    @BeforeEach
    public void setUp() {
        puntoDeVentaMock = mock(PuntoVenta.class);
        registroCompra = new RegistroRecarga(nroControl, puntoDeVentaMock, dia, hora, nroCelular, monto);
    }

    @Test
    public void testGetNroCelular() {
        assertEquals(nroCelular, registroCompra.getCelular());
    }

    @Test
    public void testGetMonto() {
        assertEquals(monto, registroCompra.getMonto());
    }

}

