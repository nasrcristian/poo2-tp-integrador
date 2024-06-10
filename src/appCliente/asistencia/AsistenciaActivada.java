package appCliente.asistencia;

import appCliente.AppCliente;

public class AsistenciaActivada implements AsistenciaAlUsuario{

	@Override
	public void manejarCambioAPiePara(AppCliente app) {
		app.asistenciaInicioEstacionamiento();	
	}

	@Override
	public void manejarCambioAVehiculoPara(AppCliente app) {
		app.asistenciaFinEstacionamiento();
	}
}
