package sistema;

public class Cuenta {

	private int nroCelular;
	private float saldo;
	private String patente;
	
	public Cuenta(int unNumero, float unSaldo, String unaPatente) {
		this.nroCelular = unNumero;
		this.saldo = unSaldo;
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
}
