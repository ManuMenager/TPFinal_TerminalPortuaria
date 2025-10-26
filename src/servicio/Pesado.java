package servicio;

public class Pesado extends Servicio {
	private int costoPesaje;
	@Override 
	public double costoServicio() {
		return costoPesaje;
	}
}


