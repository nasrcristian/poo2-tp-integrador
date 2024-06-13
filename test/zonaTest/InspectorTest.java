package zonaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import puntoVenta.PuntoVenta;
import sistema.SistemaCentral;
import zona.Inspector;
import zona.ZonaDeEstacionamiento;

public class InspectorTest {

   private ZonaDeEstacionamiento zonaMock;
   private SistemaCentral sistemaMock;
   private Inspector inspector;

    @BeforeEach
    public void setUp() {
    	sistemaMock = mock(SistemaCentral.class);
    	zonaMock = mock(ZonaDeEstacionamiento.class);
    	inspector = new Inspector(sistemaMock, zonaMock);
        
    }
    
    @Test 
    public void testGetZona() {
    	assertEquals(zonaMock, inspector.getZona());
    }
    
    @Test
    public void testUnInspectorSeLePuedeAsignarOtraZona() {
    	ZonaDeEstacionamiento zona2 = mock(ZonaDeEstacionamiento.class);
    	inspector.asignarZonaDeEstacionamiento(zona2);
    	assertEquals(zona2, inspector.getZona());
    }

    @Test 
    public void testUnInspectorPuedeVerificarSiEstaVigente() {
    	String patente = "AAA000";
    	inspector.estaVigente(patente);
    	verify(sistemaMock, times(1)).tieneEstacionamientoVigente(patente);
    }
    
    @Test
    public void testUnInspectorPuedeGenerarUnaInfraccionSiNoEstaVigente() {
    	String patente = "AAA000";
    	when(sistemaMock.tieneEstacionamientoVigente(patente)).thenReturn(false);
    	inspector.altaInfraccion(patente);
    	verify(sistemaMock, times(1)).generarInfraccion(patente, inspector);
    }
    
    @Test
    public void testUnInspectorNoPuedeGenerarUnaInfraccionSiEstaVigente() {
    	String patente = "AAA000";
    	when(sistemaMock.tieneEstacionamientoVigente(patente)).thenReturn(true);
    	inspector.altaInfraccion(patente);
    	verify(sistemaMock, times(0)).generarInfraccion(patente, inspector);
    }
    
    
    
}