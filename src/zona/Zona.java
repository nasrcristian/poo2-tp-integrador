package zona;

import inspector.Inspector;
import puntoVenta.PuntoVenta;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private Inspector inspector;
    private List<PuntoVenta> puntoVentas;

    public Zona(Inspector inspector){
        this.inspector = inspector;
        this.puntoVentas = new ArrayList<PuntoVenta>();
    }

    public void registrar(PuntoVenta puntoVenta){
        puntoVentas.add(puntoVenta);
    }
}
