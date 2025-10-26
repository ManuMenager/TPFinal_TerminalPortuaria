package filtro;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

class FiltroPuertoDestinoTest {

	@Test
    void testFiltrarUnViajeQueCoincideConPuertoDestino() {
        // setUp - Construccion de clases con Mock 
        TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viajeCoincidente = mock(Viaje.class);
        Viaje viajeNoCoincidente = mock(Viaje.class);
        
        //Configuro el comportamiento de los mocks 
        when(viajeCoincidente.tieneComoPuertoDestino(puertoDestino)).thenReturn(true);
        when(viajeNoCoincidente.tieneComoPuertoDestino(puertoDestino)).thenReturn(false);
        
        //Armo lista de viaje a filtrar y construyo el filtro con la fechaFiltro
        List<Viaje> viajes = Arrays.asList(viajeCoincidente, viajeNoCoincidente);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify - Verificacion de los resultados esperados 
        assertEquals(1, resultado.size());
        assertEquals(viajeCoincidente, resultado.get(0));
        verify(viajeCoincidente).tieneComoPuertoDestino(puertoDestino);
        verify(viajeNoCoincidente).tieneComoPuertoDestino(puertoDestino);
    }
	
	@Test
    void testFiltrarListaVacia() {
        // SetUp - Armo el escenario a probar 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        List<Viaje> viajesVacias = new ArrayList<>();
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajesVacias);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
	
	@Test
    void testFiltrarVariosViajesQueCoinciden() {
        // setUp - construccion de viajes con mock 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        Viaje viaje3 = mock(Viaje.class);
        
        // Configuracion de comportamiento de los mocks 
        when(viaje1.tieneComoPuertoDestino(puertoDestino)).thenReturn(true);
        when(viaje2.tieneComoPuertoDestino(puertoDestino)).thenReturn(true);
        when(viaje3.tieneComoPuertoDestino(puertoDestino)).thenReturn(false);
        
        
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2, viaje3);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise 
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify - verificar los resultados esperados 
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(viaje1));
        assertTrue(resultado.contains(viaje2));
        assertFalse(resultado.contains(viaje3));
    }
	
	@Test
    void testFiltrarNingunViajeCoincide() {
        // setUp - construccion de viajes con mock 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        
        //Configuracion de comportamiento de los mocks
        when(viaje1.tieneComoPuertoDestino(puertoDestino)).thenReturn(false);
        when(viaje2.tieneComoPuertoDestino(puertoDestino)).thenReturn(false);
        
        //Construccion de lista de viajes y el filtro 
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise 
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
}
