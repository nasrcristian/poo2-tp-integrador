package sistema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registroCompras.RegistroCompra;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GestorRegistrosTest {

    private GestorRegistros gestor;
    private List<RegistroCompra> registros;

    @BeforeEach
    void setUp() {
        gestor = new GestorRegistros();
    }

    @Test
    void testAgregarRegistro() {
        RegistroCompra registroCompraMock = mock(RegistroCompra.class);
        when(registroCompraMock.getNumeroControl()).thenReturn(1);
        gestor.agregarRegistro(registroCompraMock);
        assertTrue(gestor.tieneRegistro(registroCompraMock.getNumeroControl()));
    }

    @Test
    void testEliminarRegistro() {
        RegistroCompra registroCompraMock = mock(RegistroCompra.class);
        when(registroCompraMock.getNumeroControl()).thenReturn(1);
        gestor.agregarRegistro(registroCompraMock);
        gestor.eliminarRegistro(registroCompraMock.getNumeroControl());
        assertFalse(gestor.tieneRegistro(registroCompraMock.getNumeroControl()));
    }
}