package estacionamientoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.estacionamiento.EstacionamientoPorApp;

public class EstacionamientoPorAppTest {

   private String patente = "AAA000";
   private LocalTime horaInicio = LocalTime.now();
   private LocalTime horaFin = LocalTime.now().plusHours(10);
   private float costo = 40f;
   private int numeroDeCelular = 1122334455;
   private EstacionamientoPorApp estacionamiento;


    @BeforeEach
    public void setUp() {
    	estacionamiento = new EstacionamientoPorApp(patente, horaInicio, horaFin, costo, numeroDeCelular);
    }
    
    @Test
    public void testGetNumeroDeCelular() {
    	assertEquals(numeroDeCelular, estacionamiento.getNumeroDeCelular());
    }
    
    @Test 
    public void testLaHoraFinSeVuelveLaHoraActualCuandoFinaliza() {
    	estacionamiento.finalizar();
    	assertEquals(LocalTime.now().getHour(), estacionamiento.getHoraFin().getHour());
    				// Esto es debido a que fallaría por no tener el segundo exacto...
    }
}