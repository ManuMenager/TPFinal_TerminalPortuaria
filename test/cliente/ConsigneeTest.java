package cliente;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsigneeTest {

	private Consignee consignee;
	
	@BeforeEach
	public void setUp() {
		consignee = new Consignee("Mariano", "Moreno", 35265456,"marianomoreno@gmail.com");
	}
	
	@Test
	void verificarDatosCargados() {
		assertEquals(consignee.getNombre(), "Mariano");
		assertEquals(consignee.getApellido(), "Moreno");
		assertEquals(consignee.getDni(), 35265456);
		assertEquals(consignee.getEmail(), "marianomoreno@gmail.com");
	}
	
	@Test
	void verificarCambioDeEmail() {
		assertEquals(consignee.getEmail(), "marianomoreno@gmail.com");
		consignee.setEmail("nuevoEmail@gmail.com");
		assertEquals(consignee.getEmail(), "nuevoEmail@gmail.com");
	}
}
