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
        if(appCliente.haySaldoSuficiente()){
            appCliente.getSistema().iniciarEstacionamientoPorApp(appCliente.getNumero());
            appCliente.setEstado(new ConEstacionamiento());
        }else{
            appCliente.notificar("Saldo insuficiente. Estacionamiento no permitido.");
        }
    }

    @Override
    protected boolean tieneEstacionamiento() {
    	return false;
    }
}
