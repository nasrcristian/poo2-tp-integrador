package sistema.estacionamiento;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorEstacionamiento {
    
	float costoPorHora;
    LocalTime horarioApertura;
    LocalTime horarioCierre;
    List<Estacionamiento> estacionamientosActuales;
    
    public GestorEstacionamiento(float costoPorHora, LocalTime horarioApertura, LocalTime hoarioFin) {
		this.costoPorHora = costoPorHora;
		this.horarioApertura = horarioApertura;
		this.horarioCierre = hoarioFin;
		this.estacionamientosActuales = new ArrayList<Estacionamiento>();
	}

    public boolean estaVigente(String patente) {
		Optional<Estacionamiento> estacionamientoBuscado = this.estacionamientosActuales.stream().filter(e -> e.getPatente() == patente).findFirst();
		return estacionamientoBuscado.isPresent() ? estacionamientoBuscado.get().estaVigente(this.horarioApertura, this.horarioCierre) : false;
	}
    
    
}
