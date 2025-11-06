package factura;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import orden.Orden;
import servicio.Lavado;
import servicio.Pesado;
import servicio.Servicio;
import viaje.Viaje;

class FacturaTest {
	private Orden orden;
	Set<ConceptoServicio> conceptos;
	private Lavado servicioLavado;
	private Pesado servicioPesado;
	private Viaje viaje;
	Factura factura ;
	@BeforeEach
	public void setUp() {
        // Crear mocks
	orden = mock(Orden.class);
	servicioLavado = mock(Lavado.class);
	servicioPesado = mock(Pesado.class);
	viaje = mock(Viaje.class);
	// Crea Factura
	factura = new Factura(orden);

    // Configurar comportamiento de los servicios
    when(servicioLavado.getNombreServicio()).thenReturn("Lavado");
    when(servicioLavado.getFechaServicio()).thenReturn(LocalDate.of(2025, 11, 5));
    when(servicioLavado.costoServicio()).thenReturn((int) 1500.0);

    when(servicioPesado.getNombreServicio()).thenReturn("Pesado");
    when(servicioPesado.getFechaServicio()).thenReturn(LocalDate.of(2025, 11, 5));
    when(servicioPesado.costoServicio()).thenReturn(800.0);

    // Configurar viaje
    when(viaje.getMontoTotal()).thenReturn(10000.0);

    // Configurar orden
    List<Servicio> servicios = Arrays.asList(servicioLavado, servicioPesado);
    when(orden.getServicios()).thenReturn((ArrayList<Servicio>) servicios);
    when(orden.getViaje()).thenReturn(viaje);
    when(orden.montoTotalServicios()).thenReturn(2300.0);

	
  
}

	@Test
	public void testFacturaContieneTodosLosConceptos() {
	    Set<ConceptoServicio> conceptos = factura.getConceptos();
	
	    // La factura debe tener: 2 servicios + subtotal + viaje + total
	    assertEquals(5, conceptos.size(),
	        "La factura debería tener 5 conceptos (2 servicios + subtotal + viaje + total)");
	}
	
	@Test
	public void testMontoTotalCalculadoCorrectamente() {
	    double totalEsperado = 2300.0 + 10000.0; // servicios + viaje
	
	    // Buscar el concepto "TOTAL A PAGAR"
	    ConceptoServicio totalConcepto = factura.getConceptos()
	        .stream()
	        .filter(c -> c.getNombreServicio().equals("TOTAL A PAGAR"))
	        .findFirst()
	        .orElse(null);
	
	    assertNotNull(totalConcepto, "Debe existir un concepto 'TOTAL A PAGAR'");
	    assertEquals(totalEsperado, totalConcepto.getMonto(), 0.01,
	        "El total facturado debe ser la suma de los servicios y el viaje");
	}
	
	@Test
	public void testFacturaSinViajeSoloServicios() {
	    // Orden sin viaje
	    when(orden.getViaje()).thenReturn(null);
	    when(orden.montoTotalServicios()).thenReturn(2300.0);
	
	    Factura facturaSoloServicios = new Factura(orden);
	
	    // Debe contener: 2 servicios + subtotal + total (4 conceptos)
	    assertEquals(4, facturaSoloServicios.getConceptos().size(),
	        "Factura sin viaje debería tener 4 conceptos");
	}

	@Test
	public void testImpresionDesgloseNoEsVacia() {
	    String salida = factura.desgloseDeServicios();
	    assertNotNull(salida);
	    assertTrue(salida.contains("FACTURA"));
	    assertTrue(salida.contains("TOTAL A PAGAR"));
	    assertTrue(salida.contains("Lavado"));
	    assertTrue(salida.contains("Electricidad"));
	}
}
 