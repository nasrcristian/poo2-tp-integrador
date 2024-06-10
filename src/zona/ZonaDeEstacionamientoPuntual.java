package zona;

import inspector.Inspector;
import puntoVenta.PuntoVenta;

import java.util.ArrayList;
import java.util.List;

public class ZonaDeEstacionamientoPuntual implements ZonaDeEstacionamiento {
    private Inspector inspector;
    private List<PuntoVenta> puntoVentas;

    public ZonaDeEstacionamientoPuntual(Inspector inspector){
        this.puntoVentas = new ArrayList<PuntoVenta>();
        this.inspector = inspector;
        this.inspector.asignarZonaDeEstacionamiento(this);
    }

    public void registrar(PuntoVenta puntoVenta){
        puntoVentas.add(puntoVenta);
    }
}
