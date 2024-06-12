package sistema.estacionamiento;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import registroCompras.RegistroCompraPuntual;
import sistema.Cuenta;

public class GestorEstacionamiento {
    
	float costoPorHora;
    LocalTime horarioApertura;
    LocalTime horarioCierre;
    List<EstacionamientoPuntual> estacionamientosPuntualesDelDia;
    List<EstacionamientoPorApp>  estacionamientosPorAppDelDia;
    List<Estacionamiento>		 estacionamientosHistoricos;
    
    public GestorEstacionamiento(float costoPorHora, LocalTime horarioApertura, LocalTime hoarioFin) {
		this.costoPorHora = costoPorHora;
		this.horarioApertura = horarioApertura;
		this.horarioCierre = hoarioFin;
		this.estacionamientosPuntualesDelDia = new ArrayList<EstacionamientoPuntual>();
		this.estacionamientosPorAppDelDia    = new ArrayList<EstacionamientoPorApp>();
		this.estacionamientosHistoricos 	  = new ArrayList<Estacionamiento>();
	}

    private List<Estacionamiento> getEstacionamientosActuales(){
    	List<Estacionamiento> todosLosActuales = new ArrayList<Estacionamiento>();
    	todosLosActuales.addAll(estacionamientosPorAppDelDia);
    	todosLosActuales.addAll(estacionamientosPuntualesDelDia);
    	return todosLosActuales;
    }
    
    public boolean estaVigente(String patente) {
		return this.getEstacionamientosActuales().stream().anyMatch(e -> e.getPatente() == patente && e.estaVigente());
	}

	public EstacionamientoPuntual iniciarEstacionamientoPuntualConOrden(RegistroCompraPuntual ordenDeEstacionamientoPuntual) {
		EstacionamientoPuntual ticketDeEstacionamiento = new EstacionamientoPuntual(ordenDeEstacionamientoPuntual.getPatente(), this.calcularHorarioInicio(), this.calcularHorarioFin(ordenDeEstacionamientoPuntual.getCantHoras()), this.costoPorHora, ordenDeEstacionamientoPuntual);
		estacionamientosPuntualesDelDia.add(ticketDeEstacionamiento);
		return ticketDeEstacionamiento;
	}
	
	public EstacionamientoPorApp iniciarEstacionamientoPara(Cuenta cuenta) throws Exception {
		int cantidadDeHorasPermitidas = this.calcularCantidadDeHorasParaSaldo(cuenta.getSaldo());
		if (cantidadDeHorasPermitidas > 0) {
			EstacionamientoPorApp ticketDeEstacionamiento = new EstacionamientoPorApp(cuenta.getPatente(), this.calcularHorarioInicio(), this.calcularHorarioFin(cantidadDeHorasPermitidas), this.costoPorHora, cuenta.getNroCelular());
			estacionamientosPorAppDelDia.add(ticketDeEstacionamiento);
			return ticketDeEstacionamiento;
		} else {
			throw new Exception("Saldo insuficiente. Estacionamiento no permitido.");
		}
	}

	private int calcularCantidadDeHorasParaSaldo(float saldo) {
		return (int) (saldo / this.costoPorHora);
	}

	public LocalTime calcularHorarioInicio() {
		LocalTime horaActual = LocalTime.now();
		return horaActual.isBefore(horarioApertura) || horaActual.isAfter(horarioCierre) ? this.horarioApertura : horaActual;
		// Este es un caso borde en el cual al comprar un estacionamientoPuntual en un horario en el cual ya no se trabaja el mismo se pasaria para el dia siguiente.
	}
	
	public LocalTime calcularHorarioFin(int cantHoras) {
		LocalTime horaCalculada = LocalTime.now().plusHours(cantHoras);
		return horaCalculada.isAfter(horarioCierre) ? this.horarioCierre : horaCalculada;
	}

	public EstacionamientoPorApp finalizarEstacionamientoPara(String patente) throws Exception{
		Optional <EstacionamientoPorApp> estacionamientoBuscado = this.estacionamientosPorAppDelDia.stream().filter(e -> e.getPatente() == patente).findFirst();
		if (estacionamientoBuscado.isPresent()) {
			EstacionamientoPorApp estActual = estacionamientoBuscado.get();
			estActual.finalizar();
			this.estacionamientosPorAppDelDia.remove(estActual);
			this.estacionamientosHistoricos.add(estActual);
			return estActual;
		} else {
			throw new Exception("Usted no tiene ningún estacionamiento activo.");
		}
		
	}
}
