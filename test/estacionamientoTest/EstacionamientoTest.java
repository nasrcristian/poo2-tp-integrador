package estacionamientoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import registroCompras.RegistroCompraPuntual;
import sistema.estacionamiento.Estacionamiento;
import sistema.estacionamiento.EstacionamientoPuntual;

public class EstacionamientoTest {

   private String patente = "AAA000";
   private LocalTime horaInicio = LocalTime.now();
   private LocalTime horaFin = LocalTime.now().plusHours(10);
   private float costo = 40f;
   private RegistroCompraPuntual registroMock;
   private Estacionamiento estacionamiento;


    @BeforeEach
    public void setUp() {
    	registroMock = mock(RegistroCompraPuntual.class);
    	estacionamiento = new EstacionamientoPuntual(patente, horaInicio, horaFin, costo, registroMock);
    }
    
    @Test
    public void testGetPatente() {
    	assertEquals(patente, estacionamiento.getPatente());
    }
    
    @Test
    public void testGetHoraInicio() {
    	assertEquals(horaInicio, estacionamiento.getHoraInicio());
    }
    
    @Test
    public void testGetHoraFin() {
    	assertEquals(horaFin, estacionamiento.getHoraFin());
    }
    
    @Test
    public void testGetDuracion() {
    	assertEquals(Duration.between(horaInicio, horaFin).toHours(), estacionamiento.getDuracion());
    }
    
    @Test
    public void testComprobarEstaVigente() {
    	LocalTime horaActual = LocalTime.now();
    	assertEquals(horaActual.isBefore(this.horaFin) && horaActual.isAfter(this.horaInicio), estacionamiento.estaVigente());
    }
    
    @Test
    public void testSePuedeCalcularElCostoTotal() {
    	assertEquals(costo * Duration.between(horaInicio, horaFin).toHours(), estacionamiento.getCostoTotal());
    }
    
    
}
