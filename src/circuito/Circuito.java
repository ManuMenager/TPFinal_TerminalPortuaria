package circuito;

import java.util.ArrayList;
import java.util.List;

import terminalPortuaria.TerminalPortuaria;

public class Circuito {
	List<Tramo> tramos = new ArrayList<Tramo>();
	
	public void agregarTramo(Tramo t) {
		tramos.add(t);
	}

	public TerminalPortuaria terminalOrigen() {
		return tramos.getFirst().getOrigen();
	}
	
	public boolean contieneTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
		boolean contieneOrigen = tramos.stream().anyMatch(t -> t.getOrigen().equals(origen));
		boolean contieneDestino = tramos.stream().anyMatch(t -> t.getDestino().equals(destino));
		return contieneOrigen && contieneDestino;
	}

	public int tiempoDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) throws IllegalArgumentException{
		if (!contieneTerminales(origen, destino)) {
            throw new IllegalArgumentException("Alguna de las terminales no existe en el circuito");
        }
		int duracion = 0;
		boolean sumando = false;
		
		for (Tramo t : tramos) {
			if(t.getOrigen().equals(origen)) sumando = true; // Busco la terminal origen en la lista de tramos.
			if(sumando) duracion += t.getDuracion(); // Sumo el tiempo de cada tramo hasta la terminal destino.
			if(t.getDestino().equals(destino)) break; // Salgo del bucle porque ya llegué al destino.
		}
		
		return duracion;
	}

	public double precioFinal() {
		double precioFinal = 0.0;
		for (Tramo t : tramos) {
			precioFinal += t.getPrecio();
		}
		return precioFinal;
	}

}
