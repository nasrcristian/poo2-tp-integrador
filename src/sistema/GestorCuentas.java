package sistema;

import java.util.*;

import appCliente.AppCliente;
import registroCompras.RegistroRecarga;

public class GestorCuentas {
    private Map<Integer, Cuenta> cuentas;	
    
    // Cuenta es un Wrapper que asocia AppCliente con un saldo.

    public GestorCuentas(){
        this.cuentas = new HashMap<Integer, Cuenta>();
    }

    protected void crearCuenta(AppCliente app){
        cuentas.put(app.getNumero(), new Cuenta(app));
    }

    protected String getPatenteSiPuede(int nroCelular) throws Exception {
        Optional <Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        if (cuentaBuscada.isPresent()) {
        	return cuentaBuscada.get().getPatente();
        } else {
        	throw new Exception("El numero ingresado no tiene ninguna patente registrada.");
        }
    }

    protected Optional<Cuenta> getCuenta(Integer nroCelular) {
    	return Optional.of(this.cuentas.get(nroCelular));
    }

    protected float getSaldo(int nroCelular){
        Optional<Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        return cuentaBuscada.isPresent() ? cuentaBuscada.get().getSaldo() : 0;
    }


    protected void descontarCredito(int nroCelular, float monto) throws Exception {
        Optional<Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        if (cuentaBuscada.isPresent()){
            cuentaBuscada.get().descontarCredito(monto);
        } else {
        	throw new Exception("El numero ingresado no tiene ninguna patente registrada.");
        }
    }

	public void cargarCreditoSiPuede(RegistroRecarga ordenDeRecarga) throws Exception {
		Optional <Cuenta> cuentaBuscada = this.getCuenta(ordenDeRecarga.getCelular());
		if (cuentaBuscada.isPresent()) {
			this.cargarCreditoDeOrden(ordenDeRecarga, cuentaBuscada.get());
		} else {
			throw new Exception("La orden de recarga es invalida ya que no hay cuenta asociada a ese numero");
		}
		
	}

	private void cargarCreditoDeOrden(RegistroRecarga ordenDeRecarga, Cuenta cuenta) {
		cuenta.cargarCredito(ordenDeRecarga.getMonto());
	}
}