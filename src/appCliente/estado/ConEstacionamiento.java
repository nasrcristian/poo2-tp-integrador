package appCliente.estado;

import appCliente.AppCliente;

public class ConEstacionamiento implements EstadoApp {

    @Override
	public void iniciarEstacionamiento(AppCliente appCliente) {
        // no hace nada porque ya tiene un estacionamiento vigente
    }

    //Asumimos que tiene una estacionamiento iniciado ya que es la unica forma de que la APP haya pasado al estado ConEstacionamiento
    @Override
	public void finalizarEstacionamiento(AppCliente appCliente) {
        int numeroCliente = appCliente.getNumero();
        appCliente.getSistema().finalizarEstacionamientoSiPuedePara(numeroCliente);
        appCliente.setEstado(new SinEstacionamiento());
    }
}
