package empresaTransportista;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpresaTransportistaTest {
	
	EmpresaTransportista empT;
	
	@BeforeEach
	void setUp() {
		empT = new EmpresaTransportista();
	}

	@Test
	void verificarRegistroDeCamionTest() {
		Camion camion1 = mock(Camion.class);
		
		//Configuro el comportamiento del mock
		
		when(camion1.getPatente()).thenReturn("asd123");
		
		empT.registrarCamion(camion1);
		
		boolean resultado = empT.estaRegistradoElCamionDePatente("asd123"); 
		//Retorno True si esta registrado
		boolean resultado1 = empT.estaRegistradoElCamionDePatente("zzz555");
		//Retorna False porque no hay ningun camion registrado con esa patente
		
		assertTrue(resultado);
		assertFalse(resultado1);
		
		//Verificar que se llamo al metetodo getPatente()
		
		verify(camion1, atLeastOnce()).getPatente();
		
		
	}

}
