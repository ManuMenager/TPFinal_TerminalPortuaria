package empresaTransportista;

import java.util.ArrayList;
import java.util.List;

public class EmpresaTransportista {
	
	List <Camion> camiones = new ArrayList<Camion>();

	public void registrarCamion(Camion camion) {
		// TODO Auto-generated method stub
		camiones.add(camion);
	}

	public boolean estaRegistradoElCamionDePatente(String patente) {
		// TODO Auto-generated method stub
		return camiones.stream().anyMatch(camion -> camion.getPatente() == patente);
	}

}
