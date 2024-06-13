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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void testNoEstaVigente() {
        String patente = "AAA000";
        assertFalse(gestor.estaVigente(patente));
    }

    @Test
    void testEstaVigente() {
        String patente = "AAA000";
        assertFalse(gestor.estaVigente(patente));
    }

    @Test
    void testIniciarEstacionamientoPuntualConOrden() {
        RegistroCompraPuntual ordenCompraMock = mock(RegistroCompraPuntual.class);
        assertTrue(gestor.iniciarEstacionamientoPuntualConOrden(ordenCompraMock) instanceof EstacionamientoPuntual);
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
        assertThrows(Exception.class, () -> gestor.iniciarEstacionamientoPara(cuentaMock));
    }

    @Test
    void testFinalizarEstacionamientoPara() {
        String patente = "AAA000";
        assertThrows(Exception.class, () -> gestor.finalizarEstacionamientoPara(patente));
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