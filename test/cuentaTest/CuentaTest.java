package cuentaTest;

import appCliente.AppCliente;
import sistema.cuentas.Cuenta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaTest {

    private Cuenta cuenta;
    private float saldo;
    private AppCliente appMock;

    @BeforeEach
    void setUp() {
        appMock = mock(AppCliente.class);
        cuenta = new Cuenta(appMock);
    }

    @Test
    void testGetNroCelular() {
        cuenta.getNroCelular();
        verify(appMock, times(1)).getNumero();
    }

    @Test
    void testGetApp() {
        assertEquals(appMock, cuenta.getApp());
    }

    @Test
    void testGetSaldo() {
        assertEquals(0, cuenta.getSaldo());
    }

    @Test
    void testGetPatente() {
        cuenta.getPatente();
        verify(appMock, times(1)).getPatente();
    }

    @Test
    void testCargarCredito() {
        float monto = 10f;
        cuenta.cargarCredito(monto);
        assertEquals(10, cuenta.getSaldo());
    }

    @Test
    void testDescontarCredito() {
        float montoCargar = 10f;
        cuenta.cargarCredito(montoCargar);
        float montoDescontar = 5f;
        cuenta.descontarCredito(montoDescontar);
        assertEquals(5, cuenta.getSaldo());
    }
}