package sistema;

public class Cuenta {

	private int nroCelular;
	private float saldo;
	private String patente;
	
	public Cuenta(int unNumero, String unaPatente) {
		this.nroCelular = unNumero;
		this.saldo = 0;
		this.patente = unaPatente;
	}
	
	public int getNroCelular() {
		return this.nroCelular;
	}

	public float getSaldo() {
		return this.saldo;
	}

	public String getPatente() {
		return this.patente;
	}

	public void cargarCredito(float monto) {
		this.saldo += monto;
	}

	//TODO: dejar negativo, salvar hasta 0 o no permitir la transaccion?
	public void descontarCredito(float monto){
		this.saldo -= monto;
	}
}
