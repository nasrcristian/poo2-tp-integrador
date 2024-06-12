package sistema.estacionamiento;

import java.time.LocalTime;

public class EstacionamientoPorApp extends Estacionamiento {

	private Integer numeroDeCelular;
	
	public EstacionamientoPorApp(String patente, LocalTime horaInicio, LocalTime horaFin, float costoPorHora, Integer numeroDeCelular) {
		super(patente, horaInicio, horaFin, costoPorHora);
		this.numeroDeCelular = numeroDeCelular;
	}

	public Integer getNumeroDeCelular() {
		return this.numeroDeCelular;
	}
	
	public void finalizar() {
		this.setHoraFin(LocalTime.now());
	}
}
