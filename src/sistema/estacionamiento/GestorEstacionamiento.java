package sistema.estacionamiento;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sistema.Cuenta;

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
		return this.estacionamientosActuales.stream().anyMatch(e -> e.getPatente() == patente && e.estaVigente(horarioApertura, horarioCierre));
	}

	public boolean haySaldoSuficiente(float saldoCliente) {
		// TODO Auto-generated method stub
		return false;
	}


    
}
