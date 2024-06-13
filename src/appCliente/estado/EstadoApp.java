package appCliente.estado;

import appCliente.AppCliente;

public interface EstadoApp {
    public void finalizarEstacionamiento(AppCliente appCliente);
    public void iniciarEstacionamiento(AppCliente appCliente);
}
