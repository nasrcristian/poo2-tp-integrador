package sistema.sistemaDeMonitoreo;

import sistema.estacionamiento.Estacionamiento;
import sistema.registros.RegistroRecarga;

public interface ISistemaObservable {
    public void suscribirEntidad(IEntidad unaEntidad);
    public void desuscribirEntidad (IEntidad unaEntidad);
    public void notificarInicioDeEstacionamientoDe(Estacionamiento estacionamiento);
    public void notificarFinDeEstacionamientoDe(Estacionamiento estacionamiento);
    public void notificarRecargaDeCreditoDe(RegistroRecarga recarga);
}
