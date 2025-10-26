package viaje;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buque.Buque;
import circuito.Circuito;
import circuito.Tramo;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

class ViajeTest {
	Circuito spyCircuito;
	Buque buque;
	Viaje viaje; 
	Tramo spyTramo1;
	Tramo spyTramo2;
	Tramo spyTramo3;
	TerminalPortuaria China;
	TerminalPortuaria Argentina;
	TerminalPortuaria Brasil;
	TerminalPortuaria EstadosUnidos;
	
	@BeforeEach
	void setUp() {
		spyCircuito = spy(new Circuito());
		China = mock(TerminalPortuaria.class);
		Argentina = mock(TerminalPortuaria.class);
		Brasil = mock(TerminalPortuaria.class);
		EstadosUnidos = mock(TerminalPortuaria.class);
		spyTramo1 = spy(new Tramo(Argentina, Brasil, 5, 1500.00));
		spyTramo2 = spy(new Tramo(Brasil, China, 20, 10960.00));
		spyTramo3 = spy(new Tramo(China, Argentina, 25, 13260.00));
		
		spyCircuito.agregarTramo(spyTramo1);
		spyCircuito.agregarTramo(spyTramo2);
		spyCircuito.agregarTramo(spyTramo3);
		
		buque = mock(Buque.class);
		viaje = new Viaje(buque, spyCircuito, LocalDateTime.of(2025, 12, 23, 15, 00), Argentina); // 23/12/2025 a las 15hs.
	}
	
	@Test
	void proximaFechaHaciatest() {
		LocalDateTime proximaFecha = viaje.proximaFechaHacia(Brasil);
		LocalDateTime proximaFecha2 = viaje.proximaFechaHacia(EstadosUnidos);
		LocalDateTime proximaFecha3 = viaje.proximaFechaHacia(China);
		
		assertEquals(LocalDateTime.of(2025, 12, 23, 15, 00), proximaFecha);
		assertNotEquals(LocalDateTime.of(2025, 12, 23, 15, 00), proximaFecha2);
		assertEquals(LocalDateTime.of(2025, 12, 23, 15, 00), proximaFecha3);
		
		verify(spyCircuito).contieneTerminal(Brasil);
		verify(spyCircuito).contieneTerminal(EstadosUnidos);
		verify(spyCircuito).contieneTerminal(China);
	}
	
	@Test
	void tieneProximaFechaDeLlegadaTest() {
		boolean resultado = viaje.tieneProximaFechaDeLlegada(LocalDateTime.of(2025, 12, 23, 20, 00));
		boolean resultado2 = viaje.tieneProximaFechaDeLlegada(LocalDateTime.of(2025, 12, 24, 16, 00));
		boolean resultado3 = viaje.tieneProximaFechaDeLlegada(LocalDateTime.of(2025, 11, 20, 17, 00));
		
		assertTrue(resultado);
		assertTrue(resultado2);
		assertFalse(resultado3);
	}
	
	@Test
	void tieneFechaSalidaTest() {
		boolean resultado = viaje.tieneFechaSalida(LocalDateTime.of(2025, 12, 23, 15, 00));
		boolean resultado2 = viaje.tieneFechaSalida(LocalDateTime.of(2025, 12, 23, 20, 00));
		
		assertTrue(resultado);
		assertFalse(resultado2);
	}
	
	@Test
	void tieneComoPuertoDestinoTest() {
		boolean resultado = viaje.tieneComoPuertoDestino(Brasil);
		boolean resultado2 = viaje.tieneComoPuertoDestino(China);
		boolean resultado3 = viaje.tieneComoPuertoDestino(EstadosUnidos);
		
		assertTrue(resultado);
		assertTrue(resultado2);
		assertFalse(resultado3);
	}
}
