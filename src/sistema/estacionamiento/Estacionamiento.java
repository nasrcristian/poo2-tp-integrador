package sistema.estacionamiento;

import java.time.LocalTime;

public abstract class Estacionamiento {
	private String patente;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	
	
	public Estacionamiento(String patente, LocalTime horaInicio, LocalTime horaFin) {
		this.patente = patente;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}


	public String getPatente() {
		return this.patente;
	}


	public LocalTime getHoraInicio() {
		return this.horaInicio;
	}


	public LocalTime getHoraFin() {
		return this.horaFin;
	}
	
	public boolean estaVigente(LocalTime horarioApertura, LocalTime horarioCierre) {
		LocalTime horaActual = LocalTime.now();
		return this.horaInicio.isAfter(horarioApertura) && this.horaFin.isBefore(horarioCierre) && this.horaFin.isAfter(horaActual)  ;
	}
}