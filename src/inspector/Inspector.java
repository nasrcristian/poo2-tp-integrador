package inspector;

import sistema.SistemaCentral;
import zona.SinZonaDeEstacionamiento;
import zona.ZonaDeEstacionamiento;
import java.time.LocalDateTime;

public class Inspector {
    private SistemaCentral sistema;
    private ZonaDeEstacionamiento zonaACargo;

    public Inspector(SistemaCentral sistema){
        this.sistema = sistema;
        this.zonaACargo = new SinZonaDeEstacionamiento(); 
        // Ya que hay una dependencia circular entre la zona de estacionamiento y el inspector, se tomó la decisión de modelar una clase que funcione como una zona de estacionamiento "nula"
        // Por lo tanto el inspector se inicializaria sin una zona de estacionamiento asignada y luego se le daria la misma al momento de crearse la zona de estacionamiento.
    }

    public ZonaDeEstacionamiento getZona(){
        return this.zonaACargo;
    }
    
    public void asignarZonaDeEstacionamiento(ZonaDeEstacionamiento zona) {
    	this.zonaACargo = zona;
    }

    public boolean estaVigente(String patente){
        return this.sistema.tieneEstacionamientoVigente(patente);
    }

    public void altaInfraccion(String patente){
    	if (!estaVigente(patente)) {
        this.sistema.generarInfraccion(patente, this);
    	}
    }
}