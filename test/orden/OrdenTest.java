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
import empresaTransportista.Chofer;
import factura.Factura;
import servicio.Servicio;
import viaje.Viaje;

class OrdenTest {

    private Container containerMock;
    private Camion camionMock;
    private Viaje viajeMock;
    private Servicio servicio1;
    private Servicio servicio2;
    private ArrayList<Servicio> listaServicios;

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

        listaServicios = new ArrayList<>();
        listaServicios.add(servicio1);
        listaServicios.add(servicio2);
    }

    @Test
    void testFacturar_OrdenConsignee() {
        Consignee consigneeMock = mock(Consignee.class);
        when(consigneeMock.getEmail()).thenReturn("cliente@importador.com");

        OrdenConsignee orden = new OrdenConsignee(
            consigneeMock,
            camionMock,
            viajeMock,
            containerMock,
            listaServicios
        );

        Factura factura = orden.facturar();

        assertNotNull(factura);
        assertTrue(factura.desgloseDeServicios().contains("Lavado"));
        assertTrue(factura.desgloseDeServicios().contains("Almacenamiento"));
        assertTrue(factura.desgloseDeServicios().contains("Costo total del viaje"));
        assertTrue(factura.desgloseDeServicios().contains("TOTAL A PAGAR"));

        // Verifica monto total esperado: 100 + 200 + 500
        double esperado = 100 + 200 + 500;
        assertEquals(esperado, orden.montoTotal(), 0.001);
        assertEquals("cliente@importador.com", orden.getEmailCliente());
    }

    @Test
    void testFacturar_OrdenShipper() {
        Shipper shipperMock = mock(Shipper.class);
        when(shipperMock.getEmail()).thenReturn("cliente@exportador.com");

        OrdenShipper orden = new OrdenShipper(
            containerMock,
            camionMock,
            viajeMock,
            shipperMock,
            LocalDate.now(),
            listaServicios
        );

        Factura factura = orden.facturar();

        assertNotNull(factura);
        assertTrue(factura.desgloseDeServicios().contains("Lavado"));
        assertTrue(factura.desgloseDeServicios().contains("Subtotal servicios"));
        assertTrue(factura.desgloseDeServicios().contains("TOTAL A PAGAR"));

        // Verifica que solo pague servicios (no viaje)
        assertEquals(300.0, orden.montoTotal(), 0.001);
        assertEquals("cliente@exportador.com", orden.getEmailCliente());
    }

    @Test
    void testChoferAsignado_OrdenShipper() {
        Chofer choferMock = mock(Chofer.class);
        when(camionMock.getChofer()).thenReturn(choferMock);

        Shipper shipperMock = mock(Shipper.class);
        OrdenShipper orden = new OrdenShipper(
            containerMock,
            camionMock,
            viajeMock,
            shipperMock,
            LocalDate.now(),
            listaServicios
        );

        assertEquals(choferMock, orden.choferAsignado());
    }

    @Test
    void testMontoTotalServiciosYViaje() {
        Consignee consigneeMock = mock(Consignee.class);
        OrdenConsignee ordenCons = new OrdenConsignee(
            consigneeMock,
            camionMock,
            viajeMock,
            containerMock,
            listaServicios
        );

        Shipper shipperMock = mock(Shipper.class);
        OrdenShipper ordenShip = new OrdenShipper(
            containerMock,
            camionMock,
            viajeMock,
            shipperMock,
            LocalDate.now(),
            listaServicios
        );

        assertEquals(800.0, ordenCons.montoTotal(), 0.001); // 100 + 200 + 500
        assertEquals(300.0, ordenShip.montoTotal(), 0.001); // solo servicios
    }
}
