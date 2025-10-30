package criterio;

import java.util.List;
import java.util.stream.Collectors;

import circuito.Circuito;
import terminalPortuaria.TerminalPortuaria;

public abstract class Criterio {

	
	public abstract Circuito mejorCircuito(List<Circuito> circuitos, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino);
	
	protected List<Circuito> filtrarCircuitos(List<Circuito> circuitos, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		return circuitos.stream().filter(c -> c.recorreDesdeHasta(terminalGestionada, puertoDestino)).collect(Collectors.toList());
	}
}
