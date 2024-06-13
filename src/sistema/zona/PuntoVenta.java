package sistema.zona;

import java.time.LocalDate;
import java.time.LocalTime;

import sistema.SistemaCentral;
import sistema.registros.RegistroCompra;
import sistema.registros.RegistroCompraPuntual;
import sistema.registros.RegistroRecarga;

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
    	RegistroCompraPuntual ordenDeEstacionamientoPuntual = new RegistroCompraPuntual(this.numeroDeControl, this, LocalDate.now(), LocalTime.now(), patente, cantHoras);
    	this.sistema.iniciarEstacionamientoPuntual(ordenDeEstacionamientoPuntual);
    	this.aumentarNumeroDeControl();
    }

	public int getNroControl() {
		return this.numeroDeControl;
	}
}
