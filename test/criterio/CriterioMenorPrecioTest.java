package criterio;


import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import circuito.Circuito;
import terminalPortuaria.TerminalPortuaria;

class CriterioMenorPrecioTest {

	@Test
	void circuitoConMenorPrecioTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
		
		Circuito c1 = mock(Circuito.class);
		Circuito c2 = mock(Circuito.class);
		Circuito c3 = mock(Circuito.class);
		Circuito c4 = mock(Circuito.class);
		
		List<Circuito> circuitos = Arrays.asList(c1, c2, c3, c4);
		CriterioMenorPrecio criterio = new CriterioMenorPrecio();
		
		//Configuro los comportamientos de los mocks
		
		when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		when(c3.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(c4.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		
		//El filtro descarta el circuito 3 por false
		when(c1.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(150.00);
		when(c2.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(120.00);
		when(c4.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(160.00);
		
		//Exercise
		Circuito resultado = criterio.mejorCircuito(circuitos,terminalGestionada,puertoDestino);
		
		// Verify - verificar los resultados esperados 
		assertEquals(resultado, c2);
	}
	
	@Test
	void listaCircuitoVaciaTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
		List<Circuito> circuitos = new ArrayList<>();
		CriterioMenorPrecio criterio = new CriterioMenorPrecio();
		
		//Lista vacia debe lanzar la exception.
		assertThrows(IllegalArgumentException.class, 
				() -> criterio.mejorCircuito(circuitos, terminalGestionada, puertoDestino));
	}
	
	@Test
	void listaCircuitoSinCoincidirConLosPuertosTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
			
		Circuito c1 = mock(Circuito.class);
		Circuito c2 = mock(Circuito.class);
		Circuito c3 = mock(Circuito.class);
		Circuito c4 = mock(Circuito.class);
				
		List<Circuito> circuitos = Arrays.asList(c1, c2, c3, c4);
		CriterioMenorPrecio criterio = new CriterioMenorPrecio();
			
		//Configuro los comportamientos de los mocks
		when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(c3.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(c4.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		
		//Lista vacia luego del filtrado por coincidencia de puertos, debe lanzar la exception.
		assertThrows(IllegalArgumentException.class, 
				() -> criterio.mejorCircuito(circuitos, terminalGestionada, puertoDestino));
	}
	
	@Test
	void testConPreciosIguales() {
	    TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
	    TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
	    
	    Circuito c1 = mock(Circuito.class);
	    Circuito c2 = mock(Circuito.class);
	    
	    List<Circuito> circuitos = Arrays.asList(c1, c2);
	    CriterioMenorPrecio criterio = new CriterioMenorPrecio();
	    
	    when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    
	    when(c1.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100.0);
	    when(c2.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100.0);
	    
	    Circuito resultado = criterio.mejorCircuito(circuitos, terminalGestionada, puertoDestino);
	    
	    // Cuando hay precios iguales, Stream.min() devuelve el primero que encuentra
	    assertNotNull(resultado);
	    //verificar que al menos es uno de los dos
	    assertTrue(resultado == c1 || resultado == c2);
	}
	
	@Test
	void testConUnSoloCircuitoValido() {
	    TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
	    TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
	    
	    Circuito c1 = mock(Circuito.class);
	    Circuito c2 = mock(Circuito.class);
	    
	    List<Circuito> circuitos = Arrays.asList(c1, c2);
	    CriterioMenorPrecio criterio = new CriterioMenorPrecio();
	    
	    when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false); // No válido
	    
	    when(c1.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(200.0);
	    
	    Circuito resultado = criterio.mejorCircuito(circuitos, terminalGestionada, puertoDestino);
	    
	    assertEquals(c1, resultado);
	}
}
