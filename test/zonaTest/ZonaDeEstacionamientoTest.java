package zonaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.SistemaCentral;
import sistema.zona.PuntoVenta;
import sistema.zona.ZonaDeEstacionamiento;
import sistema.zona.infracciones.Inspector;

public class ZonaDeEstacionamientoTest {

   private ZonaDeEstacionamiento zona;
   private SistemaCentral sistemaMock;
   private Inspector inspectorMock;

    @BeforeEach
    public void setUp() {
    	sistemaMock = mock(SistemaCentral.class);
    	inspectorMock = mock(Inspector.class);
        zona = new ZonaDeEstacionamiento(inspectorMock);
        
    }
    
    @Test 
    public void testGetInspector() {
    	assertEquals(inspectorMock, zona.getInspector());
    }
    
    @Test
    public void testUnInspectorSiempreSeLeAsignaLaZona() {
    	verify(inspectorMock, times(1)).asignarZonaDeEstacionamiento(zona);
    }

    @Test 
    public void testAgregarUnPuntoDeVenta() {
    	ZonaDeEstacionamiento zonaConInspectorNuevo = new ZonaDeEstacionamiento(sistemaMock);
    	zonaConInspectorNuevo.registrar(mock(PuntoVenta.class));
    	assertEquals(false, zonaConInspectorNuevo.getPuntosDeVenta().isEmpty());
    }
    
    
}
