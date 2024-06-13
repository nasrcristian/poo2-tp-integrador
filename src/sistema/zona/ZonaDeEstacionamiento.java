package sistema.zona;

import sistema.SistemaCentral;
import sistema.zona.infracciones.Inspector;

import java.util.ArrayList;
import java.util.List;

public class ZonaDeEstacionamiento{
	private Inspector inspector;
    private List<PuntoVenta> puntoVentas;

    public ZonaDeEstacionamiento(Inspector inspector){
        this.puntoVentas = new ArrayList<PuntoVenta>();
        this.inspector = inspector;
        this.inspector.asignarZonaDeEstacionamiento(this); // Este sería un constructor en el cual ya hay un inspector existente y se le asigna una nueva zona de estacionamiento.
    }
    
    public ZonaDeEstacionamiento(SistemaCentral sistema){
        this.puntoVentas = new ArrayList<PuntoVenta>();
        this.inspector = new Inspector(sistema, this);
    }
    

	public void registrar(PuntoVenta puntoVenta){
        puntoVentas.add(puntoVenta);
    }
	
	public Inspector getInspector() {
		return this.inspector;
	}
	
	public List<PuntoVenta> getPuntosDeVenta(){
		return puntoVentas;
	}
}
