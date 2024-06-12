package appCliente.modo;

import appCliente.AppCliente;

public class ModoManual extends ModoDeApp{


	@Override
	protected String mensajeIniciarEstacionamiento() {
		return "Se han detectado cambios, desea iniciar su estacionamiento?";
	}

	@Override
	protected String mensajeFinalizarEstacionamiento() {
		return "Se han detectado cambios, desea finalizar su estacionamiento?";
	}

	@Override
	protected void manejarIniciarEstacionamiento(AppCliente app) {
		// No tiene ningun comportamiento especial.
	}

	@Override
	protected void manejarFinalizarEstacionamiento(AppCliente app) {
		// No tiene ningun comportamiento especial.
	}
}
