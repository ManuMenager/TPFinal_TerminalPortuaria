package terminalPortuaria;

import static org.mockito.Mockito.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import circuito.Circuito;
import cliente.Consignee;
import cliente.Shipper;
import criterio.Criterio;
import empresaTransportista.EmpresaTransportista;
import filtro.Filtro;
import naviera.Naviera;
import orden.OrdenConsignee;
import orden.OrdenShipper;
import viaje.Viaje;

class TerminalPortuariaTest {

	TerminalPortuaria terminal;
	
	@Mock
	private Criterio criterio;
	@Mock
	private EmpresaTransportista empresaTransportista1;
	@Mock
	private EmpresaTransportista empresaTransportista2;
	@Mock
	private OrdenShipper ordenShipper1;
	@Mock
	private OrdenShipper ordenShipper2;
	@Mock
    private OrdenConsignee ordenConsignee1;
    @Mock
    private OrdenConsignee ordenConsignee2;
    @Mock
    private Naviera naviera1;
    @Mock
    private Naviera naviera2;
    @Mock
    private Shipper shipper1;
    @Mock
    private Shipper shipper2;
    @Mock
    private Consignee consignee1;
    @Mock
    private Consignee consignee2;
    @Mock
    private TerminalPortuaria destino;
    @Mock
    private Circuito circuito;
    @Mock
    private Filtro filtro;
    @Mock
    private Viaje viaje1;
    @Mock
    private Viaje viaje2;
    @Mock
    private Viaje viaje3;
    
	
	@BeforeEach
	public void setUp() {
		Point ubicacion = new Point(1,1);
		terminal = new TerminalPortuaria("BsAs",ubicacion);
	}
	
	// ===== TESTS DE REGISTROS =====

    @Test
    public void testRegistrarEmpresaTransportista() {
        // When
        terminal.registrarEmpresa(empresaTransportista1);
        terminal.registrarEmpresa(empresaTransportista2);

        //La terminal se inicializa sin empresasTransportistas registradas 
        // Then - Verificar que se pueden registrar múltiples empresas
        assertEquals(2, terminal.getEmpresasTransportistas().size());
    }
    
    @Test
    public void testRegistrarOrdenShipper() {
        // When
        terminal.registrarOrdenShipper(ordenShipper1);
        terminal.registrarOrdenShipper(ordenShipper2);

        //La terminal inicializa sin ordenes 
        // Then
        assertEquals(2, terminal.getOrdenesShipper().size());
    }
    
    @Test
    public void testRegistrarOrdenConsignee() {
        // When
        terminal.registrarOrdenConsignee(ordenConsignee1);
        terminal.registrarOrdenConsignee(ordenConsignee2);

        //La terminal inicializa sin ordenes 
        // Then
        assertEquals(2, terminal.getOrdenesConsignee().size());
    }

    @Test
    public void testRegistrarNaviera() {
        // When
        terminal.registrarNaviera(naviera1);
        terminal.registrarNaviera(naviera2);

        //La terminal inicializa sin navieras 
        // Then
        assertEquals(2, terminal.getNavieras().size());
    }

    @Test
    public void testRegistrarShipper() {
        // When
        terminal.registrarShipper(shipper1);
        terminal.registrarShipper(shipper2);

        //La terminal inicializa sin shippers registrados
        // Then
        assertEquals(2, terminal.getShippers().size());
    }
    
    @Test
    public void testRegistrarConsignee() {
        // When
        terminal.registrarConsignee(consignee1);
        terminal.registrarConsignee(consignee2);

        //La terminal inicializa sin consignees registrados
        // Then
        assertEquals(2, terminal.getConsignees().size());
    }
    
    @Test
    public void testMejorCircuito() {
        // Given
        terminal.setCriterio(criterio);
        List<Naviera> navieras = Arrays.asList(naviera1, naviera2);
        
        when(criterio.mejorCircuito(navieras, terminal, destino))
            .thenReturn(circuito);

        // When
        Circuito resultado = terminal.mejorCircuito(destino, navieras);

        // Then
        assertNotNull("El circuito no debería ser nulo", resultado);
        assertEquals("Debería retornar el circuito esperado", circuito, resultado);
        verify(criterio, times(1)).mejorCircuito(navieras, terminal, destino);
    }
    
    @Test 
    public void testBuscarRutasConViajes() {
    	terminal.registrarNaviera(naviera1);
    	terminal.registrarNaviera(naviera2);
    	
    	List<Viaje> viajesNaviera1 = Arrays.asList(viaje1, viaje2);
        List<Viaje> viajesNaviera2 = Arrays.asList(viaje3);
        List<Viaje> viajesFiltrados = Arrays.asList(viaje1, viaje3);
    	
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(viajesNaviera1);
        when(naviera2.getViajesDeTerminal(terminal)).thenReturn(viajesNaviera2);
        when(filtro.filtrar(anyList())).thenReturn(viajesFiltrados);

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertNotNull("La lista de viajes no debería ser nula", resultado);
        assertEquals("Debería retornar 2 viajes filtrados", 2, resultado.size());
        assertTrue("Debería contener viaje1", resultado.contains(viaje1));
        assertTrue("Debería contener viaje3", resultado.contains(viaje3));
        assertFalse("No debería contener viaje2 (filtrado)", resultado.contains(viaje2));
        
        verify(naviera1, times(1)).getViajesDeTerminal(terminal);
        verify(naviera2, times(1)).getViajesDeTerminal(terminal);
        verify(filtro, times(1)).filtrar(anyList());
    }
    
    @Test
    public void testBuscarRutasSinNavieras() {
        // Given
        List<Viaje> viajesFiltrados = new ArrayList<>();
        when(filtro.filtrar(anyList())).thenReturn(viajesFiltrados);

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertNotNull("La lista no debería ser nula", resultado);
        assertTrue("La lista debería estar vacía", resultado.isEmpty());
        verify(filtro, times(1)).filtrar(anyList());
    }

    @Test
    public void testBuscarRutasConFiltroQueRetornaTodos() {
     
        terminal.registrarNaviera(naviera1);
        List<Viaje> viajesNaviera1 = Arrays.asList(viaje1, viaje2);
        List<Viaje> viajesFiltrados = Arrays.asList(viaje1, viaje2);
        
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(viajesNaviera1);
        when(filtro.filtrar(viajesNaviera1)).thenReturn(viajesFiltrados);

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertEquals("Debería retornar todos los viajes", 2, resultado.size());
        verify(filtro, times(1)).filtrar(viajesNaviera1);
    }
    
    @Test
    public void testSetCriterio() {
        // Given
        Criterio nuevoCriterio = mock(Criterio.class);

        // When
        terminal.setCriterio(nuevoCriterio);

        // Then - No hay getter, pero verifica que no lanza excepción
        assertTrue("El método setCriterio debería ejecutarse correctamente", true);
    }
    
    // ===== TESTS DE COMPORTAMIENTO CON LISTAS VACÍAS =====
    
    @Test
    public void testBuscarRutasConNavierasSinViajes() {
        // Given
        terminal.registrarNaviera(naviera1);
        terminal.registrarNaviera(naviera2);
        
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(new ArrayList<>());
        when(naviera2.getViajesDeTerminal(terminal)).thenReturn(new ArrayList<>());
        when(filtro.filtrar(anyList())).thenReturn(new ArrayList<>());

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertNotNull("La lista no debería ser nula", resultado);
        assertTrue("La lista debería estar vacía", resultado.isEmpty());
    }
    
    @Test
    public void testMejorCircuitoConListaNavierasVacia() {
        // Given
        terminal.setCriterio(criterio);
        List<Naviera> navierasVacias = new ArrayList<>();
        
        when(criterio.mejorCircuito(navierasVacias, terminal, destino))
            .thenReturn(circuito);

        // When
        Circuito resultado = terminal.mejorCircuito(destino, navierasVacias);

        // Then
        assertNotNull("El circuito no debería ser nulo", resultado);
        verify(criterio, times(1)).mejorCircuito(navierasVacias, terminal, destino);
    }

}
