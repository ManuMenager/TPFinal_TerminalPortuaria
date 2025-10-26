package orden;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import empresaTransportista.Camion;
import empresaTransportista.Chofer;
import servicio.Servicio;
import viaje.Viaje;
import visitor.ReportVisitor;

public class Orden {
	private LocalDate turno;
	private Camion camionAsignado;
//	private ArrayList <Servicio> servicios = new ArrayList<Servicio>();
	private Viaje viaje;
	
	public Chofer choferAsignado{
		return camionAsignado.getConductor();
	}
	public void facturarServicios(List<Servicio> servicios,viaje) {
		
	}
	public void accept(ReportVisitor visitor) {
		visitor.visitOrden(this);
	}
	public double montoTotal() {
		
	}
	public double montoTotalServicios() {
		
	}
}
