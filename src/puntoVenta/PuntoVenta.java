package puntoVenta;

import java.time.LocalDate;
import java.time.LocalTime;

import sistema.SistemaCentral;
import registroCompras.RegistroCompra;
import registroCompras.RegistroRecarga;

public class PuntoVenta {

	private SistemaCentral sistema;
	private int numeroDeControl;
	
	public PuntoVenta(SistemaCentral sistema) {
		this.sistema = sistema;
		this.numeroDeControl = 1;
	}
	
	
    /* public void cargarCredito(int numeroDeCelular, Float monto){
    	try {
			this.sistema.cargarCreditoDelNumeroDesde(numeroDeCelular, monto, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.aumentarNumeroDeControl();
    } */
	
	public void cargarCredito(int numeroDeCelular, Float monto){
    	RegistroRecarga ordenDeRecarga = new RegistroRecarga(this.numeroDeControl, this, LocalDate.now(), LocalTime.now(), numeroDeCelular, monto);
		try {
			this.sistema.cargarCreditoDeLaOrdenSiPuede(ordenDeRecarga);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.aumentarNumeroDeControl();
    }
    
    public void aumentarNumeroDeControl() {
    	this.numeroDeControl += 1;
    }

    public void compraPuntual(String patente, int cantHoras){
    	
    }


	public int getNroControl() {
		return this.numeroDeControl;
	}
}
