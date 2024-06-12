package sistema;

import java.util.*;

import registroCompras.RegistroRecarga;

public class GestorCuentas {
    private Set<Cuenta> cuentas;

    public GestorCuentas(){
        this.cuentas = new HashSet<Cuenta>();
    }

    protected void crearCuenta(int numCelular, String patente){
        cuentas.add(new Cuenta(numCelular, patente));
    }

    protected String getPatenteSiPuede(int nroCelular) throws Exception {
        Optional <Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        if (cuentaBuscada.isPresent()) {
        	return cuentaBuscada.get().getPatente();
        } else {
        	throw new Exception("El numero ingresado no tiene ninguna patente registrada.");
        }
    }

    protected Optional<Cuenta> getCuenta(int nroCelular) {
        return this.cuentas.stream().filter(cuenta -> cuenta.getNroCelular() == nroCelular).findFirst();
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