package sistema.estacionamiento;

import java.time.Duration;
import java.time.LocalTime;

public abstract class Estacionamiento {
	private String patente;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private float costoPorHora;
	
	
	public Estacionamiento(String patente, LocalTime horaInicio, LocalTime horaFin, float costoPorHora) {
		this.patente = patente;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.costoPorHora = costoPorHora;
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


	public boolean estaVigente() {
		LocalTime horaActual = LocalTime.now();
		return  horaActual.isBefore(this.horaFin) && horaActual.isAfter(this.horaInicio);
		// La verificacion de que la hora actual es antes del horario de inicio del estacionamiento es por si se hace una compra de un estacionamiento para el dia siguiente.
	}

	//metodos para evento de fin estacionamiento
	public int getDuracion() {
		return Duration.between(this.horaInicio, this.horaFin).toHoursPart();
	}
	
	protected void setHoraFin(LocalTime nuevoHorarioFin) {
		this.horaFin = nuevoHorarioFin;
	}


	public float getCostoTotal() {
		return costoPorHora * this.getDuracion();
	}
}

