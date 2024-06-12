package appCliente;

public class ConEstacionamiento extends EstadoApp {

    public ConEstacionamiento(){

    }

    @Override
    protected void iniciarEstacionamiento(AppCliente appCliente, String patente) {
        // no hace nada porque ya tiene un estacionamiento vigente
    }

    //Asumimos que tiene una estacionamiento iniciado ya que es la unica forma de que la APP haya pasado al estado ConEstacionamiento
    @Override
    protected void finalizarEstacionamiento(AppCliente appCliente) {
        int numeroCliente = appCliente.getNumero();
        appCliente.getSistema().finalizarEstacionamientoPara(numeroCliente);
        appCliente.setEstado(new SinEstacionamiento());
    }

    @Override
    protected boolean tieneEstacionamiento() {
        return true;
    }
}
