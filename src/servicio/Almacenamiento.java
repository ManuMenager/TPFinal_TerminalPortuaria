package servicio;import orden.Orden;

public class Almacenamiento extends Servicio {
	private int costoPorDia;
	
	public Almacenamiento(String nombreServicio, int costoPorDia) {
		super(nombreServicio);
		this.costoPorDia = costoPorDia;
	}

	@Override 
	public double costoServicio(Orden orden) {
		if(orden.getViaje().fechaSalidaTerminalGestionada() != localDate now);
	}


}
