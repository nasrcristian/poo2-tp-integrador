package estacionamientoTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.SistemaCentral;
import sistema.cuentas.Cuenta;
import sistema.estacionamiento.Estacionamiento;
import sistema.estacionamiento.EstacionamientoPorApp;
import sistema.estacionamiento.EstacionamientoPuntual;
import sistema.estacionamiento.GestorEstacionamiento;
import sistema.registros.RegistroCompraPuntual;

import java.time.LocalTime;
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
        assertEquals(LocalTime.now().getHour(), gestor.calcularHorarioInicio().getHour());
    }

    @Test
    void testCalcularHorarioFin() {
        int cantHoras = 10;
        LocalTime horaEsperada = LocalTime.now().plusHours(cantHoras);
        assertEquals(horaEsperada.isAfter(horarioCierre) ? this.horarioCierre.getHour() : horaEsperada.getHour(), gestor.calcularHorarioFin(cantHoras).getHour());
    }

    @Test
    void testIniciarEstacionamientoPara() throws Exception {
        Cuenta cuentaMock = mock(Cuenta.class);
        String patente = "AAA000";
        int numeroCelular = 123456789;
        when(cuentaMock.getSaldo()).thenReturn(80f);
        when(cuentaMock.getPatente()).thenReturn(patente);
        when(cuentaMock.getNroCelular()).thenReturn(numeroCelular);
        gestor.iniciarEstacionamientoPara(cuentaMock);
        assertFalse(gestor.getEstacionamientosPorAppDelDia().isEmpty());
    }

    @Test
    void testFinalizarEstacionamientoPara() throws Exception{
    	Cuenta cuentaMock = mock(Cuenta.class);
        String patente = "AAA000";
        int numeroCelular = 123456789;
        when(cuentaMock.getSaldo()).thenReturn(80f);
        when(cuentaMock.getPatente()).thenReturn(patente);
        when(cuentaMock.getNroCelular()).thenReturn(numeroCelular);
        gestor.iniciarEstacionamientoPara(cuentaMock);
        gestor.finalizarEstacionamientoPara(cuentaMock.getPatente());
        assertTrue(gestor.getEstacionamientosPorAppDelDia().isEmpty());
        }
    
    @Test
    void testFinalizarEstacionamientosVigentes() throws Exception{
        SistemaCentral sistemaMock = mock(SistemaCentral.class);
        RegistroCompraPuntual ordenCompraMock = mock(RegistroCompraPuntual.class);
        gestor.iniciarEstacionamientoPuntualConOrden(ordenCompraMock);
        Cuenta cuentaMock = mock(Cuenta.class);
        String patente = "AAA000";
        int numeroCelular = 123456789;
        when(cuentaMock.getSaldo()).thenReturn(80f);
        when(cuentaMock.getPatente()).thenReturn(patente);
        when(cuentaMock.getNroCelular()).thenReturn(numeroCelular);
        gestor.iniciarEstacionamientoPara(cuentaMock);
        assertFalse(gestor.getEstacionamientosPuntualesDelDia().isEmpty());
        assertFalse(gestor.getEstacionamientosPorAppDelDia().isEmpty());
        gestor.finalizarEstacionamientosVigentes(sistemaMock);
        verify(sistemaMock, times(1)).finalizarEstacionamientoSiPuedePara(numeroCelular);
        assertTrue(gestor.getEstacionamientosPuntualesDelDia().isEmpty());
    }
}