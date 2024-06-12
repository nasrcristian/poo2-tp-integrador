package appCliente;

public abstract class EstadoApp {
    public abstract void finalizarEstacionamiento(AppCliente appCliente);
    protected abstract void iniciarEstacionamiento(AppCliente appCliente, String patente);
    public abstract boolean tieneEstacionamiento();
}
