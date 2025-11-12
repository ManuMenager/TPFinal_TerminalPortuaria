package orden;

import java.time.LocalDate;
import java.time.LocalDateTime;
>>>>>>> feature/Terminal1
import java.util.ArrayList;
import container.Container;
import empresaTransportista.Camion;
import empresaTransportista.Chofer;
import factura.Factura;
import servicio.Servicio;
import viaje.Viaje;
import visitor.ReportVisitor;
import visitor.Visitable;

public abstract class Orden {
	private ArrayList <Servicio> servicios = new ArrayList<Servicio>();
	private Container container;
	private Camion camionAsignado;
	private Viaje viaje;
	
	
	
	public Orden(Container container, Camion camionAsignado, Viaje viaje) {
		super();
		this.container = container;
		this.camionAsignado = camionAsignado;
		this.viaje = viaje;
		this.servicios = new ArrayList<>();
	}
	
	
	public Factura facturar() {
	    Factura factura = new Factura(this.servicios, this);
	    System.out.println(factura.desgloseDeServicios());
	    return factura;
	}
	
	
	
	
	//DATOS DE LA CARGA
	public Container getContainer() {
		return container;  
	}
	// public void facturarServicios() preguntar si se hace
	

	/*public Chofer choferAsignado{
		return this.getCamionAsignado().getConductor();//HACER
	}*/
	
	public void setCamionAsignado(Camion camionAsignado) {
		this.camionAsignado = camionAsignado;
	}
	public double montoTotalServicios() {
	
			return this.servicios.stream().mapToDouble( s -> s.costoServicio(this)  ).sum();
	}
	public double montoTotalViaje() {
		
		return this.viaje.precioFinal();
	}
	public abstract double montoTotal();
	public void agregarServicio(Servicio servicio) {
		servicios.add(servicio);
	}

	//GETTERS
	public ArrayList<Servicio> getServicios() {
		return servicios;
	}
	public Viaje getViaje() {
		return viaje;
	}
	
	public Camion getCamionAsignado() {
		return camionAsignado;
	}
	
	//SETTERS
	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}
	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	
	public void setContainer(Container container) {
		this.container = container;
	}

	public String getNombreBuque() {
	// TODO Auto-generated method stub
		return null;
	}
	public LocalDateTime getHorarioTurno() {
		// TODO Auto-generated method stub
		return null;
	}
}

