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

class CriterioMenorTiempoTest {

	@Test
	void circuitoConMenorTiempoTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
		
		Circuito c1 = mock(Circuito.class);
		Circuito c2 = mock(Circuito.class);
		Circuito c3 = mock(Circuito.class);
		Circuito c4 = mock(Circuito.class);
		
		List<Circuito> circuitos = Arrays.asList(c1, c2, c3, c4);
		CriterioMenorTiempo criterio = new CriterioMenorTiempo();
		
		//Configuro los comportamientos de los mocks
		
		when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		when(c3.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(c4.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		
		//El filtro descarta el circuito 3 por false
		when(c1.tiempoDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(150);
		when(c2.tiempoDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(120);
		when(c4.tiempoDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(160);
		
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
		CriterioMenorTiempo criterio = new CriterioMenorTiempo();
		
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
		CriterioMenorTiempo criterio = new CriterioMenorTiempo();
			
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
	void testConMenorTiempoIguales() {
	    TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
	    TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
	    
	    Circuito c1 = mock(Circuito.class);
	    Circuito c2 = mock(Circuito.class);
	    
	    List<Circuito> circuitos = Arrays.asList(c1, c2);
	    CriterioMenorTiempo criterio = new CriterioMenorTiempo();
	    
	    when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    
	    when(c1.tiempoDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100);
	    when(c2.tiempoDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100);
	    
	    Circuito resultado = criterio.mejorCircuito(circuitos, terminalGestionada, puertoDestino);
	    
	    // Cuando hay tiempo iguales, Stream.min() devuelve el primero que encuentra
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
	    CriterioMenorTiempo criterio = new CriterioMenorTiempo();
	    
	    when(c1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    when(c2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false); // No válido
	    
	    when(c1.tiempoDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(20);
	    
	    Circuito resultado = criterio.mejorCircuito(circuitos, terminalGestionada, puertoDestino);
	    
	    assertEquals(c1, resultado);
	}

}
