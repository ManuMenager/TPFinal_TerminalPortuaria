package buque;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import container.Container;

import java.awt.Point;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

public class BuqueTest {
    Buque buque;
    GPS gps;
    TerminalPortuaria terminalGestionada;
    Viaje viaje;
    Container container;
    Inbound inbound;

    @BeforeEach
    public void setUp() {
        terminalGestionada = mock(TerminalPortuaria.class);
        gps = mock(GPS.class);
        viaje = mock(Viaje.class); 
        container = mock(Container.class);
        inbound = mock(Inbound.class);

        buque = new Buque("El Perla", terminalGestionada, gps, viaje);
        
        when(terminalGestionada.getPosicion()).thenReturn(new Point(0, 0));
    }

    @Test
    public void buqueLejosDeTerminalTest() {
        when(gps.enviarPosicion()).thenReturn(new Point(60, 0));
        
        buque.evaluarPosicion();
        
        assertTrue(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueLlegandoATerminalTest() {
    	when(gps.enviarPosicion()).thenReturn(new Point(40, 0));
    	
        buque.evaluarPosicion();

        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }

    @Test
    public void buqueArribaEnTerminal() {
        when(gps.enviarPosicion()).thenReturn(new Point(0, 0));
        
        buque.evaluarPosicion();
        buque.evaluarPosicion();
        
        verify(terminalGestionada).notificarConsignees();
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueNoArribaPorProblemasClimaticosTest() {
    	when(gps.enviarPosicion()).thenReturn(new Point(0, 0));
    	
    	buque.evaluarPosicion();
    	buque.evaluarPosicion();
        
        verify(terminalGestionada, times(0)).notificarConsignees();  
        assertTrue(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueRealizaTrabajosTest() {
        buque.iniciarTrabajos();
        
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueTerminaTrabajosTest() {
        buque.finalizarTrabajos();

        verify(terminalGestionada).finalizarTrabajos(buque);
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueSeRetiraDeTerminalTest() {
    	when(gps.enviarPosicion()).thenReturn(new Point(2, 0));
    	
        buque.depart();
        
        assertTrue(buque.puedePagarServicios());
        assertFalse(buque.puedeRegistrar());
        
        buque.evaluarPosicion();

        verify(terminalGestionada).notificarShippers();
        assertTrue(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
}