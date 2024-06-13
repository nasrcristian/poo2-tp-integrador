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
	private EstadoApp estado;
	private ModoDeApp modo;
	private SistemaCentral sistema;
	private Notificador notificador;
	private AsistenciaAlUsuario asistencia;
	
	
	public AppCliente(String patente, int nro, SistemaCentral sistema, Notificador notificador) {
		this.patente = patente;
		this.nroCelular = nro;
		this.sistema = sistema;
		this.estado = new SinEstacionamiento(); //por defecto la app no tiene estacionamiento.
		this.setModo(new ModoManual()); // Por defecto la app se encuentra en modo manual.
		this.setAsistencia(new AsistenciaActivada());
		this.notificador = notificador; // Es un objeto externo que se encargaría de manejar las notificaciones que reciba la app.
		this.crearCuenta();
	}
	
	public int getNumero() {
		return this.nroCelular;
	}

	public String getPatente() {
		return this.patente;
	}
	
	public SistemaCentral getSistema(){
		return this.sistema;
	}

	//estado de la aplicacion
	protected void setEstado(EstadoApp estado){
		this.estado = estado;
	}

	public void iniciarEstacionamiento() {
		this.estado.iniciarEstacionamiento(this);
	}

	public void finalizarEstacionamiento() {
		this.estado.finalizarEstacionamiento(this);
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
		this.setModo(new ModoManual());
		// En caso de que la asistencia este desactivada, el modo automático no va a hacer nada y el modo manual no va a notificar. 
		// Pero cuando este sea activado va a volver actuar sin necesidad de activar nuevamente el mismo.
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
	public float consultaSaldo() {
		return this.sistema.consultarSaldoDe(this.nroCelular);
	}
	
	private void crearCuenta() {
		this.sistema.crearCuenta(this);
	}

	public void notificar(String mensaje) {
		this.notificador.manejarNotificacion(mensaje);;
	}

	
// GETTERS
	public EstadoApp getEstado() {
		return this.estado;
	}

	public ModoDeApp getModo() {
		return this.modo;
	}

	public AsistenciaAlUsuario getAsistencia() {
		return this.asistencia;
	}

	


}

