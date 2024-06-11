package sistema.sistemaObservable;

import registroCompras.RegistroRecarga;
import sistema.entidadObservadora.Entidad;
import sistema.estacionamiento.Estacionamiento;

public interface SistemaObservable {
    public void suscribirEntidad(Entidad unaEntidad);
    public void desuscribirEntidad (Entidad unaEntidad);
    public void notificarInicioDeEstacionamientoDe(Estacionamiento estacionamiento);
    public void notificarFinDeEstacionamientoDe(Estacionamiento estacionamiento);
    public void notificarRecargaDeCreditoDe(RegistroRecarga recarga);
}
