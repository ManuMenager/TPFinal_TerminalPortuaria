package orden;

import java.time.LocalDate;

import cliente.Shipper;
import container.Container;
import empresaTransportista.Camion;
import viaje.Viaje;

public class OrdenShipper extends Orden {
	private Shipper shipper;
	private LocalDate turno;

	
	
	public OrdenShipper(Container container, Camion camionAsignado, Viaje viaje, Shipper shipper, LocalDate turno) {
		super(container, camionAsignado, viaje);
		this.shipper = shipper;
		this.turno = turno;
	}



	public Shipper getShipper() {
		return shipper;
	}



	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}



	public LocalDate getTurno() {
		return turno;
	}



	public void setTurno(LocalDate turno) {
		this.turno = turno;
	}



}
