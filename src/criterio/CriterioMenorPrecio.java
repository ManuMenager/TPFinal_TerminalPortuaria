package criterio;

import java.util.Comparator;
import java.util.List;
import circuito.Circuito;
import terminalPortuaria.TerminalPortuaria;

public class CriterioMenorPrecio extends Criterio{

	public Circuito mejorCircuito(List<Circuito> circuitos, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		
		if (circuitos.isEmpty()) {
			throw new IllegalArgumentException("la lista de circuito esta vacia");
		}
		
		List<Circuito> filtrados = this.filtrarCircuitos(circuitos, terminalGestionada, puertoDestino);
		return this.menorPrecio(filtrados, terminalGestionada, puertoDestino);
	}

	private Circuito menorPrecio(List<Circuito> filtrados, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		
		if (filtrados.isEmpty()) {
			throw new IllegalArgumentException(
					"No hay circuitos que recorran desde " + terminalGestionada + " hasta " + puertoDestino);
		}
		
		return filtrados.stream()
			    .min(Comparator.comparing(circuito -> circuito.precioDesdeHasta(terminalGestionada, puertoDestino)))
			    .orElse(null);	}

}
