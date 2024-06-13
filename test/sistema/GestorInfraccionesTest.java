package sistema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.zona.ZonaDeEstacionamiento;
import sistema.zona.infracciones.GestorInfracciones;
import sistema.zona.infracciones.Infraccion;
import sistema.zona.infracciones.Inspector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GestorInfraccionesTest {

    private GestorInfracciones gestor;
    private List<Infraccion> infracciones;
    private SistemaCentral sistemaMock;

    @BeforeEach
    void setUp() {
        sistemaMock = mock(SistemaCentral.class);
        gestor = new GestorInfracciones(sistemaMock);
    }

    @Test
    void testGenerarInfraccion() {
        String patente = "AAA000";
        Inspector inspectorMock = mock(Inspector.class);
        ZonaDeEstacionamiento zonaMock = mock(ZonaDeEstacionamiento.class);
        when(inspectorMock.getZona()).thenReturn(zonaMock);
        gestor.generarInfraccion(patente, inspectorMock);
        assertTrue(gestor.tieneInfraccion(patente));
    }
}