package sistema;

import java.util.*;

public class GestorCuentas {
    private Set<Cuenta> cuentas;

    public GestorCuentas(){
        this.cuentas = new HashSet<Cuenta>();
    }

    protected void agregarCuenta(int numCelular, String patente){
        cuentas.add(new Cuenta(numCelular, patente));
    }

    protected String getPatente(int nroCelular) throws Exception {
        Optional <Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        return cuentaBuscada.map(Cuenta::getPatente).orElseThrow(() -> new Exception("El numero ingresado no tiene ninguna patente registrada."));
    }

    protected Optional<Cuenta> getCuenta(int nroCelular) {
        return this.cuentas.stream().filter(c -> c.getNroCelular() == nroCelular).findFirst();
    }

    protected float getSaldo(int nroCelular){
        Optional<Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        return cuentaBuscada.map(Cuenta::getSaldo).orElse(0F);
    }

    protected void descontarCredito(int nroCelular, float monto) throws Exception {
        Optional<Cuenta> cuentaBuscada = this.getCuenta(nroCelular);
        if (cuentaBuscada.isPresent()){
            cuentaBuscada.get().descontarCredito(monto);
        }else{throw new Exception("El numero ingresado no tiene ninguna patente registrada.");}
    }
}