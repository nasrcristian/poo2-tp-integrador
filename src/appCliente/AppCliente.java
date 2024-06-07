package appCliente;

import sistema.SistemaCentral;

public class AppCliente {
	private String patente;
	private String nroCelular;
	private ModoDeApp modo;
	private SistemaCentral sistema;
	
	public AppCliente(String patente, String nro, SistemaCentral sistema) {
		this.patente = patente;
		this.nroCelular = nro;
		this.sistema = sistema;
		this.setModo(new ModoManual());
	}
	
	private void setModo(ModoDeApp m) {
		this.modo = m;
	}
	
	public void iniciarEstacionamiento() {
		this.sistema.iniciarEstacionamientoPara(this.patente, this.nroCelular);
	}
	
	public void finalizarEstacionamiento() {
		this.sistema.finalizarEstacionamientoDe(this.nroCelular);
	}
	
	public float consultaSaldo() {
		return this.sistema.consultarSaldoDe(this.nroCelular);
	}
	
	
}

