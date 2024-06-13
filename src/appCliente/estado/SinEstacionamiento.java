package appCliente.estado;

import appCliente.AppCliente;

public class SinEstacionamiento implements EstadoApp {

    @Override
	public void finalizarEstacionamiento(AppCliente appCliente) {
        //// no hace nada porque aun no tiene un estacionamiento vigente
    }

    @Override
	public void iniciarEstacionamiento(AppCliente appCliente) {
      if (appCliente.getSistema().iniciarEstacionamientoSiPuedePara(appCliente.getNumero())) {
        	appCliente.setEstado(new ConEstacionamiento());
        } // En caso de que no tenga saldo no se va a iniciar el estacionamiento.
    }
}
