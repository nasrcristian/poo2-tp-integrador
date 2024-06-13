package sistema.estacionamiento;

import java.time.LocalTime;

import sistema.registros.RegistroCompraPuntual;

public class EstacionamientoPuntual extends Estacionamiento {

	private RegistroCompraPuntual ordenDeCompra;
	
	public EstacionamientoPuntual(String patente, LocalTime horaInicio, LocalTime horaFin, float costoPorHora, RegistroCompraPuntual ordenDeEstacionamientoPuntual) {
		super(patente, horaInicio, horaFin, costoPorHora);
		this.ordenDeCompra = ordenDeEstacionamientoPuntual;
	}
	
	public RegistroCompraPuntual getRegistroCompra() {
		return this.ordenDeCompra;
	}

}
