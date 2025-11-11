package orden;

import java.util.List;

import cliente.Shipper;
import container.Container;
import empresaTransportista.Camion;
import empresaTransportista.Chofer;
import servicio.Servicio;
import viaje.Viaje;

public class OrdenShipper extends Orden {
	public OrdenShipper(Shipper shipper, Camion camion, Viaje viaje, Container container, List<Servicio> servicios) {
		// TODO Auto-generated constructor stub
	}

	public double montoTotalViaje();

	public Chofer choferAsignado() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNombreBuque() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmailCliente() {
		// TODO Auto-generated method stub
		return null;
	}
}
