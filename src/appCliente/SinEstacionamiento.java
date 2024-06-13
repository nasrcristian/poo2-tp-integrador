package appCliente;

public class SinEstacionamiento extends EstadoApp {

    @Override
    protected void finalizarEstacionamiento(AppCliente appCliente) {
        //// no hace nada porque aun no tiene un estacionamiento vigente
    }

    @Override
    protected void iniciarEstacionamiento(AppCliente appCliente) {
      if (appCliente.getSistema().iniciarEstacionamientoSiPuedePara(appCliente.getNumero())) {
        	appCliente.setEstado(new ConEstacionamiento());
        } // En caso de que no tenga saldo no se va a iniciar el estacionamiento.
    }
}
