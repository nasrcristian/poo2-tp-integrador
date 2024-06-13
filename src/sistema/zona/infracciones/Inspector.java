package sistema.zona.infracciones;

import sistema.SistemaCentral;
import sistema.zona.ZonaDeEstacionamiento;


public class Inspector {
    private SistemaCentral sistema;
    private ZonaDeEstacionamiento zonaACargo;

    public Inspector(SistemaCentral sistema, ZonaDeEstacionamiento zona){
        this.sistema = sistema;
        this.zonaACargo = zona;
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