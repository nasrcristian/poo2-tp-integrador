package sistema;

import appCliente.AppCliente;
import sistema.cuentas.Cuenta;
import sistema.cuentas.GestorCuentas;
import sistema.registros.RegistroRecarga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorCuentasTest {

    private GestorCuentas gestor;
    private Map<Integer, Cuenta> cuentas;

    @BeforeEach
    void setUp() {
        gestor = new GestorCuentas();
    }

    @Test
    void testCrearCuenta() {
        AppCliente appClienteMock = mock(AppCliente.class);
        when(appClienteMock.getNumero()).thenReturn(123456789);
        gestor.crearCuenta(appClienteMock);
        verify(appClienteMock, times(1)).getNumero();
    }

    @Test
    void testGetPatente() {
        AppCliente appClienteMock = mock(AppCliente.class);
        when(appClienteMock.getNumero()).thenReturn(123456789);
        gestor.crearCuenta(appClienteMock);
        when(appClienteMock.getPatente()).thenReturn("AAA000");
        assertDoesNotThrow(() -> gestor.getPatenteSiPuede(appClienteMock.getNumero()));
        verify(appClienteMock, times(1)).getPatente();
    }

    // @Test
    //TODO arreglar test, deberia capturar bien la excepcion
//    void testNoHayPatente() {
//        int numeroCelularSinCuenta = 123456789;
//        Exception excepcion = assertThrows(Exception.class, () -> gestor.getPatenteSiPuede(numeroCelularSinCuenta));
//        assertEquals("El numero ingresado no tiene ninguna patente registrada.", excepcion.getMessage());
//    }

    @Test
    void testGetCuenta() {
        AppCliente appClienteMock = mock(AppCliente.class);
        when(appClienteMock.getNumero()).thenReturn(123456789);
        gestor.crearCuenta(appClienteMock);
        gestor.getCuenta(appClienteMock.getNumero());
        verify(appClienteMock, times(2)).getNumero();
    }

    @Test
    void testGetSaldo() {
        AppCliente appClienteMock = mock(AppCliente.class);
        when(appClienteMock.getNumero()).thenReturn(123456789);
        gestor.crearCuenta(appClienteMock);
        assertEquals(0, gestor.getSaldo(appClienteMock.getNumero()));
    }

    @Test
    void testDescontarCredito() {
        AppCliente appClienteMock = mock(AppCliente.class);
        when(appClienteMock.getNumero()).thenReturn(123456789);
        gestor.crearCuenta(appClienteMock);
        RegistroRecarga ordenRecargaMock = mock(RegistroRecarga.class);
        when(ordenRecargaMock.getCelular()).thenReturn(123456789);
        when(ordenRecargaMock.getMonto()).thenReturn(20f);
        try {
            gestor.cargarCreditoSiPuede(ordenRecargaMock);
            assertEquals(20, gestor.getSaldo(appClienteMock.getNumero()));
            float montoDescarga = 10f;
            gestor.descontarCredito(appClienteMock.getNumero(), montoDescarga);
            assertEquals(10, gestor.getSaldo(appClienteMock.getNumero()));
        } catch (Exception e) {
            fail("Test mal hecho: " + e.getMessage());
        }
    }

    @Test
    void testCargarCreditoSiPuede() {
        AppCliente appClienteMock = mock(AppCliente.class);
        when(appClienteMock.getNumero()).thenReturn(123456789);
        gestor.crearCuenta(appClienteMock);
        RegistroRecarga ordenRecargaMock = mock(RegistroRecarga.class);
        when(ordenRecargaMock.getCelular()).thenReturn(123456789);
        when(ordenRecargaMock.getMonto()).thenReturn(20f);
        try {
            gestor.cargarCreditoSiPuede(ordenRecargaMock);
            assertEquals(20, gestor.getSaldo(appClienteMock.getNumero()));
        } catch (Exception e) {
            fail("Test mal hecho: " + e.getMessage());
        }
    }
}