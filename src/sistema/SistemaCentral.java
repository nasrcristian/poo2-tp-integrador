package sistema;

import java.util.List;
import java.util.Optional;

import appCliente.AppCliente;

import java.util.ArrayList;

import inspector.Inspector;

public class SistemaCentral {
	
	private GestorEstacionamiento estacionamientos;
	private GestorInfracciones infracciones;
	private List<Cuenta> cuentas;
	
	public SistemaCentral(GestorEstacionamiento gestorEst, GestorInfracciones gestorInf) {
		this.estacionamientos = gestorEst;
		this.infracciones = gestorInf;
		this.cuentas = new ArrayList<Cuenta>();
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

}
