package sistema;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import appCliente.AppCliente;

import java.util.ArrayList;

import inspector.Inspector;

public class SistemaCentral {

	private GestorEstacionamiento estacionamientos;
	private GestorInfracciones infracciones;
	private GestorCuentas cuentas;
	
	public SistemaCentral(GestorEstacionamiento gestorEst, GestorInfracciones gestorInf, GestorCuentas gestorCuen) {
		this.estacionamientos = gestorEst;
		this.infracciones = gestorInf;
		this.cuentas = gestorCuen;
	}

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

	public void generarInfraccion(String patente, Inspector inspector) {
		this.infracciones.generarInfraccion(patente, inspector);
	}

	public float consultarSaldoDe(int nroCelular) {
		return this.cuentas.getSaldo(nroCelular);
	}

	public void iniciarEstacionamientoPara(AppCliente appCliente) {
		// TODO Auto-generated method stub
		
	}

	public void finalizarEstacionamientoPara(AppCliente appCliente) {
		// TODO Auto-generated method stub
		
	}

}
