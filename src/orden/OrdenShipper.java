package orden;

import java.time.LocalDateTime;
import java.util.ArrayList;
import cliente.Shipper;
import container.Container;
import empresaTransportista.Camion;
import empresaTransportista.Chofer;
import servicio.Servicio;

import viaje.Viaje;

public class OrdenShipper extends Orden {
	private Shipper shipper;
	private LocalDateTime turno;

	
	
	public OrdenShipper(Container container, Camion camionAsignado, Viaje viaje, Shipper shipper,
			LocalDateTime turno, ArrayList<Servicio> servicios) {
		super(container, camionAsignado, viaje, servicios);
		this.shipper = shipper;
		this.turno = turno;
	}



	public Shipper getShipper() {
		return shipper;
	}



	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}



	public LocalDateTime getTurno() {
		return turno;
	}



	public void setTurno(LocalDateTime turno) {
		this.turno = turno;
	}

	@Override
    public double montoTotal() {
		return  this.montoTotalServicios();
	}



	public String getEmailCliente() {
		// TODO Auto-generated method stub
		return this.shipper.getEmail();
	}



	public Chofer choferAsignado() {
		// TODO Auto-generated method stub
		return this.getCamionAsignado().getChofer();
	}
	

}
