package buque;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import container.Container;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;
import visitor.ReportVisitor;
import visitor.Visitable;

public class Buque implements Visitable {
	private String nombre;
	private Viaje viaje;
	private GPS gps;
	private EstadoBuque estado = new Outbound();
	private List<Container> containers = new ArrayList<>();
	private TerminalPortuaria terminalGestionada;
	private Point posicion;

	public Buque(String nombre, TerminalPortuaria terminalGestionada, GPS gps, Viaje viaje) {
		this.nombre = nombre;
		this.terminalGestionada = terminalGestionada;
		this.gps = gps;
		this.viaje = viaje;
	}
	
	protected void setEstado(EstadoBuque e) {
		this.estado = e;
	}
	
	public void evaluarPosicion() {
		this.posicion = gps.enviarPosicion();
		estado.evaluarPosicion(this);
	}
	
	public double distanciaALaTerminal() {
    	double distanciaX = terminalGestionada.getPosicion().x - this.getPosicion().x;
    	double distanciaY = terminalGestionada.getPosicion().y - this.getPosicion().y;
        return Math.hypot(distanciaX, distanciaY);
    }
	
	public Point getPosicion() {
		return this.posicion;
	}
	
	public TerminalPortuaria getTerminalGestionada() {
		return this.terminalGestionada;
	}

	public void agregarContainer(Container c) {
		this.containers.add(c);
	}
	
	public void removerContainer(Container c) {
		this.containers.remove(c);
	}

	public void notificarArribo() {
		this.terminalGestionada.notificarConsignees();
	}
	
	public void notificarSalida() {
		this.terminalGestionada.notificarShippers();
	}
	
	public boolean puedePagarServicios() {
		return this.estado.puedePagarServicios();
	}
	
	public boolean puedeRegistrar() {
		return this.estado.puedeRegistrar();
	}
	
	public void iniciarTrabajos() {
		this.estado.iniciarTrabajos(this);
	}
	
	public void finalizarTrabajos() {
		this.estado.finalizarTrabajos(this);
	}
	
	public void depart() {
		this.estado.depart(this);
	}

	@Override
	public void accept(ReportVisitor visitor) {
		
	}
}
