package naviera;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buque.Buque;
import circuito.Circuito;
import circuito.Tramo;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

class NavieraTest {
	Naviera naviera;
	Buque b1;
	Buque b2;
	Buque b3;
	List<Buque> buques;
	Viaje viaje1;
	Viaje viaje2;
	Viaje viaje3;
	Circuito circuito1;
	Circuito circuito2;
	Circuito circuito3;
	Tramo argToBra;
	Tramo braToEsp;
	Tramo espToArg;
	Tramo argToUsa;
	Tramo braToArg;
	Tramo usaToBra;
	TerminalPortuaria Argentina;
	TerminalPortuaria Brasil;
	TerminalPortuaria España;
	TerminalPortuaria EstadosUnidos;
	
	@BeforeEach
	void setUp() {
		b1 = new Buque();
		b2 = new Buque();
		b3 = new Buque();
		buques = new ArrayList<>(List.of(b1, b2 , b3));
		
		Argentina = mock(TerminalPortuaria.class);
		Brasil = mock(TerminalPortuaria.class);
		España = mock(TerminalPortuaria.class);
		EstadosUnidos = mock(TerminalPortuaria.class);
		
		circuito1 = spy(new Circuito());
		circuito2 = spy(new Circuito());
		circuito3 = spy(new Circuito());
		
		argToBra = new Tramo(Argentina, Brasil, 5, 500);
		braToEsp = new Tramo(Brasil, España, 10, 1000);
		espToArg = new Tramo(España, Argentina, 15, 1500);
		argToUsa = new Tramo(Argentina, EstadosUnidos, 20, 2000);
		braToArg = new Tramo(Brasil, Argentina, 5, 500);
		usaToBra = new Tramo(EstadosUnidos, Brasil, 18, 1700);
		
		circuito1.agregarTramo(argToBra);
		circuito1.agregarTramo(braToEsp);
		circuito1.agregarTramo(espToArg);
		
		circuito2.agregarTramo(braToArg);
		circuito2.agregarTramo(argToUsa);
		circuito2.agregarTramo(usaToBra);
		
		circuito3.agregarTramo(espToArg);
		circuito3.agregarTramo(argToBra);
		circuito3.agregarTramo(braToEsp);
		
		viaje1 = spy(new Viaje(b1, circuito1, LocalDateTime.of(2025, 12, 23, 15, 30), Argentina));
		viaje2 = spy(new Viaje(b2, circuito2, LocalDateTime.of(2026, 2, 15, 18, 30), Brasil));
		viaje3 = spy(new Viaje(b3, circuito3, LocalDateTime.of(2025, 11, 5, 10, 15), España));
		
		naviera = new Naviera(buques);
		
		naviera.registrarCircuito(circuito1);
		naviera.registrarCircuito(circuito2);
		naviera.registrarCircuito(circuito3);
		naviera.registrarViaje(viaje1);
		naviera.registrarViaje(viaje2);
		naviera.registrarViaje(viaje3);
	}
	
	@Test
	void proximaFechaHaciaTest() {
		Optional<LocalDateTime> fecha = naviera.proximaFechaHacia(España);
		
		assertEquals(LocalDateTime.of(2025, 11, 5, 10, 15), fecha.get());
		
		verify(viaje1).proximaFechaHacia(España);
		verify(viaje2).proximaFechaHacia(España);
		verify(viaje3).proximaFechaHacia(España);
	}
	
	@Test
	void recorreDesdeHastaTest() {
		boolean resultado = naviera.recorreDesdeHasta(Argentina, España);
		boolean resultado2 = naviera.recorreDesdeHasta(Brasil, España);
		boolean resultado3 = naviera.recorreDesdeHasta(Argentina, EstadosUnidos);
		boolean resultado4 = naviera.recorreDesdeHasta(EstadosUnidos, España);
		
		assertTrue(resultado);
		assertTrue(resultado2);
		assertTrue(resultado3);
		assertFalse(resultado4);
	}
	
	@Test
	void escalasDesdeHastaTest() {
		List<Integer> escalas = naviera.escalasDesdeHasta(Argentina, España);
		List<Integer> escalas2 = naviera.escalasDesdeHasta(Brasil, Argentina);
		
		assertEquals(List.of(1,1), escalas);
		assertEquals(List.of(1,0), escalas2);
		assertThrows(IllegalArgumentException.class, () -> {
			naviera.escalasDesdeHasta(España, EstadosUnidos);
		});
	}
	
	@Test
	void precioDesdeHastaTest() {
		List<Double> precios = naviera.preciosDesdeHasta(Argentina, España);
		List<Double> precios2 = naviera.preciosDesdeHasta(Brasil, Argentina);
		
		assertEquals(List.of(1500.0,1500.0), precios);
		assertEquals(List.of(2500.0,500.0), precios2);
		assertThrows(IllegalArgumentException.class, () -> {
			naviera.preciosDesdeHasta(España, EstadosUnidos);
		});
	}
}