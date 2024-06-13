package zona;

import puntoVenta.PuntoVenta;
import sistema.SistemaCentral;

import java.util.ArrayList;
import java.util.List;

public class ZonaDeEstacionamiento{
	private SistemaCentral sistema;
    private Inspector inspector;
    private List<PuntoVenta> puntoVentas;

    public ZonaDeEstacionamiento(SistemaCentral sistema, Inspector inspector){
        this.sistema = sistema;
    	this.puntoVentas = new ArrayList<PuntoVenta>();
        this.inspector = inspector;
        this.inspector.asignarZonaDeEstacionamiento(this); // Este sería un constructor en el cual ya hay un inspector existente y se le asigna una nueva zona de estacionamiento.
    }
    
    public ZonaDeEstacionamiento(SistemaCentral sistema){
        this.sistema = sistema;
        this.puntoVentas = new ArrayList<PuntoVenta>();
        this.inspector = new Inspector(sistema, this);
    }
    

	public void registrar(PuntoVenta puntoVenta){
        puntoVentas.add(puntoVenta);
    }
}
