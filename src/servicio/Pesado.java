package servicio;

import orden.Orden;

public class Pesado extends Servicio {
	private int costoPesaje;
	
	public Pesado(String nombreServicio, int costoPesaje) {
		super(nombreServicio);
		this.costoPesaje = costoPesaje;
	}
	
	@Override 
	public double costoServicio(Orden orden) {
		return costoPesaje;
	}
	public int getCostoPesaje() {
		return costoPesaje;
	}
	public void setCostoPesaje(int costoPesaje) {
		this.costoPesaje = costoPesaje;
	}	
}


