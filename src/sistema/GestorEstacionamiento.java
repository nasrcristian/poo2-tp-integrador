package sistema;

import sistema.estacionamiento.Estacionamiento;

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

    protected boolean estaVigente(String patente) {
		Optional<Estacionamiento> estacionamientoBuscado = this.estacionamientosActuales.stream().filter(e -> e.getPatente().equals(patente)).findFirst();
		return estacionamientoBuscado.isPresent() ? estacionamientoBuscado.get().estaVigente(this.horarioApertura, this.horarioCierre) : false;
	}

	protected boolean haySaldoSuficiente(float saldo){
		return saldo >= this.costoPorHora;
	}

	//TODO revisar como calcular la hora maxima para colocar el fin solo con la patente y como notificar a la app del inicio de estacionamiento.
	protected void iniciarEstacionamiento(String patente, SistemaCentral sistema){
		Estacionamiento estacionamientoNuevo = new Estacionamiento(patente, LocalTime.now(), null);
		this.estacionamientosActuales.add(estacionamientoNuevo);
		String notificacion = "Estacionamiento disponible desde " + LocalTime.now() + " hrs hasta las " + horaMaxima + "hrs";
		appCliente.notificar(notificacion);
		sistema.notificarInicioDeEstacionamientoDe(estacionamientoNuevo); //tal vez notificar implementando a Entidad la App?
	}

	protected void finalizarEstacionamiento(String patente, SistemaCentral sistema) throws Exception {
		Optional <Estacionamiento> estacionamientoADarFin = this.getEstacionamiento(patente);
		if (estacionamientoADarFin.isPresent()){
			sistema.notificarFinDeEstacionamientoDe(estacionamientoADarFin.get());
			sistema.descontarSaldo(,getCostoEstacionamiento(estacionamientoADarFin.get())); //TODO pensar como descontar saldo de la cuenta con la patente como dato
			String notificacion = "Estacionamiento que fue iniciado " + estacionamientoADarFin.get().getHoraInicio() + " hrs y finalizo " + estacionamientoADarFin.get().getHoraFin() + " hrs. Con una duracion de " + estacionamientoADarFin.get().getDuracion() + " hrs y un costo de " + this.getCostoEstacionamiento(estacionamientoADarFin.get());
			appCliente.notificar(notificacion);
			this.estacionamientosActuales.remove(estacionamientoADarFin.get());
		}else{throw new Exception("No existe un estacionamiento para la patente ingresada.");}
	}

	private Optional<Estacionamiento> getEstacionamiento(String patente){
		return estacionamientosActuales.stream().filter(e -> e.getPatente().equals(patente)).findFirst();
	}

	private float getCostoEstacionamiento(Estacionamiento estacionamiento) throws Exception {
		return estacionamiento.getDuracion() * this.costoPorHora;
	}
}
