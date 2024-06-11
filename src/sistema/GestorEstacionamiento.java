package sistema;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class GestorEstacionamiento {
    float costoPorHora;
    LocalTime horarioInicio;
    LocalTime horarioFin;
    List<Estacionamiento> estacionamientosActuales;
    
	public boolean estaVigente(String patente) {
		Optional<Estacionamiento> estacionamientoBuscado = this.estacionamientosActuales.stream().filter(e -> e.getPatente() == patente).findFirst();
		return estacionamientoBuscado.map(Estacionamiento::estaVigente).orElse(false);
	}
    
    
}
