package inspector;


import sistema.GestorInfracciones;
import zona.Zona;

import java.time.LocalDateTime;

public class Inspector {
    private GestorInfracciones sistema;
    private Zona zonaACargo;

    public Inspector(GestorInfracciones sistema, Zona zonaACargo){
        this.sistema = sistema;
        this.zonaACargo = zonaACargo;
    }

    public Zona getZona(){
        return this.zonaACargo;
    }

    public boolean estaVigente(String patente, LocalDateTime horaConsulta){
        return (this.sistema.tieneEstacionamientoVigente(patente, horaConsulta));
    }

    public void altaInfraccion(String patente){
        this.sistema.generarInfraccion(patente, this);
    }
}