package estacionamientoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.estacionamiento.EstacionamientoPuntual;
import sistema.registros.RegistroCompraPuntual;

public class EstacionamientoPuntualTest {

   private String patente = "AAA000";
   private LocalTime horaInicio = LocalTime.now();
   private LocalTime horaFin = LocalTime.now().plusHours(10);
   private float costo = 40f;
   private RegistroCompraPuntual registroMock;
   private EstacionamientoPuntual estacionamiento;


    @BeforeEach
    public void setUp() {
    	registroMock = mock(RegistroCompraPuntual.class);
    	estacionamiento = new EstacionamientoPuntual(patente, horaInicio, horaFin, costo, registroMock);
    }
    
    @Test
    public void testGetRegistro() {
    	assertEquals(registroMock, estacionamiento.getRegistroCompra());
    }
}