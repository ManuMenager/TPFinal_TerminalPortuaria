package orden;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cliente.Consignee;
import cliente.Shipper;
import container.Container;
import empresaTransportista.Camion;
import factura.Factura;
import servicio.Servicio;
import viaje.Viaje;

class OrdenTest {
    
    private Container containerMock;
    private Camion camionMock;
    private Viaje viajeMock;
    private Servicio servicio1;
    private Servicio servicio2;

    @BeforeEach
    void setUp() {
        containerMock = mock(Container.class);
        camionMock = mock(Camion.class);
        viajeMock = mock(Viaje.class);

        servicio1 = mock(Servicio.class);
        servicio2 = mock(Servicio.class);

        when(servicio1.getNombreServicio()).thenReturn("Lavado");
        when(servicio2.getNombreServicio()).thenReturn("Almacenamiento");
        when(servicio1.getFechaServicio()).thenReturn(LocalDate.of(2025, 11, 1));
        when(servicio2.getFechaServicio()).thenReturn(LocalDate.of(2025, 11, 2));
        when(servicio1.costoServicio(any())).thenReturn(100.0);
        when(servicio2.costoServicio(any())).thenReturn(200.0);

        when(viajeMock.precioFinal()).thenReturn(500.0);
    }

    @Test
    void testFacturar_OrdenConsignee() {
        Consignee consigneeMock = mock(Consignee.class);
        OrdenConsignee orden = new OrdenConsignee(containerMock, camionMock, viajeMock, consigneeMock);
        orden.agregarServicio(servicio1);
        orden.agregarServicio(servicio2);

        Factura factura = orden.facturar();

        assertNotNull(factura);
        assertEquals(5, factura.getConceptos().size()); 
        // 2 servicios + subtotal + viaje + total

        double esperado = 100 + 200 + 500; 
        assertTrue(factura.desgloseDeServicios().contains("TOTAL A PAGAR"));
        assertTrue(factura.desgloseDeServicios().contains("Lavado"));
        assertTrue(factura.desgloseDeServicios().contains("Almacenamiento"));
        assertTrue(factura.desgloseDeServicios().contains("Costo total del viaje"));
        assertTrue(factura.desgloseDeServicios().contains(String.format("$%.2f", esperado)));
    }

    @Test
    void testFacturar_OrdenShipper() {
        Shipper shipperMock = mock(Shipper.class);
        OrdenShipper orden = new OrdenShipper(containerMock, camionMock, viajeMock, shipperMock, LocalDate.now());
        orden.agregarServicio(servicio1);

        // En este caso montoTotal() no incluye viaje
        when(viajeMock.precioFinal()).thenReturn(500.0);

        Factura factura = orden.facturar();

        assertNotNull(factura);
        assertTrue(factura.desgloseDeServicios().contains("Lavado"));
        assertTrue(factura.desgloseDeServicios().contains("Subtotal servicios"));
        assertTrue(factura.desgloseDeServicios().contains("TOTAL A PAGAR"));
    }

    @Test
    void testMontoTotal_Ordenes() {
        Consignee consigneeMock = mock(Consignee.class);
        OrdenConsignee ordenCons = new OrdenConsignee(containerMock, camionMock, viajeMock, consigneeMock);
        ordenCons.agregarServicio(servicio1);
        ordenCons.agregarServicio(servicio2);

        Shipper shipperMock = mock(Shipper.class);
        OrdenShipper ordenShip = new OrdenShipper(containerMock, camionMock, viajeMock, shipperMock, LocalDate.now());
        ordenShip.agregarServicio(servicio1);
        ordenShip.agregarServicio(servicio2);

        assertEquals(800.0, ordenCons.montoTotal()); // incluye viaje (500)
        assertEquals(300.0, ordenShip.montoTotal()); // solo servicios
    }
}
