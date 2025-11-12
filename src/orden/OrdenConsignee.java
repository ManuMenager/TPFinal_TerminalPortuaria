package orden;

import java.util.ArrayList;

import cliente.Consignee;
import container.Container;
import empresaTransportista.Camion;
import servicio.Servicio;
import viaje.Viaje;

public class OrdenConsignee extends Orden {
	private Consignee consignee;

	public OrdenConsignee(Consignee consignee, Camion camionAsignado, Viaje viaje, Container container,
			ArrayList<Servicio> servicios) {
		super(container, camionAsignado, viaje, servicios);
		this.consignee = consignee;
	}

	public Consignee getConsignee() {
		return consignee;
	}

	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}
	@Override
    public double montoTotal() {
		return  this.montoTotalServicios() + this.montoTotalViaje();
	}

	public String getEmailCliente() {
		return this.consignee.getEmail();
	}
	

}
