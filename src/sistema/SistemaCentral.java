package sistema;

import java.util.List;

import java.util.Optional;
import java.util.Set;

import appCliente.AppCliente;

import java.util.ArrayList;

import inspector.Inspector;
import registroCompras.RegistroCompra;
import registroCompras.RegistroRecarga;
import sistema.entidadObservadora.Entidad;
import sistema.estacionamiento.Estacionamiento;
import sistema.sistemaObservable.SistemaObservable;
import zona.ZonaDeEstacionamientoMedido;

public class SistemaCentral implements SistemaObservable {
	
	private GestorEstacionamiento estacionamientos;
	private GestorInfracciones infracciones;
	private GestorRegistros registros;
	private GestorCuentas cuentas;
	private Set<ZonaDeEstacionamientoMedido> zonas;
	private List <Entidad> entidades;
	
	public SistemaCentral(GestorEstacionamiento gestorEst, GestorInfracciones gestorInf, GestorRegistros registros, GestorCuentas cuentas, Set<ZonaDeEstacionamientoMedido> zonas) {
		this.estacionamientos = gestorEst;
		this.infracciones = gestorInf;
		this.registros = registros;
		this.cuentas = cuentas;
		this.entidades = new ArrayList<Entidad>();
		this.zonas = zonas;
	}

	//metodos cuenta
	public void crearCuenta(int nroCelular, String patente) {
		this.cuentas.agregarCuenta(nroCelular, patente);
	}
	
	public float consultarSaldoDe(int nroCelular) {
		return this.cuentas.getSaldo(nroCelular);
	}

	public void cargarCreditoDeLaOrdenSiPuede(RegistroRecarga ordenDeRecarga) throws Exception{
		Optional <Cuenta> cuentaBuscada = this.cuentas.getCuenta(ordenDeRecarga.getCelular());
		if (cuentaBuscada.isPresent()) {
			this.cargarCreditoDeOrden(ordenDeRecarga, cuentaBuscada.get());
		} else {
			throw new Exception("La orden de recarga es invalida ya que no hay cuenta asociada a ese numero");
		}
	}

	private void cargarCreditoDeOrden(RegistroRecarga ordenDeRecarga, Cuenta cuenta) {
		cuenta.cargarCredito(ordenDeRecarga.getMonto());
		this.registrarCompra(ordenDeRecarga);
		this.notificarRecargaDeCreditoDe(ordenDeRecarga);
	}

	//NOTE: metodo necesario? en publico?
	public void registrarCompra(RegistroCompra registroDeCompra) {
		this.registros.agregarRegistro(registroDeCompra);
	}

	//metodos infraccion
	public void generarInfraccion(String patente, Inspector inspector) {
		this.infracciones.generarInfraccion(patente, inspector);
	}

	//metodos estacionamiento
	public boolean tieneEstacionamientoVigente(int nroCelular) {
		try {
			return this.tieneEstacionamientoVigente(this.cuentas.getPatente(nroCelular));
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

	public void iniciarEstacionamientoPara(String patente) {
		// TODO Auto-generated method stub
		this.estacionamientos.iniciarEstacionamiento(patente);
	}

	public void finalizarEstacionamientoPara(int numeroCelular) {
		// TODO Auto-generated method stub
		
	}

	public void generarEstacionamientoPuntual(RegistroCompra registroPuntual){

	}
	
	/* ### SISTEMA DE MONITOREO ### */
	@Override
	public void suscribirEntidad(Entidad unaEntidad) {
		this.entidades.add(unaEntidad);
	}

	@Override
	public void desuscribirEntidad (Entidad unaEntidad) {
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
