package puntoVentaTest;

import appCliente.AppCliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.SistemaCentral;
import sistema.cuentas.Cuenta;
import sistema.cuentas.GestorCuentas;
import sistema.zona.PuntoVenta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PuntoVentaTest {

    private PuntoVenta puntoVenta;
    private SistemaCentral sistemaMock;

    @BeforeEach
    void setUp() {
        sistemaMock = mock(SistemaCentral.class);
        puntoVenta = new PuntoVenta(sistemaMock);
        
    }

    @Test
    void testCargarCredito() {
        int numeroCelular = 123456789;
        float monto = 104.5f;
        puntoVenta.cargarCredito(numeroCelular, monto);
        verify(sistemaMock, times(1)).cargarCreditoDeLaOrdenSiPuede(any()); // Solo interesa saber que recibió el mensaje.
       
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
       verify(sistemaMock, times(1)).iniciarEstacionamientoPuntual(any()); // Solo interesa saber que recibió el mensaje.
    }

    @Test
    void testGetNroControl() {
        assertEquals(1, puntoVenta.getNroControl());
    }
}