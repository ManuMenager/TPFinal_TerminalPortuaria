package empresaTransportista;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CamionTest {
	
	Camion camion;
	
	@BeforeEach
	void setUp() {
		camion = new Camion("asd123");
	}

	@Test
	void verificarChoferTest() {
		Chofer chofer = mock(Chofer.class);
		Chofer chofer1 = mock(Chofer.class);
		
		//Configuro el comportamiento del mock
		
		when(chofer.getDni()).thenReturn(38689123);
		when(chofer1.getDni()).thenReturn(45649123);
		
		camion.registrarChofer(chofer); //Registro solo a chofer 
		
		boolean resultado = camion.estaRegistradoElChofer(chofer);
		//deberia ser true ya que esta registrado
		boolean resultado1 = camion.estaRegistradoElChofer(chofer1);
		// deberia ser false ya que no esta registrado
		
		assertTrue(resultado);
		assertFalse(resultado1);
		
		//Verificar que se llamo al metetodo getDni()
		
		verify(chofer, atLeastOnce()).getDni();
		
	}

}
