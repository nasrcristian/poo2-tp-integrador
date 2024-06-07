package appCliente;

public class AsistenciaDesactivada implements AsistenciaAlUsuario{

	@Override
	public void manejarCambioAPiePara(AppCliente app) {
		// Si la asistencia esta desactivada no tiene que hacer ningun manejo con los cambios de desplazamiento
	}

	@Override
	public void manejarCambioAVehiculoPara(AppCliente app) {
		// Si la asistencia esta desactivada no tiene que hacer ningun manejo con los cambios de desplazamiento
	}

	
}
