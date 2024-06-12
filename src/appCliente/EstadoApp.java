package appCliente;

public abstract class EstadoApp {
    protected abstract void finalizarEstacionamiento(AppCliente appCliente);
    protected abstract void iniciarEstacionamiento(AppCliente appCliente, String patente);
    protected abstract boolean tieneEstacionamiento();
}
