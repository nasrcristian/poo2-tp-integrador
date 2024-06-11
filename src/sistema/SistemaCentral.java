package sistema;

import java.util.List;

import java.util.Optional;
import java.util.Set;

import appCliente.AppCliente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;

import inspector.Inspector;
import puntoVenta.PuntoVenta;
import registroCompras.RegistroCompra;
import registroCompras.RegistroRecarga;
import sistema.entidadObservadora.Entidad;
import sistema.estacionamiento.Estacionamiento;
import sistema.estacionamiento.GestorEstacionamiento;
import zona.ZonaDeEstacionamientoMedido;

public class SistemaCentral {
	
	private GestorEstacionamiento estacionamientos;
	private GestorInfracciones infracciones;
	private GestorRegistros registros;
	private Set<ZonaDeEstacionamientoMedido> zonas;
	private Set<Cuenta> cuentas;
	private List <Entidad> entidades;
	
	public SistemaCentral(GestorEstacionamiento gestorEst, GestorInfracciones gestorInf, GestorRegistros registros, Set<ZonaDeEstacionamientoMedido> zonas) {
		this.estacionamientos = gestorEst;
		this.infracciones = gestorInf;
		this.registros = registros;
		this.cuentas = new HashSet<Cuenta>();
		this.entidades = new ArrayList<Entidad>();
		this.zonas = zonas;
	}
	

	public void crearCuenta(int nroCelular, String patente) {
		this.cuentas.add(new Cuenta(nroCelular, patente));	
	}
	
	public float consultarSaldoDe(int nroCelular) {
		Optional<Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
		return cuentaBuscada.isPresent() ? cuentaBuscada.get().getSaldo() : 0;
	}

	public boolean tieneEstacionamientoVigente(int nroCelular) {
		try {
			return this.tieneEstacionamientoVigente(this.getPatente(nroCelular));
		} catch (Exception e) {
			return false;
		}
	}

	private String getPatente(int nroCelular) throws Exception {
		Optional <Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
		if (cuentaBuscada.isPresent()) {
			return cuentaBuscada.get().getPatente();
		} else {
			throw new Exception("El numero ingresado no tiene ninguna patente registrada."); 
		}
	}
		
	
	private Optional<Cuenta> getCuenta(int nroCelular) {
		return this.cuentas.stream().filter(c -> c.getNroCelular() == nroCelular).findFirst();
	}




	public boolean tieneEstacionamientoVigente(String patente) {
		return this.estacionamientos.estaVigente(patente);
	}


	public void generarInfraccion(String patente, Inspector inspector) {
		this.infracciones.generarInfraccion(patente, inspector);
	}


	public void iniciarEstacionamientoPara(AppCliente appCliente) {
		// TODO Auto-generated method stub
		
	}

	public void finalizarEstacionamientoPara(AppCliente appCliente) {
		// TODO Auto-generated method stub
		
	}

	
	/* ### SISTEMA DE MONITOREO ### */
	public void suscribirEntidad(Entidad unaEntidad) {
		this.entidades.add(unaEntidad);
	}
	
	public void desuscribirEntidad (Entidad unaEntidad) {
		this.entidades.remove(unaEntidad);
	}
	
	public void notificarInicioDeEstacionamientoDe(Estacionamiento estacionamiento) {
		this.entidades.stream().forEach(e -> e.actualizarInicioDeEstacionamientoDe(estacionamiento));
	}
	
	public void notificarFinDeEstacionamientoDe(Estacionamiento estacionamiento) {
		this.entidades.stream().forEach(e -> e.actualizarFinalizacionDeEstacionamientoDe(estacionamiento));
	}
	
	public void notificarRecargaDeCreditoDe(RegistroRecarga recarga) {
		this.entidades.stream().forEach(e -> e.actualizarConRecargaDeCredito(recarga));
	}



	/* public void cargarCreditoDelNumeroDesde(int numero, Float monto, PuntoVenta puntoVenta) throws Exception{
		Optional <Cuenta> cuentaBuscada = this.getCuenta(numero);
		if (cuentaBuscada.isPresent()) {
			cuentaBuscada.get().cargarCredito(monto);
			RegistroCompra registro = new RegistroRecarga(puntoVenta.getNroControl(), puntoVenta, LocalDate.now(), LocalTime.now(), numero, monto);
		} else {
			throw new Exception("No hay una cuenta asociada a ese numero");
		}
	} */

	public void cargarCreditoDeLaOrdenSiPuede(RegistroRecarga ordenDeRecarga) throws Exception{
		Optional <Cuenta> cuentaBuscada = this.getCuenta(ordenDeRecarga.getCelular());
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


	public void registrarCompra(RegistroCompra registroDeCompra) {
		this.registros.agregarRegistro(registroDeCompra);
		
	}



}
