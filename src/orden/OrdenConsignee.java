package orden;

import cliente.Consignee;
import container.Container;
import empresaTransportista.Camion;
import viaje.Viaje;

public class OrdenConsignee extends Orden {
	private Consignee consignee;

	public OrdenConsignee(Container container, Camion camionAsignado, Viaje viaje, Consignee consignee) {
		super(container, camionAsignado, viaje);
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
	
	
=======
import java.util.List;

import cliente.Consignee;
import container.Container;
import empresaTransportista.Camion;
import servicio.Servicio;
import viaje.Viaje;

public class OrdenConsignee extends Orden {

	public OrdenConsignee(Consignee consignee, Camion camion, Viaje viaje, Container container, List<Servicio> servicios) {
		// TODO Auto-generated constructor stub
	}

	public String getEmailCliente() {
		// TODO Auto-generated method stub
		return null;
	}

}
