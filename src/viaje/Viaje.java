package viaje;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import buque.Buque;
import circuito.Circuito;
import circuito.Tramo;
import terminalPortuaria.TerminalPortuaria;

public class Viaje {
	private Buque buque;
	private Circuito circuito;
	private LocalDateTime fechaSalida;
	private LocalDateTime fechaSalidaTerminalGestionada;
	private TerminalPortuaria terminalGestionada;
	private Map<TerminalPortuaria, LocalDateTime> cronograma = new HashMap<TerminalPortuaria, LocalDateTime>();;
	
	
	public Viaje(Buque buque, Circuito circuito, LocalDateTime fechaSalida, TerminalPortuaria terminalGestionada) {
		this.buque = buque;
		this.circuito = circuito;
		this.fechaSalida = fechaSalida;
		this.terminalGestionada = terminalGestionada;
		this.cronograma = crearCronograma(circuito);
		this.fechaSalidaTerminalGestionada = fechaSalidaTerminalGestionada();
	}
	
	private LocalDateTime fechaSalidaTerminalGestionada() {
		return this.cronograma.get(terminalGestionada);
	}

	private Map<TerminalPortuaria, LocalDateTime> crearCronograma(Circuito circuito) {
		Map<TerminalPortuaria, LocalDateTime> nuevoCronograma = new HashMap<>();
		LocalDateTime fechaActual = this.fechaSalida;
		TerminalPortuaria terminalActual = circuito.terminalOrigen();
		for (Tramo tramo : circuito.getTramos()) {
			fechaActual = fechaActual.plusHours(tramo.getDuracion());
			nuevoCronograma.put(tramo.getOrigen(), fechaActual);
			terminalActual = tramo.getDestino();
		}
		return nuevoCronograma;
	}
	
	public LocalDateTime proximaFechaHacia(TerminalPortuaria terminal) {
		if (this.circuito.contieneTerminal(terminal)) {
			return this.fechaSalidaTerminalGestionada;
		} else {
			return null;
		}
	}
	
}
