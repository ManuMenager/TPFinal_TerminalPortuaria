package servicio;import java.time.LocalDate;
import java.time.Period;

import orden.Orden;

public class Almacenamiento extends Servicio {
	private int costoPorDia;
	
	public Almacenamiento(String nombreServicio, int costoPorDia) {
		super(nombreServicio);
		this.costoPorDia = costoPorDia;
	}

	@Override 
	public double costoServicio(Orden orden) {
		Period periodo = Period.between(orden.getViaje().fechaLlegadaTerminalGestionada(), LocalDate.now());
        int dias = periodo.getDays();
        return dias * costoPorDia;
	}

	public int getCostoPorDia() {
		return costoPorDia;
	}

	public void setCostoPorDia(int costoPorDia) {
		this.costoPorDia = costoPorDia;
	}

}
