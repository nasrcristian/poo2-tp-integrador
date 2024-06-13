package infraccionTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.zona.ZonaDeEstacionamiento;
import sistema.zona.infracciones.Infraccion;
import sistema.zona.infracciones.Inspector;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InfraccionTest {

    private Infraccion infraccion;
    private String patente = "AAA000";
    private LocalDate fecha = LocalDate.now();
    private LocalTime hora = LocalTime.now();
    private ZonaDeEstacionamiento zonaMock;
    private Inspector inspectorMock;

    @BeforeEach
    public void setUp(){
        zonaMock = mock(ZonaDeEstacionamiento.class);
        inspectorMock = mock(Inspector.class);
        infraccion = new Infraccion(patente, fecha, hora, zonaMock, inspectorMock);
    }

    @Test
    void testGetPatente(){
        assertEquals(patente, infraccion.getPatente());
    }

    @Test
    void testGetFecha() {
        assertEquals(fecha, infraccion.getFecha());
    }

    @Test
    void testGetHora() {
        assertEquals(hora, infraccion.getHora());
    }

    @Test
    void testGetZona() {
        assertEquals(zonaMock, infraccion.getZona());
    }

    @Test
    void getInspector() {
        assertEquals(inspectorMock, infraccion.getInspector());
    }
}