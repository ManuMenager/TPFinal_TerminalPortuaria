package empresaTransportista;

import java.util.ArrayList;
import java.util.List;

public class Camion {
	
	private String patente;
	List<Chofer> conductores = new ArrayList<Chofer>();

	public Camion(String patente) {
		// TODO Auto-generated constructor stub
		this.patente = patente;
	}

	public String getPatente() {
		// TODO Auto-generated method stub
		return patente;
	}

	public void registrarChofer(Chofer chofer) {
		// TODO Auto-generated method stub
		conductores.add(chofer);
	}

	public boolean estaRegistradoElChofer(Chofer chofer1) {
		// TODO Auto-generated method stub
		return conductores.stream().anyMatch(chofer -> chofer.getDni() == chofer1.getDni());
	}

}
