package puntoVenta;

import java.time.LocalDate;
import java.time.LocalTime;

import sistema.SistemaCentral;
import registroCompras.RegistroCompra;
import registroCompras.RegistroCompraHoras;
import registroCompras.RegistroRecarga;

public class PuntoVenta {

	private SistemaCentral sistema;
	private int numeroDeControl;
	
	public PuntoVenta(SistemaCentral sistema) {
		this.sistema = sistema;
		this.numeroDeControl = 1;
	}
	
	public void cargarCredito(int numeroDeCelular, Float monto){
    	RegistroRecarga ordenDeRecarga = new RegistroRecarga(this.numeroDeControl, this, LocalDate.now(), LocalTime.now(), numeroDeCelular, monto);
		this.sistema.cargarCreditoDeLaOrdenSiPuede(ordenDeRecarga);
    	this.aumentarNumeroDeControl();
    }
    
    public void aumentarNumeroDeControl() {
    	this.numeroDeControl += 1;
    }

    public void compraPuntual(String patente, int cantHoras){
    	RegistroCompraHoras ordenDeEstacionamientoPuntual = new RegistroCompraHoras(this.numeroDeControl, this, LocalDate.now(), LocalTime.now(), patente, cantHoras);
    	// TODO Terminar implementacion
    }

	public int getNroControl() {
		return this.numeroDeControl;
	}
}
