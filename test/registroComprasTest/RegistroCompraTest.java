package registroComprasTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import puntoVenta.PuntoVenta;
import registroCompras.RegistroCompraPuntual;

public class RegistroCompraTest {
    private RegistroCompraPuntual registroCompra;
    private PuntoVenta puntoDeVentaMock;
    private Integer nroControl = 111;
    private String patente = "AAA000";
    private LocalDate dia = LocalDate.now();
    private LocalTime hora = LocalTime.now();
    private Integer horas = 10;

    @BeforeEach
    public void setUp() {
        puntoDeVentaMock = mock(PuntoVenta.class);
        registroCompra = new RegistroCompraPuntual(nroControl, puntoDeVentaMock, dia, hora, patente, horas);
    }

    @Test
    public void testGetNroControl() {
        assertEquals(nroControl, registroCompra.getNumeroControl());
    }

    @Test
    public void testGetPuntoDeVenta() {
        assertEquals(puntoDeVentaMock, registroCompra.getPuntoVenta());
    }
    
    @Test 
    public void testGetDia() {
    	assertEquals(dia, registroCompra.getFecha());
    }
    
    @Test
    public void testGetHora() {
    	assertEquals(hora, registroCompra.getHora());
    }

}

