package terminalPortuaria;

import java.awt.Point;

import buque.Buque;
import java.lang.reflect.AccessFlag.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import circuito.Circuito;
import cliente.Consignee;
import cliente.Shipper;
import criterio.Criterio;
import empresaTransportista.EmpresaTransportista;
import filtro.Filtro;
import naviera.Naviera;
import orden.OrdenConsignee;
import orden.OrdenShipper;
import viaje.Viaje;

public class TerminalPortuaria {

	private String nombre;
	private Point ubicacion;
	private Criterio criterio;
	private List<EmpresaTransportista> empresasTransportistas = new ArrayList<EmpresaTransportista>();
	private List<OrdenConsignee> ordenesImportacion = new ArrayList<OrdenConsignee>();
	private List<OrdenShipper> ordenesExportacion = new ArrayList<OrdenShipper>();
	private List<Consignee> importadores = new ArrayList<Consignee>();
	private List<Shipper> exportadores = new ArrayList<Shipper>();
	private List<Naviera> navieras = new ArrayList<Naviera>();
	
	public TerminalPortuaria(String nombre, Point ubicacion) {
		this.nombre = nombre;
		this.ubicacion = ubicacion;
	}
	
	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	
	public void registrarEmpresa(EmpresaTransportista empresa) {
		this.empresasTransportistas.add(empresa);
	}
	
	public List<EmpresaTransportista> getEmpresasTransportistas() {
		return this.empresasTransportistas;
	}
	
	public void registrarOrdenShipper(OrdenShipper orden) {
		this.ordenesExportacion.add(orden);
	}
	
	public List<OrdenShipper> getOrdenesShipper(){
		return this.ordenesExportacion;
	}
	
	public void registrarOrdenConsignee(OrdenConsignee orden) {
		this.ordenesImportacion.add(orden);
	}
	
	public List<OrdenConsignee> getOrdenesConsignee(){
		return this.ordenesImportacion;
	}
	
	public void registrarNaviera(Naviera naviera) {
		this.navieras.add(naviera);
	}
	
	public List<Naviera> getNavieras(){
		return this.navieras;
	}
	
	public void registrarShipper(Shipper shipper) {
		this.exportadores.add(shipper);
	}
	
	public List<Shipper> getShippers(){
		return this.exportadores;
	}
	
	public void registrarConsignee(Consignee consignee) {
		this.importadores.add(consignee);
	}
	
	public List<Consignee> getConsignees(){
		return this.importadores;
	}
	
	//Dependendiendo del criterio retorna el mejor circuito para llegar a ese destino.
	public Circuito mejorCircuito(TerminalPortuaria this, TerminalPortuaria destino, List<Naviera> navieras) {
		return this.criterio.mejorCircuito(navieras, this, destino);
	}
	
	public List<Viaje> buscarRutas(Filtro filtro){
		List<Viaje> viajes = this.getViajes();
		return filtro.filtrar(viajes);
	}

	private List<Viaje> getViajes() {
		return this.navieras.stream()
				.flatMap(n->n.getViajesDeTerminal(this).stream())
				.collect(Collectors.toList());
	}
	
	public void notificarConsignees() {
		// TODO Auto-generated method stub
		
	}

	public void notificarShippers() {
		// TODO Auto-generated method stub
		
	}

	public Point getPosicion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void iniciarTrabajos(Buque b) {
		// TODO Auto-generated method stub
		
	}

	public void finalizarTrabajos(Buque b) {
		// TODO Auto-generated method stub
		
	}
}