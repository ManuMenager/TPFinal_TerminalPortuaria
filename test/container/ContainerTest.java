package container;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ContainerTest {
	Container container;
	Dry dry;
	Reefer reefer;
	Tanque tanque;
	DryComposite dryComposite;
	
	@BeforeEach
	public void setUp() {
		container = new Container("RODR1234567", 60 , 300 , 70 , 500 , true);
		dry = new Dry("MANU1234567", 50 , 200 , 50 , 200 );
		reefer = new Reefer("FRAN1234567", 30 , 200 , 60 , 500,false,3.0);
		tanque = new Tanque("ELIAS123467", 40 , 200 , 60 , 500, true);
		dryComposite = new DryComposite("COMP1234567", 50,400, 600, 1000 );
	}

	@Test
	void containerReeferTest() {
		LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0, 0); // 1 de Enero, 10:00:00
        LocalDateTime fin = LocalDateTime.of(2025, 1, 1, 20, 0, 0);     // 1 de Enero, 20:00:00
        
		reefer.conectar(inicio);
		reefer.desconectar(fin);
		
		
		assertEquals(10.0, reefer.getHorasConectado());
		assertEquals(30.0, reefer.getHorasConectado() * reefer.getConsumoPorHora());

	}
	public void verificoQueCuandoSeCreaContainerTieneTodasSusDimencionesDeclaradas() {
		assertTrue(container.getId() == "RODR1234567" );
		assertTrue(container.getAltura() == 60 );
		assertTrue(container.getLargo() == 300 );
		assertTrue(container.getAncho() == 70 );
		assertTrue(container.pesoTotal() == 500 );
		assertTrue(container.isServicioEspecial() == true );

}
	@Test 
	public void verificoQueCuandoSeCreaContainerDryTieneTodasSusDimencionesDeclaradas() {
			assertTrue(dry.getAltura() == 50 );
			assertTrue(dry.getLargo() == 200 );
			assertTrue(dry.getAncho() == 50 );
			assertTrue(dry.getId() == "MANU1234567" );
			assertTrue(dry.isServicioEspecial() == true );
	
	}
	@Test
	public void verificoQueCuandoSeCreaContainerTanqueTieneTodasSusDimencionesDeclaradas() {
		assertTrue(tanque.getId() == "ELIAS123467" );
		assertTrue(tanque.getAncho() == 40 );
		assertTrue(tanque.getLargo() == 200 );
		assertTrue(tanque.getAltura() == 60 );
		assertTrue(tanque.pesoTotal() == 500 );
		assertTrue(tanque.isServicioEspecial() == true );
	}
	@Test
	public void verificoQueCuandoSeCreaContainerReeferTieneTodasSusDimencionesDeclaradas() {
		assertTrue(reefer.getId() == "FRAN1234567" );
		assertTrue(reefer.getAncho() == 30 );
		assertTrue(reefer.getLargo() == 200 );
		assertTrue(reefer.getAltura() == 60 );
		assertTrue(reefer.pesoTotal() == 500 );
		assertTrue(reefer.isServicioEspecial() == false );
	}
	@Test
	public void pesoTotalDeDrys() {
		dryComposite.add(dry);
		dryComposite.add(dry);
		assertEquals(1400.0, dryComposite.pesoTotal());
	}
}
