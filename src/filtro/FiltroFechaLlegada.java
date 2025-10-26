package filtro;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import viaje.Viaje;

public class FiltroFechaLlegada implements Filtro {
	
	LocalDate fecha;

	public FiltroFechaLlegada(LocalDate f1) {
		this.fecha = f1;
	}

	@Override
	public List<Viaje> filtrar(List<Viaje> listaDeViajes) {
		// TODO Auto-generated method stub
		return listaDeViajes.stream().filter(viaje -> viaje.tieneProximaFechaDeLlegada(this.fecha)).collect(Collectors.toList());
	}

	@Override
	public void agregar(Filtro filtro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Filtro filtro) {
		// TODO Auto-generated method stub
		
	}

}
