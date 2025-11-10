package visitor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import buque.Buque;
import container.Container;
import orden.Orden;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

import java.time.LocalDateTime;


class VisitorTest {


 
	    Buque buque;
	    Orden orden;
	    Viaje viajeMock;
	    Container containerMock;
	    TerminalPortuaria terminalMock;

	    @BeforeEach
	    void setUp() {
	        // Creamos mocks
	        viajeMock = mock(Viaje.class);
	        containerMock = mock(Container.class);
	        terminalMock = mock(TerminalPortuaria.class);

	        // Configuramos el viaje
	        when(viajeMock.fechaLlegadaTerminalGestionada()).thenReturn(LocalDateTime.of(2025, 11, 10, 8, 0));
	        when(viajeMock.fechaSalidaTerminalGestionada()).thenReturn(LocalDateTime.of(2025, 11, 10, 18, 0));

	        // Creamos el buque real pero le inyectamos el viaje mockeado
	        buque = new Buque("Evergreen", terminalMock, null);
	        buque.setViaje(viajeMock);
	        buque.agregarContainer(containerMock);

	        // Configuramos container mock
	        when(containerMock.getTipo()).thenReturn("Dry");
	        when(containerMock.getId()).thenReturn("C123");

	        // Creamos una orden real
	        orden = new Orden(containerMock, null, viajeMock);
	    }

	    @Test
	    void testMuelleReportVisitorGeneraReporteCorrecto() {
	        MuelleReportVisitor visitor = new MuelleReportVisitor();

	        buque.accept(visitor);

	        String reporte = visitor.generarReporte();

	        assertTrue(reporte.contains("Buque: Evergreen"));
	        assertTrue(reporte.contains("Arribo: 2025-11-10T08:00"));
	        assertTrue(reporte.contains("Partida: 2025-11-10T18:00"));
	        assertTrue(reporte.contains("Containers operados: 1"));
	    }

	    @Test
	    void testAduanaReportVisitorGeneraHtmlCorrecto() {
	        AduanaReportVisitor visitor = new AduanaReportVisitor();

	        buque.accept(visitor);
	        orden.accept(visitor);

	        String html = visitor.generarHTML();

	        assertTrue(html.contains("<h1>Reporte Aduana</h1>"));
	        assertTrue(html.contains("Buque: Evergreen"));
	        assertTrue(html.contains("<li>Dry - C123</li>"));
	        assertTrue(html.endsWith("</ul>"));
	    }

	    @Test
	    void testQueBuqueLlamaAlVisitorCorrecto() {
	        ReportVisitor visitorMock = mock(ReportVisitor.class);

	        buque.accept(visitorMock);

	        verify(visitorMock, times(1)).visit(buque);
	        verify(visitorMock, never()).visit(orden);
	    }

	    @Test
	    void testQueOrdenLlamaAlVisitorCorrecto() {
	        ReportVisitor visitorMock = mock(ReportVisitor.class);

	        orden.accept(visitorMock);

	        verify(visitorMock, times(1)).visit(orden);
	        verify(visitorMock, never()).visit(buque);
	    }   
	
}