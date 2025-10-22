package circuito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terminalPortuaria.TerminalPortuaria;

class CircuitoTest {
	Circuito c1;
	Tramo t1;
	Tramo t2;
	Tramo t3;
	TerminalPortuaria China;
	TerminalPortuaria España;
	TerminalPortuaria EstadosUnidos;
	TerminalPortuaria Brasil;
	
	@BeforeEach
	public void setUp() {
		c1 = new Circuito();
		t1 = mock(Tramo.class);
		t2 = mock(Tramo.class);
		t3 = mock(Tramo.class);
		China = mock(TerminalPortuaria.class);
		España = mock(TerminalPortuaria.class);
		EstadosUnidos = mock(TerminalPortuaria.class);
		Brasil = mock(TerminalPortuaria.class);
		c1.agregarTramo(t1);
		c1.agregarTramo(t2);
		c1.agregarTramo(t3);
	}
	
	@Test
	void terminalOrigenTest() {
		when(t1.getOrigen()).thenReturn(China);
		
		assertEquals(China, c1.terminalOrigen());
	}
	
	@Test
	void contieneTerminalesTest() {
		when(t1.getOrigen()).thenReturn(China);
		when(t1.getDestino()).thenReturn(España);
		when(t2.getDestino()).thenReturn(EstadosUnidos);
		when(t3.getDestino()).thenReturn(China);
		
		boolean resultado1 = c1.contieneTerminales(China, EstadosUnidos);
		boolean resultado2 = c1.contieneTerminales(China, Brasil);
		
		assertTrue(resultado1);
		assertFalse(resultado2);
		verify(t1).getOrigen();
		verify(t1).getDestino();
		verify(t2).getDestino();
		verify(t3).getDestino();
	}
	
	@Test
	void tiempoDesdeHastaTest() {
		when(t1.getDuracion()).thenReturn(10);
		when(t2.getDuracion()).thenReturn(5);
		when(t3.getDuracion()).thenReturn(2);
		when(t1.getOrigen()).thenReturn(China);
		when(t1.getDestino()).thenReturn(España);
		when(t2.getOrigen()).thenReturn(España);
		when(t2.getDestino()).thenReturn(EstadosUnidos);
		
		int duracion = c1.tiempoDesdeHasta(China, EstadosUnidos);
		
		assertEquals(15, duracion);
		assertThrows(IllegalArgumentException.class, () -> {
		    c1.tiempoDesdeHasta(China, Brasil);
		});
		verify(t1).getDuracion();
		verify(t2).getDuracion();
		verifyNoInteractions(t3);
	}
	
	@Test
	void precioFinalTest() {
		when(t1.getPrecio()).thenReturn(3999.99);
		when(t2.getPrecio()).thenReturn(2000.0);
		when(t3.getPrecio()).thenReturn(1000.0);
		
		double precioFinal = c1.precioFinal();
		
		assertEquals(6999.99, precioFinal);
		verify(t1, times(1)).getPrecio();
		verify(t2, times(1)).getPrecio();
		verify(t3, times(1)).getPrecio();
	}
}
