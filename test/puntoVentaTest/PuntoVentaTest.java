package puntoVentaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puntoVenta.PuntoVenta;
import sistema.SistemaCentral;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PuntoVentaTest {

    private PuntoVenta puntoVenta;
    private SistemaCentral sistemaMock;
    private int numeroDeControl;

    @BeforeEach
    void setUp() {
        sistemaMock = mock(SistemaCentral.class);
        puntoVenta = new PuntoVenta(sistemaMock);
    }

    @Test
    void testCargarCredito() {
        int numeroCelular = 123456789;
        float monto = 104.5f;
        assertDoesNotThrow(() -> puntoVenta.cargarCredito(numeroCelular,monto));
    }

    @Test
    void testAumentarNumeroDeControl() {
        puntoVenta.aumentarNumeroDeControl();
        assertEquals(2, puntoVenta.getNroControl());
    }

    @Test
    void testCompraPuntual() {
        String patente = "AAA000";
        int cantHoras = 4;
        puntoVenta.compraPuntual(patente, cantHoras);
        assertEquals(2, puntoVenta.getNroControl());
    }

    @Test
    void testGetNroControl() {
        assertEquals(1, puntoVenta.getNroControl());
    }
}