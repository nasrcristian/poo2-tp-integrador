package appCliente.modo;

import appCliente.AppCliente;

public abstract class ModoDeApp {

	public final void asistenciaInicioEstacionamientoPara(AppCliente app) {
		this.alertarUsuario(app, this.mensajeIniciarEstacionamiento());
		this.manejarIniciarEstacionamiento(app);
	}
	
	public final void asistenciaFinDeEstacionamientoPara(AppCliente app) {
		this.alertarUsuario(app, this.mensajeFinalizarEstacionamiento());
		this.manejarFinalizarEstacionamiento(app);
	}
	
	protected void alertarUsuario(AppCliente app, String mensaje) {
		app.notificar(mensaje);
	}
	
	protected abstract String mensajeIniciarEstacionamiento();
	protected abstract String mensajeFinalizarEstacionamiento();
	protected abstract void manejarIniciarEstacionamiento(AppCliente app);
	protected abstract void manejarFinalizarEstacionamiento(AppCliente app);

}
