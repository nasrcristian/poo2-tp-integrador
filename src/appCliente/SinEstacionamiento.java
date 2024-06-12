package appCliente;

public class SinEstacionamiento extends EstadoApp {

    public SinEstacionamiento(){

    }

    @Override
    protected void finalizarEstacionamiento(AppCliente appCliente) {
        //// no hace nada porque aun no tiene un estacionamiento vigente
    }

    @Override
    protected void iniciarEstacionamiento(AppCliente appCliente) {
        appCliente.getSistema().iniciarEstacionamientoSiPuedePara(appCliente.getNumero());
        appCliente.setEstado(new ConEstacionamiento());
    }

    @Override
    protected boolean tieneEstacionamiento() {
    	return false;
    }
}
