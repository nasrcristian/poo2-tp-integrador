package appCliente;

import appCliente.asistencia.AsistenciaActivada;
import appCliente.asistencia.AsistenciaAlUsuario;
import appCliente.asistencia.AsistenciaDesactivada;
import appCliente.modo.ModoAutomatico;
import appCliente.modo.ModoDeApp;
import appCliente.modo.ModoManual;
import sistema.SistemaCentral;

public class AppCliente implements MovementSensor{
	private String patente;
	private int nroCelular;
	private ModoDeApp modo;
	private SistemaCentral sistema;
	private Notificador notificador;
	private AsistenciaAlUsuario asistencia;
	
	
	public AppCliente(String patente, int nro, SistemaCentral sistema, Notificador notificador) {
		this.patente = patente;
		this.nroCelular = nro;
		this.sistema = sistema;
		this.setModo(new ModoManual()); // Por defecto la app se encuentra en modo manual.
		this.setAsistencia(new AsistenciaActivada());
		this.notificador = notificador; // Es un objeto externo que se encargaría de manejar las notificaciones que reciba la app.
	}
	
	public int getNumero() {
		return this.nroCelular;
	}
	
	// Modo de la aplicacion
	private void setModo(ModoDeApp m) {
		this.modo = m;
	}
	
	public void activarModoAutomatico() {
		this.setModo(new ModoAutomatico());
	}
	
	public void activarModoManual() {
		this.setModo(new ModoManual());
	}
	
	
	
	
	
	// Asistencia al usuario
	
	private void setAsistencia(AsistenciaAlUsuario a) {
		this.asistencia = a;
	}
	
	public void activarAsistencia() {
		this.setAsistencia(new AsistenciaActivada());
	}
	
	public void desactivarAsistencia() {
		this.setAsistencia(new AsistenciaDesactivada());
	}
	
	@Override
	public void driving() {
		this.asistencia.manejarCambioAVehiculoPara(this);
	}
	
	@Override
	public void walking() {
		this.asistencia.manejarCambioAPiePara(this);
	}
	
	
	public void asistenciaFinEstacionamiento() {
		if (this.sistema.tieneEstacionamientoVigente(this.nroCelular)) {
			this.modo.asistenciaFinDeEstacionamientoPara(this);
		}
	}

	public void asistenciaInicioEstacionamiento() {
		if (!this.sistema.tieneEstacionamientoVigente(this.nroCelular)) {
			this.modo.asistenciaInicioEstacionamientoPara(this);
		}
	}
	
	
	
	// Manejo de la app con el sistema principal
	public void iniciarEstacionamiento() {
		this.sistema.iniciarEstacionamientoPara(this);
	}
	
	public void finalizarEstacionamiento() {
		this.sistema.finalizarEstacionamientoPara(this);
	}
	
	public float consultaSaldo() {
		return this.sistema.consultarSaldoDe(this.nroCelular);
	}
	
	public void crearCuenta() {
		this.sistema.crearCuenta(this.nroCelular, this.patente);
	}

	public void notificar(String mensaje) {
		this.notificador.manejarNotificacion(mensaje);;
	}


}

