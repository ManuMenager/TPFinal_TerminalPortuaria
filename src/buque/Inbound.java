package buque;

public class Inbound extends EstadoBuque {

	@Override
	public void evaluarPosicion(Buque b) {
		if (b.distanciaALaTerminal() == 0.0 && !hayProblemasClimaticos()) {
			notificarArribo(b);
			b.setEstado(new Arrived());
		} else if (hayProblemasClimaticos()){
			b.setEstado(new Outbound());
		}
	}
	
	boolean hayProblemasClimaticos() {
		return true;
	}
	
	private void notificarArribo(Buque b) {
		b.notificarArribo();
	}
}