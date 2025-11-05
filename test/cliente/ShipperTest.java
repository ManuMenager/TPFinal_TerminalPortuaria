package cliente;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipperTest {

	private Shipper shipper;
	
	@BeforeEach
	public void setUp() {
		shipper = new Shipper("Mariano", "Moreno", 35265456,"marianomoreno@gmail.com");
	}
	
	@Test
	void verificarDatosCargados() {
		assertEquals(shipper.getNombre(), "Mariano");
		assertEquals(shipper.getApellido(), "Moreno");
		assertEquals(shipper.getDni(), 35265456);
		assertEquals(shipper.getEmail(), "marianomoreno@gmail.com");
	}
	
	@Test
	void verificarCambioDeEmail() {
		assertEquals(shipper.getEmail(), "marianomoreno@gmail.com");
		shipper.setEmail("nuevoEmail@gmail.com");
		assertEquals(shipper.getEmail(), "nuevoEmail@gmail.com");
	}

}
