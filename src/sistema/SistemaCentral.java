package sistema;

import java.util.List;

import java.util.Optional;
import java.util.Set;

import appCliente.AppCliente;

import java.util.ArrayList;

import inspector.Inspector;
import registroCompras.RegistroCompraPuntual;
import registroCompras.RegistroRecarga;
import sistema.entidadObservadora.IEntidad;
import sistema.estacionamiento.Estacionamiento;
import sistema.estacionamiento.EstacionamientoPorApp;
import sistema.estacionamiento.GestorEstacionamiento;
import sistema.sistemaObservable.ISistemaObservable;
import zona.ZonaDeEstacionamientoMedido;

public class SistemaCentral implements ISistemaObservable {
	
	private GestorEstacionamiento estacionamientos;
	private GestorInfracciones infracciones;
	private GestorRegistros registros;
	private GestorCuentas cuentas;
	private Set<ZonaDeEstacionamientoMedido> zonas;
	private List <IEntidad> entidades;
	
	public SistemaCentral(GestorEstacionamiento gestorEst, GestorInfracciones gestorInf, GestorRegistros registros, GestorCuentas cuentas, Set<ZonaDeEstacionamientoMedido> zonas) {
		this.estacionamientos = gestorEst;
		this.infracciones = gestorInf;
		this.registros = registros;
		this.cuentas = cuentas;
		this.entidades = new ArrayList<IEntidad>();
		this.zonas = zonas;
	}

	//mensajes cuenta
	public void crearCuenta(AppCliente app) {
		this.cuentas.crearCuenta(app);
	}
	
	public float consultarSaldoDe(int nroCelular) {
		return this.cuentas.getSaldo(nroCelular);
	}
	
	//metodos infraccion
	public void cargarCreditoDeLaOrdenSiPuede(RegistroRecarga ordenDeRecarga){
		try {
			this.cuentas.cargarCreditoSiPuede(ordenDeRecarga);
			this.registros.agregarRegistro(ordenDeRecarga);			 // Esto solo se ejecuta si no hay una excepción.
			this.notificarRecargaDeCreditoDe(ordenDeRecarga);// Esto solo se ejecuta si no hay una excepción.
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	//mensajes infraccion
	public void generarInfraccion(String patente, Inspector inspector) {
		this.infracciones.generarInfraccion(patente, inspector);
	}

	//mensajes estacionamiento
	
	public void iniciarEstacionamientoPuntual(RegistroCompraPuntual ordenDeEstacionamientoPuntual) {
		Estacionamiento ticketEstacionamiento = this.estacionamientos.iniciarEstacionamientoPuntualConOrden(ordenDeEstacionamientoPuntual);
		this.registros.agregarRegistro(ordenDeEstacionamientoPuntual);
		this.notificarInicioDeEstacionamientoDe(ticketEstacionamiento);
	}
	
	
	public boolean tieneEstacionamientoVigente(int nroCelular) { // Permite que la verificacion sea sin necesidad de ingresar patente.
		try {
			return this.tieneEstacionamientoVigente(this.cuentas.getPatenteSiPuede(nroCelular)); // getPatente puede tirar una excepción ya que la cuenta puede no estar asociada.
		} catch (Exception e) {
			return false;
		}
	}

	public boolean tieneEstacionamientoVigente(String patente) {
		return this.estacionamientos.estaVigente(patente);
	}

	public boolean haySaldoSuficiente(AppCliente appCliente){
		float saldoCliente = consultarSaldoDe(appCliente.getNumero());
		return this.estacionamientos.haySaldoSuficiente(saldoCliente);
	}
	
	
	public void iniciarEstacionamientoSiPuedePara(int numeroDeTelefono) {
		Optional<Cuenta> cuentaBuscada = this.cuentas.getCuenta(numeroDeTelefono);
		if (cuentaBuscada.isPresent()) {
			Cuenta cuenta = cuentaBuscada.get();
			iniciarEstacionamientoPara(cuenta);
		}
	}

	private void iniciarEstacionamientoPara(Cuenta cuenta) {
		AppCliente app = cuenta.getApp();
		try {
			EstacionamientoPorApp ticketDeEstacionamiento = this.estacionamientos.iniciarEstacionamientoPara(cuenta);
			this.notificarInicioDeEstacionamientoDe(ticketDeEstacionamiento);
			app.notificar("Se ha iniciado correctamente un estacionamiento a las " + ticketDeEstacionamiento.getHoraInicio() + ". El horario máximo de finalización del mismo es a las " + ticketDeEstacionamiento.getHoraFin() + ".");
		} catch (Exception e) {
			app.notificar(e.getMessage());
		}
	}


	public void finalizarEstacionamientoSiPuedePara(int numeroCelular){
		Optional<Cuenta> cuentaBuscada = this.cuentas.getCuenta(numeroCelular);
		if (cuentaBuscada.isPresent()) {
			Cuenta cuenta = cuentaBuscada.get();
			finalizarEstacionamientoPara(cuenta);
		}
	}

	private void finalizarEstacionamientoPara(Cuenta cuenta) {
		AppCliente app = cuenta.getApp();
		try {
			EstacionamientoPorApp ticketDeEstacionamiento = this.estacionamientos.finalizarEstacionamientoPara(cuenta.getPatente());
			this.cuentas.descontarCredito(cuenta.getNroCelular(), ticketDeEstacionamiento.getCostoTotal());
			this.notificarFinDeEstacionamientoDe(ticketDeEstacionamiento);
			app.notificar("El estacionamiento comenzado a las " + ticketDeEstacionamiento.getHoraInicio() + ", finalizó correctamente a las " + ticketDeEstacionamiento.getHoraFin() + ". Su duración total fue de " + ticketDeEstacionamiento.getDuracion() + "y tuvo un costo total de: " + ticketDeEstacionamiento.getCostoTotal() + " pesos.");
		} catch (Exception e){
			app.notificar(e.getMessage());
		}
	}
	
	/* ################ SISTEMA DE MONITOREO ################ */
	@Override
	public void suscribirEntidad(IEntidad unaEntidad) {
		this.entidades.add(unaEntidad);
	}
	
	@Override
	public void desuscribirEntidad (IEntidad unaEntidad) {
		this.entidades.remove(unaEntidad);
	}
	
	@Override
	public void notificarInicioDeEstacionamientoDe(Estacionamiento estacionamiento) {
		this.entidades.stream().forEach(e -> e.actualizarInicioDeEstacionamientoDe(estacionamiento));
	}
	
	@Override
	public void notificarFinDeEstacionamientoDe(Estacionamiento estacionamiento) {
		this.entidades.stream().forEach(e -> e.actualizarFinalizacionDeEstacionamientoDe(estacionamiento));
	}
	
	@Override
	public void notificarRecargaDeCreditoDe(RegistroRecarga recarga) {
		this.entidades.stream().forEach(e -> e.actualizarConRecargaDeCredito(recarga));
	}
}
