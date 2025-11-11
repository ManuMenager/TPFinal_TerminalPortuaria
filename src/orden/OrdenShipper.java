package orden;

import java.time.LocalDate;
=======
import java.util.List;
>>>>>>> feature/Terminal1

import cliente.Shipper;
import container.Container;
import empresaTransportista.Camion;
<<<<<<< HEAD
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



=======
import empresaTransportista.Chofer;
import servicio.Servicio;
import viaje.Viaje;

public class OrdenShipper extends Orden {
	public OrdenShipper(Shipper shipper, Camion camion, Viaje viaje, Container container, List<Servicio> servicios) {
		// TODO Auto-generated constructor stub
	}

	public Chofer choferAsignado() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmailCliente() {
		// TODO Auto-generated method stub
		return null;
	}

}
