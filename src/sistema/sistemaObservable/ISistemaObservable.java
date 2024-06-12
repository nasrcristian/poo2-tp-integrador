package sistema.sistemaObservable;

import registroCompras.RegistroRecarga;
import sistema.entidadObservadora.IEntidad;
import sistema.estacionamiento.Estacionamiento;

public interface ISistemaObservable {
    public void suscribirEntidad(IEntidad unaEntidad);
    public void desuscribirEntidad (IEntidad unaEntidad);
    public void notificarInicioDeEstacionamientoDe(Estacionamiento estacionamiento);
    public void notificarFinDeEstacionamientoDe(Estacionamiento estacionamiento);
    public void notificarRecargaDeCreditoDe(RegistroRecarga recarga);
}
