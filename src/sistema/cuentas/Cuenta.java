package sistema.cuentas;

import appCliente.AppCliente;

public class Cuenta {
	private float saldo;
	private AppCliente app;
	
	public Cuenta(AppCliente app) {
		this.saldo = 0;
		this.app = app;
	}
	public int getNroCelular() {
		return this.app.getNumero();
	}

	public AppCliente getApp() {
		return this.app;
	}
	
	public float getSaldo() {
		return this.saldo;
	}

	public String getPatente() {
		return this.app.getPatente();
	}

	public void cargarCredito(float monto) {
		this.saldo += monto;
	}

	//TODO: dejar negativo, salvar hasta 0 o no permitir la transaccion?
	public void descontarCredito(float monto){
		this.saldo -= monto;
	}
}
