package sistema.entidadObservadora;

import registroCompras.RegistroRecarga;
import sistema.estacionamiento.Estacionamiento;

public interface IEntidad {

	public void actualizarInicioDeEstacionamientoDe(Estacionamiento estacionamiento);
	
	public void actualizarFinalizacionDeEstacionamientoDe(Estacionamiento estacionamiento);

	public void actualizarConRecargaDeCredito(RegistroRecarga recarga);

}
