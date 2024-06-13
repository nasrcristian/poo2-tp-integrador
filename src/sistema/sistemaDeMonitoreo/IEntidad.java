package sistema.sistemaDeMonitoreo;

import sistema.estacionamiento.Estacionamiento;
import sistema.registros.RegistroRecarga;

public interface IEntidad {

	public void actualizarInicioDeEstacionamientoDe(Estacionamiento estacionamiento);
	
	public void actualizarFinalizacionDeEstacionamientoDe(Estacionamiento estacionamiento);

	public void actualizarConRecargaDeCredito(RegistroRecarga recarga);

}
