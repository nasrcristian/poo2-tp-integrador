package sistema;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appCliente.AppCliente;
import registroCompras.RegistroCompraPuntual;
import registroCompras.RegistroRecarga;
import sistema.Cuenta;
import sistema.SistemaCentral;
import sistema.entidadObservadora.IEntidad;
import sistema.estacionamiento.Estacionamiento;
import sistema.estacionamiento.EstacionamientoPorApp;
import sistema.estacionamiento.EstacionamientoPuntual;
import sistema.estacionamiento.GestorEstacionamiento;
import zona.Inspector;
import zona.ZonaDeEstacionamiento;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SistemaCentralTest {

	private SistemaCentral sistema;
	private GestorEstacionamiento estacionamientosMock;
	private GestorInfracciones infraccionesMock;
	private GestorRegistros registrosMock;
	private GestorCuentas cuentasMock;
	private Set<ZonaDeEstacionamiento> zonas;
	private AppCliente appMock;
	
	@BeforeEach
	public void setUp() {
		this.estacionamientosMock = mock(GestorEstacionamiento.class);
		this.infraccionesMock = mock(GestorInfracciones.class);
		this.registrosMock = mock(GestorRegistros.class);
		this.cuentasMock = mock(GestorCuentas.class);
		this.appMock = mock(AppCliente.class);
		sistema = new SistemaCentral(estacionamientosMock, infraccionesMock, registrosMock, cuentasMock, zonas);
	}
	
	@Test
	public void testCrearCuenta() {
		sistema.crearCuenta(appMock);
		verify(cuentasMock, times(1)).crearCuenta(appMock);
	}
	
	@Test
	public void testConsultaSaldo() {
		sistema.consultarSaldoDe(111);
		when(cuentasMock.getSaldo(111)).thenReturn(122f);
		verify(cuentasMock, times(1)).getSaldo(111);
	}
	
	@Test
	public void testGenerarInfraccion() {
		Inspector inspectorMock = mock(Inspector.class);
		String patente = "AAA000";
		sistema.generarInfraccion(patente, inspectorMock);
		verify(infraccionesMock, times(1)).generarInfraccion(patente, inspectorMock);	
	}
	
	@Test
	public void testSuscribirEntidad () {
		IEntidad entidadMock = mock(IEntidad.class);
		sistema.suscribirEntidad(entidadMock);
		assertFalse(sistema.getEntidades().isEmpty());
	}
	
	@Test
	public void testDesuscribirEntidad () {
		IEntidad entidadMock = mock(IEntidad.class);
		sistema.suscribirEntidad(entidadMock);
		sistema.desuscribirEntidad(entidadMock);
		assertTrue(sistema.getEntidades().isEmpty());
	}
	
	@Test
	public void testNotificarInicioDeEstacionamientoDe() {
		IEntidad entidadMock = mock(IEntidad.class);
		sistema.suscribirEntidad(entidadMock);
		sistema.notificarInicioDeEstacionamientoDe(any());
		verify(entidadMock, times(1)).actualizarInicioDeEstacionamientoDe(any());
	}
	
	@Test
	public void testNotificarFinDeEstacionamientoDe() {
		IEntidad entidadMock = mock(IEntidad.class);
		sistema.suscribirEntidad(entidadMock);
		sistema.notificarFinDeEstacionamientoDe(any());
		verify(entidadMock, times(1)).actualizarFinalizacionDeEstacionamientoDe(any());
	}
	
	@Test
	public void testNotificarRecargaDeCreditoDe() {
		IEntidad entidadMock = mock(IEntidad.class);
		sistema.suscribirEntidad(entidadMock);
		sistema.notificarRecargaDeCreditoDe(any());
		verify(entidadMock, times(1)).actualizarConRecargaDeCredito(any());
	}
	
	@Test
	public void testIniciarEstacionamientoPuntualLlamaAEstacionamientos() {
		sistema.iniciarEstacionamientoPuntual(any());
		verify(estacionamientosMock, times(1)).iniciarEstacionamientoPuntualConOrden(any());
	}
	
	@Test
	public void testIniciarEstacionamientoPuntualLlamaARegistros() {
		sistema.iniciarEstacionamientoPuntual(any());
		verify(registrosMock, times(1)).agregarRegistro(any());
	}
	
	@Test
	public void testIniciarEstacionamientoPuntualNotificaAEntidades() {
		IEntidad entidadMock = mock(IEntidad.class);
		sistema.suscribirEntidad(entidadMock);
		sistema.iniciarEstacionamientoPuntual(any());
		verify(entidadMock, times(1)).actualizarInicioDeEstacionamientoDe(any());
	}
	
	@Test
	public void testEstaVigentePatenteLlamaAEstacionamientos() {
		sistema.tieneEstacionamientoVigente(any());
		verify(estacionamientosMock, times(1)).estaVigente(any());
	}
	
	@Test
	public void testEstaVigentePorCelularLlamaAcuentas() {
		try {
			sistema.tieneEstacionamientoVigente(111000);
			verify(cuentasMock, times(1)).getPatenteSiPuede(111000);
		} catch (Exception e) {
			return;
		}
		}
	
	@Test
	public void testEstaVigentePorCelularLlamaAEstacionamientosSiNoFalla() {
		try {
		String patente = "AAA000";
		int celular = 111000;
		sistema.tieneEstacionamientoVigente(celular);
		when(cuentasMock.getPatenteSiPuede(celular)).thenReturn(patente);
		verify(estacionamientosMock, times(1)).estaVigente(any());
		} catch (Exception e) {
			fail();
		}
	}

	
	@Test
	public void testFinalizarElDiaLlamaAEstacionamientos() {
		sistema.finalizarDia();
		verify(estacionamientosMock, times(1)).finalizarEstacionamientosVigentes(sistema);
	}
	
	@Test
	public void testIniciarEstacionamientoSiPuedeParaRetornaFalsoSiNoHayCuenta(){
		assertFalse(sistema.iniciarEstacionamientoSiPuedePara(111000));
	}
	
	@Test
	public void testIniciarEstacionamientoSiPuedeLlamaAEstacionamientosSiHayCuenta() throws Exception{
		AppCliente appMock = mock(AppCliente.class);
		int celular = 111000;
		Cuenta mockCuenta = mock(Cuenta.class);
		Optional<Cuenta> optionalMock = Optional.of(mockCuenta);
		when(cuentasMock.getCuenta(celular)).thenReturn(optionalMock);
		when(mockCuenta.getApp()).thenReturn(appMock);
		sistema.iniciarEstacionamientoSiPuedePara(celular);
		verify(estacionamientosMock, times(1)).iniciarEstacionamientoPara(mockCuenta);
	}
	
	@Test
	public void testIniciarEstacionamientoSiPuedeLlamaANotificarAppSiHayCuenta() throws Exception{
		AppCliente appMock = mock(AppCliente.class);
		EstacionamientoPorApp ticketMock = mock(EstacionamientoPorApp.class);
		int celular = 111000;
		Cuenta mockCuenta = mock(Cuenta.class);
		Optional<Cuenta> optionalMock = Optional.of(mockCuenta);
		when(cuentasMock.getCuenta(celular)).thenReturn(optionalMock);
		when(mockCuenta.getApp()).thenReturn(appMock);
		when(estacionamientosMock.iniciarEstacionamientoPara(mockCuenta)).thenReturn(ticketMock);
		when(ticketMock.getHoraInicio()).thenReturn(LocalTime.now());
		when(ticketMock.getHoraFin()).thenReturn(LocalTime.now());
		
		sistema.iniciarEstacionamientoSiPuedePara(celular);
		
		verify(appMock, times(1)).notificar(any());
	}
	
	@Test
	public void testFinalizarEstacionamientoSinCuentaNoHaceNada() throws Exception {
		int cuenta = 111000;
		sistema.finalizarEstacionamientoSiPuedePara(cuenta);
		Optional<Cuenta> optionalMock = Optional.empty();
		when(cuentasMock.getCuenta(cuenta)).thenReturn(optionalMock);
		verify(estacionamientosMock, times(0)).finalizarEstacionamientoPara(any());
	}
	
	@Test
	public void testFinalizarEstacionamientoConCuentaLlamaADescontarCredito() throws Exception {
		AppCliente appMock = mock(AppCliente.class);
		int celular = 111000;
		String patente = "AAA000";
		Cuenta mockCuenta = mock(Cuenta.class);
		Optional<Cuenta> optionalMock = Optional.of(mockCuenta);
		EstacionamientoPorApp ticketMock = mock(EstacionamientoPorApp.class);
		float costo = 40f;
		
		when(cuentasMock.getCuenta(celular)).thenReturn(optionalMock);
		when(mockCuenta.getApp()).thenReturn(appMock);
		when(mockCuenta.getPatente()).thenReturn(patente);
		when(mockCuenta.getNroCelular()).thenReturn(celular);
		

		when(estacionamientosMock.finalizarEstacionamientoPara(patente)).thenReturn(ticketMock);
		when(ticketMock.getCostoTotal()).thenReturn(costo);
		
		sistema.finalizarEstacionamientoSiPuedePara(celular);
		
		
		verify(cuentasMock, times(1)).descontarCredito(celular, costo);
	}
	
	@Test
	public void testFinalizarEstacionamientoSiPuedeLlamaANotificarAppSiHayCuenta() throws Exception{
		AppCliente appMock = mock(AppCliente.class);
		int celular = 111000;
		String patente = "AAA000";
		Cuenta mockCuenta = mock(Cuenta.class);
		Optional<Cuenta> optionalMock = Optional.of(mockCuenta);
		EstacionamientoPorApp ticketMock = mock(EstacionamientoPorApp.class);
		float costo = 40f;
		
		when(cuentasMock.getCuenta(celular)).thenReturn(optionalMock);
		when(mockCuenta.getApp()).thenReturn(appMock);
		when(estacionamientosMock.finalizarEstacionamientoPara(patente)).thenReturn(ticketMock);
		when(ticketMock.getHoraInicio()).thenReturn(LocalTime.now());
		when(ticketMock.getHoraFin()).thenReturn(LocalTime.now());
		
		sistema.iniciarEstacionamientoSiPuedePara(celular);
		
		verify(appMock, times(1)).notificar(any());
	}
	
	@Test
	public void testTieneEstacionamientoVigentePorCelularRetornaFalsoSiNoHayCuenta() {
			assertFalse(sistema.tieneEstacionamientoVigente(111000));
		}

	@Test
	public void testCargarCreditoDeLaOrdenSiPuedeLlamaACuentasSiNoFalla() throws Exception {
		sistema.cargarCreditoDeLaOrdenSiPuede(any());
		verify(cuentasMock, times(1)).cargarCreditoSiPuede(any());
	}
	
	@Test
	public void testCargarCreditoDeLaOrdenSiPuedeLlamaARegistrosSiNoFalla() throws Exception {
		sistema.cargarCreditoDeLaOrdenSiPuede(any());
		verify(registrosMock, times(1)).agregarRegistro(any());
	}
	
	@Test
	public void testCargarCreditoDeLaOrdenSiPuedeNotificaAEntidadesSiNoFalla() throws Exception {
		sistema.cargarCreditoDeLaOrdenSiPuede(any());
		verify(cuentasMock, times(1)).cargarCreditoSiPuede(any());
	}
}
	

