package appCliente.modo;

import appCliente.AppCliente;

public class ModoAutomatico extends ModoDeApp{

	@Override
	protected String mensajeIniciarEstacionamiento() {
		return "Se han detectado cambios, por lo que su estacionamiento ha sido inciado automaticamente.";
	}

	@Override
	protected String mensajeFinalizarEstacionamiento() {
		return "Se han detectado cambios, por lo que su estacionamiento ha sido finalizado automaticamente.";
	}

	@Override
	protected void manejarIniciarEstacionamiento(AppCliente app) {
		app.iniciarEstacionamiento();
	}

	@Override
	protected void manejarFinalizarEstacionamiento(AppCliente app) {app.finalizarEstacionamiento();}
}
