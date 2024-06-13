package estacionamientoTest;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registroCompras.RegistroCompra;
import registroCompras.RegistroCompraPuntual;
import sistema.Cuenta;
import sistema.SistemaCentral;
import sistema.estacionamiento.Estacionamiento;
import sistema.estacionamiento.EstacionamientoPorApp;
import sistema.estacionamiento.EstacionamientoPuntual;
import sistema.estacionamiento.GestorEstacionamiento;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorEstacionamientoTest {

    GestorEstacionamiento gestor;
    float costoPorHora= 5f;
    LocalTime horarioApertura = LocalTime.of(7,0);
    LocalTime horarioCierre = LocalTime.of(20,0);
    List<EstacionamientoPuntual> estacionamientosPuntualesDelDia;
    List<EstacionamientoPorApp>  estacionamientosPorAppDelDia;
    List<Estacionamiento>		 estacionamientosHistoricos;

    @BeforeEach
    void setUp() {
        gestor = new GestorEstacionamiento(costoPorHora, horarioApertura, horarioCierre);
    }

    @Test
    void testEstaVigente() {
        String patente = "AAA000";
        RegistroCompraPuntual ordenCompraMock = mock(RegistroCompraPuntual.class);
        when(ordenCompraMock.getPatente()).thenReturn(patente);
        when(ordenCompraMock.getCantHoras()).thenReturn(2);
        gestor.iniciarEstacionamientoPuntualConOrden(ordenCompraMock);
        assertTrue(gestor.estaVigente(patente));
    }

    @Test
    void testIniciarEstacionamientoPuntualConOrden() {
        RegistroCompraPuntual ordenCompraMock = mock(RegistroCompraPuntual.class);
        when(ordenCompraMock.getPatente()).thenReturn("AAA000");
        when(ordenCompraMock.getCantHoras()).thenReturn(2);
        EstacionamientoPuntual estacionamientoPuntualTest = gestor.iniciarEstacionamientoPuntualConOrden(ordenCompraMock);
        assertTrue(gestor.getEstacionamientosPuntualesDelDia().contains(estacionamientoPuntualTest));
    }

    @Test
    void testCalcularHorarioInicio() {
        assertEquals(LocalTime.now(), gestor.calcularHorarioInicio());
    }

    @Test
    void testCalcularHorarioFin() {
        int cantHoras = 10;
        assertEquals(LocalTime.now().plusHours(cantHoras),gestor.calcularHorarioFin(cantHoras));
    }

    @Test
    void testIniciarEstacionamientoPara() {
        Cuenta cuentaMock = mock(Cuenta.class);
        String patente = "AAA000";
        int numeroCelular = 123456789;
        when(cuentaMock.getSaldo()).thenReturn(10f);
        when(cuentaMock.getPatente()).thenReturn(patente);
        when(cuentaMock.getNroCelular()).thenReturn(numeroCelular);
        try{
            gestor.iniciarEstacionamientoPara(cuentaMock);
            assertTrue(gestor.estaVigente(cuentaMock.getPatente()));
        }catch (Exception e){
            fail("Test fallo: " + e.getMessage());
        }
    }

    @Test
    void testFinalizarEstacionamientoPara() {
        Cuenta cuentaMock = mock(Cuenta.class);
        String patente = "AAA000";
        int numeroCelular = 123456789;
        when(cuentaMock.getSaldo()).thenReturn(10f);
        when(cuentaMock.getPatente()).thenReturn(patente);
        when(cuentaMock.getNroCelular()).thenReturn(numeroCelular);
        try{
            gestor.iniciarEstacionamientoPara(cuentaMock);
            assertTrue(gestor.estaVigente(cuentaMock.getPatente()));
            gestor.finalizarEstacionamientoPara(cuentaMock.getPatente());
            assertFalse(gestor.estaVigente(cuentaMock.getPatente()));
        }catch (Exception e){
            fail("Test fallo: " + e.getMessage());
        }
    }

    @Test
    void testFinalizarEstacionamientosVigentes() {
        SistemaCentral sistemaMock = mock(SistemaCentral.class);
        RegistroCompraPuntual ordenCompraMock = mock(RegistroCompraPuntual.class);
        gestor.iniciarEstacionamientoPuntualConOrden(ordenCompraMock);
        assertTrue(gestor.getEstacionamientosPuntualesDelDia().size() == 1);
        gestor.finalizarEstacionamientosVigentes(sistemaMock);
        assertTrue(gestor.getEstacionamientosPorAppDelDia().isEmpty());
        assertTrue(gestor.getEstacionamientosPuntualesDelDia().isEmpty());
    }
}