package sistema.estacionamiento;

import java.time.LocalTime;

public class Estacionamiento {
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

	//TODO revisar para compra puntual: la compra puede ser de otro dia pero respetar los horarios
	public boolean estaVigente(LocalTime horarioApertura, LocalTime horarioCierre) {
		LocalTime horaActual = LocalTime.now();
		return this.horaInicio.isAfter(horarioApertura) && this.horaFin.isBefore(horarioCierre) && this.horaFin.isAfter(horaActual)  ;
	}

	//metodos para evento de fin estacionamiento
	public int getDuracion() throws Exception {
		this.vertificarVigencia();
		return this.horaInicio.getHour() - this.horaFin.getHour();
	}

	private void vertificarVigencia() throws Exception {
		if(this.estaVigente()){throw new Exception("No hay estacionamiento vigente para consultar duracion");}
	}

	private boolean estaVigente(){
		return this.horaInicio.isBefore(LocalTime.now()) && this.horaFin.isAfter(LocalTime.now());
	}
}
