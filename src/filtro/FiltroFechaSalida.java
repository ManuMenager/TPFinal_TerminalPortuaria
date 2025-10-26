package filtro;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import viaje.Viaje;

public class FiltroFechaSalida implements Filtro {

	LocalDate fecha;
	
	public FiltroFechaSalida(LocalDate fechaFiltro) {
		this.fecha = fechaFiltro;
	}

	@Override
	public List<Viaje> filtrar(List<Viaje> listaDeViajes) {
		// TODO Auto-generated method stub
		return listaDeViajes.stream().filter(v -> v.tieneFechaSalida(fecha)).collect(Collectors.toList());
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
