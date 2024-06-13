package appClienteTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import appCliente.*;
import appCliente.asistencia.AsistenciaActivada;
import appCliente.asistencia.AsistenciaAlUsuario;
import appCliente.asistencia.AsistenciaDesactivada;
import appCliente.modo.ModoAutomatico;
import appCliente.modo.ModoManual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sistema.SistemaCentral;

public class AppClienteTest {

    private AppCliente appCliente;
    private SistemaCentral sistemaMock;
    private Notificador notificadorMock;
    private String patente = "AAA000";
    private int nroCelular = 123456789;

    @BeforeEach
    public void setUp() {
        sistemaMock = mock(SistemaCentral.class);
        notificadorMock = mock(Notificador.class);
        mock(AsistenciaAlUsuario.class);
        appCliente = new AppCliente(patente, nroCelular, sistemaMock, notificadorMock);
        
    }

    @Test
    public void testGetNumero() {
        assertEquals(nroCelular, appCliente.getNumero());
    }

    @Test
    public void testGetPatente() {
        assertEquals(patente, appCliente.getPatente());
    }

    @Test
    public void testIniciarEstacionamientoSinSaldo() {
        when(sistemaMock.consultarSaldoDe(appCliente.getNumero())).thenReturn(0f);
        appCliente.iniciarEstacionamiento();
        verify(sistemaMock, times(1)).iniciarEstacionamientoSiPuedePara(appCliente.getNumero());
        assertTrue(appCliente.getEstado() instanceof SinEstacionamiento);
    }

    @Test
    public void testIniciarEstacionamientoConSaldo() {
        when(sistemaMock.iniciarEstacionamientoSiPuedePara(appCliente.getNumero())).thenReturn(true);
        appCliente.iniciarEstacionamiento();
        verify(sistemaMock, times(1)).iniciarEstacionamientoSiPuedePara(appCliente.getNumero());
        assertTrue(appCliente.getEstado() instanceof ConEstacionamiento);
    }
    
    @Test
    public void testIniciarEstacionamientoTeniendoUnEstacionamiento() {
    	when(sistemaMock.iniciarEstacionamientoSiPuedePara(appCliente.getNumero())).thenReturn(true);
    	appCliente.iniciarEstacionamiento();
    	appCliente.iniciarEstacionamiento();
    	verify(sistemaMock, times(1)).iniciarEstacionamientoSiPuedePara(appCliente.getNumero());
    }

    @Test
    public void testFinalizarEstacionamientoSinEstacionamiento() {
        appCliente.finalizarEstacionamiento();
        verify(sistemaMock, times(0)).finalizarEstacionamientoSiPuedePara(appCliente.getNumero());
    }

    @Test
    public void testFinalizarEstacionamientoConEstacionamiento() {
        when(sistemaMock.iniciarEstacionamientoSiPuedePara(appCliente.getNumero())).thenReturn(true);
        appCliente.iniciarEstacionamiento();
        appCliente.finalizarEstacionamiento();
        verify(sistemaMock, times(1)).finalizarEstacionamientoSiPuedePara(appCliente.getNumero());
        assertTrue(appCliente.getEstado() instanceof SinEstacionamiento);
    }

    @Test
    public void testActivarModoAutomatico() {
        appCliente.activarModoAutomatico();
        assertTrue(appCliente.getModo() instanceof ModoAutomatico);
    }

    @Test
    public void testActivarModoManual() {
        appCliente.activarModoManual();
        appCliente.getModo().asistenciaInicioEstacionamientoPara(appCliente);
        appCliente.getModo().asistenciaFinDeEstacionamientoPara(appCliente);
        assertTrue(appCliente.getModo() instanceof ModoManual);
    }

    @Test
    public void testActivarAsistencia() {
        appCliente.activarAsistencia();
        assertTrue(appCliente.getAsistencia() instanceof AsistenciaActivada);
    }

    @Test
    public void testDesactivarAsistencia() {
        appCliente.desactivarAsistencia();
        appCliente.driving();
        appCliente.walking();
        assertTrue(appCliente.getAsistencia() instanceof AsistenciaDesactivada);

    }

    @Test
    public void testConsultaSaldo() {
        float saldoEsperado = 100.0f;
        when(sistemaMock.consultarSaldoDe(nroCelular)).thenReturn(saldoEsperado);
        assertEquals(saldoEsperado, appCliente.consultaSaldo());
        verify(sistemaMock, times(1)).consultarSaldoDe(nroCelular);
    }

    @Test
    public void testCrearCuenta() {
        verify(sistemaMock, times(1)).crearCuenta(appCliente);
    }

    @Test
    public void testNotificar() {
        String mensaje = "Este es un mensaje de test";
        appCliente.notificar(mensaje);
        verify(notificadorMock, times(1)).manejarNotificacion(mensaje);
    }
    
    @Test
    public void testDrivingConAsistenciaYModoAutomatico() {
    	appCliente.activarAsistencia();
    	appCliente.activarModoAutomatico();
    	when(sistemaMock.tieneEstacionamientoVigente(appCliente.getNumero())).thenReturn(true);
    	
    	appCliente.driving();
    	
    	verify(notificadorMock, times(1)).manejarNotificacion("Se han detectado cambios, por lo que su estacionamiento ha sido finalizado automaticamente.");   	
    }
    
    @Test
    public void testWalkingConAsistenciaYModoAutomatico() {
    	appCliente.activarAsistencia();
    	appCliente.activarModoAutomatico();
    	when(sistemaMock.tieneEstacionamientoVigente(appCliente.getNumero())).thenReturn(false);
    	
    	appCliente.walking();
    	
    	verify(notificadorMock, times(1)).manejarNotificacion("Se han detectado cambios, por lo que su estacionamiento ha sido inciado automaticamente.");   	
    }
}
