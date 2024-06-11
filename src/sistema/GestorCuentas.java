package sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorCuentas {
    private List<Cuenta> cuentas;

    public GestorCuentas(){
        this.cuentas = new ArrayList<Cuenta>();
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
}
