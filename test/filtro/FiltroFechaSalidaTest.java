package filtro;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import viaje.Viaje;

class FiltroFechaSalidaTest {

	@Test
	void testConUnViajeQueCoincideFechaSalida() {
		// setUp - Construccion de clases con Mock 
        LocalDateTime fechaFiltro = LocalDateTime.of(2025, 12, 1, 9, 00);
        Viaje viajeCoincidente = mock(Viaje.class);
        Viaje viajeNoCoincidente = mock(Viaje.class);
        
      //Configuro el comportamiento de los mocks 
        when(viajeCoincidente.tieneFechaSalida(fechaFiltro)).thenReturn(true);
        when(viajeNoCoincidente.tieneFechaSalida(fechaFiltro)).thenReturn(false);
        
        //Armo lista de viaje a filtrar y construyo el filtro con la fechaFiltro
        List<Viaje> viajes = Arrays.asList(viajeCoincidente, viajeNoCoincidente);
        FiltroFechaSalida filtro = new FiltroFechaSalida(fechaFiltro);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify - Verificacion de los resultados esperados 
        assertEquals(1, resultado.size());
        assertEquals(viajeCoincidente, resultado.get(0));
        verify(viajeCoincidente).tieneFechaSalida(fechaFiltro);
        verify(viajeNoCoincidente).tieneFechaSalida(fechaFiltro);
	}
	
	@Test
    void testFiltrarListaVacia() {
        // SetUp - Armo el escenario a probar 
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 4, 00);
        List<Viaje> viajesVacias = new ArrayList<>();
        FiltroFechaSalida filtro = new FiltroFechaSalida(fechaFiltro);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajesVacias);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
	
	@Test
    void testFiltrarVariosViajesQueCoinciden() {
        // setUp - construccion de viajes con mock 
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 5,00);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        Viaje viaje3 = mock(Viaje.class);
        
        // Configuracion de comportamiento de los mocks 
        when(viaje1.tieneFechaSalida(fechaFiltro)).thenReturn(true);
        when(viaje2.tieneFechaSalida(fechaFiltro)).thenReturn(true);
        when(viaje3.tieneFechaSalida(fechaFiltro)).thenReturn(false);
        
        
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2, viaje3);
        FiltroFechaSalida filtro = new FiltroFechaSalida(fechaFiltro);
        
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
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 5, 00);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        
        //Configuracion de comportamiento de los mocks
        when(viaje1.tieneFechaSalida(fechaFiltro)).thenReturn(false);
        when(viaje2.tieneFechaSalida(fechaFiltro)).thenReturn(false);
        
        //Construccion de lista de viajes y el filtro 
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        FiltroFechaSalida filtro = new FiltroFechaSalida(fechaFiltro);
        
        // Exercise 
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }

}
