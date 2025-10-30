package servicio;

import orden.Orden;

public abstract class Servicio {
	private String nombreServicio;
	//private LocalDate fechaServicio; PREGUNTAR PARA QUE ES
	
	public Servicio(String nombreServicio) {
		super();
		this.nombreServicio = nombreServicio;
	}
	
	public abstract double costoServicio(Orden orden);

	
}
