package naviera;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import buque.Buque;
import circuito.Circuito;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

public class Naviera {
	private List<Circuito> circuitos = new ArrayList<>();
	private List<Buque> buques;
	private List<Viaje> viajes = new ArrayList<>();
	
	public Naviera(List<Buque> b) {
		this.buques = b;
	}
	
	public void registrarCircuito(Circuito c) {
		this.circuitos.add(c);
	}
	
	public void registrarViaje(Viaje v) {
		viajes.add(v);
	}
	
	public Optional<LocalDateTime> proximaFechaHacia(TerminalPortuaria destino) {
		LocalDateTime ahora = LocalDateTime.now();
		Optional<LocalDateTime> proximaFecha = viajes.stream()
											    .map(v -> v.proximaFechaHacia(destino))
											    .filter(Objects::nonNull)
											    .filter(f -> f.isAfter(ahora))
											    .min(Comparator.comparing(f -> Duration.between(ahora, f)));
		return proximaFecha;
	}
	
	public boolean recorreDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		return circuitos.stream().anyMatch(v -> v.recorreDesdeHasta(origen, destino));
	}
	
	public List<Integer> escalasDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		if (recorreDesdeHasta(origen, destino)) {
			return circuitos.stream()
					.map(c -> c.escalasDesdeHasta(origen, destino))
			        .filter(escalas -> escalas != -1)
			        .collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException("Alguna de las terminales no existe en el circuito o el circuito no recorre esas terminales en ese orden."); 
		}
	}
	
	public List<Double> preciosDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		if (recorreDesdeHasta(origen, destino)) {
			return circuitos.stream()
					.map(c -> c.precioDesdeHasta(origen, destino))
					.filter(precios -> precios != -1)
					.collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException("Alguna de las terminales no existe en el circuito o el circuito no recorre esas terminales en ese orden.");
		}
	}
}