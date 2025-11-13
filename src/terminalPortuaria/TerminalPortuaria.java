package terminalPortuaria;

import java.awt.Point;

import buque.Buque;
import java.lang.reflect.AccessFlag.Location;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import circuito.Circuito;
import cliente.Consignee;
import cliente.Shipper;
import container.Container;
import criterio.Criterio;
import empresaTransportista.Camion;
import empresaTransportista.EmpresaTransportista;
import factura.Factura;
import filtro.Filtro;
import naviera.Naviera;
import orden.Orden;
import orden.OrdenConsignee;
import orden.OrdenShipper;
import servicio.Servicio;
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
	private CorreoEmail correo;
	private Set<Circuito> circuitos = new HashSet<Circuito>();
	
	public TerminalPortuaria(String nombre, Point ubicacion) {
		this.nombre = nombre;
		this.ubicacion = ubicacion;
	}
	
	public void setCorreo(CorreoEmail correo) {
		this.correo = correo;
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
	
	public String verificarTurno(OrdenShipper orden) { //Entrega Terrestre de Carga
		if(this.verificarHorario(orden) && this.verificarCamion(orden) && this.verificarChofer(orden)) {
			return "Aprobado";
		}
		else {
			return "Rechazado";
		}
	}
	
	private boolean verificarChofer(OrdenShipper orden) {
		// TODO Auto-generated method stub
		return empresasTransportistas.stream().anyMatch(e -> e.estaRegistradoElChofer(orden.choferAsignado()));
	}

	private boolean verificarCamion(Orden orden) {
		// TODO Auto-generated method stub
		return empresasTransportistas.stream().anyMatch(e -> e.estaRegistradoElCamion(orden.getCamionAsignado()));
	}

	private boolean verificarHorario(OrdenShipper orden) {
		// TODO Auto-generated method stub
		LocalDateTime horaActual = LocalDateTime.now();
		return orden.getTurno().isBefore(horaActual.plusHours(3)); //Devuelve true cuando no se paso de las 3 horas
	}
	
	public void crearOrdenShipper(Shipper shipper, Camion camion, Viaje viaje,
			Container container, ArrayList<Servicio> servicios, LocalDateTime turno) {
		
		OrdenShipper orden = new OrdenShipper(container,camion,viaje,shipper,turno,servicios);
		this.registrarOrdenShipper(orden);
	}
	
	public void crearOrdenConsignee(Consignee consignee, Camion camion, Viaje viaje,
			Container container, ArrayList<Servicio> servicios) {
		
		OrdenConsignee orden = new OrdenConsignee(consignee,camion,viaje,container,servicios);
		this.registrarOrdenConsignee(orden);
	}

	public void notificarConsignees(Buque b) {
		List<OrdenConsignee> ordenesFiltradas = this.ordenesConsigneeFiltradasPorBuque(b);
		for (OrdenConsignee orden : ordenesFiltradas) {
			String emailConsignee = orden.getEmailCliente();
			this.correo.enviarCorreo(emailConsignee, "Buque ingresando a terminal");
		}
	}
	
	public List<OrdenConsignee> ordenesConsigneeFiltradasPorBuque(Buque b){
		return this.getOrdenesConsignee()
                .stream()
                .filter(orden -> orden.getNombreBuque().equals(b.getNombre()))
                .collect(Collectors.toList());
	}

	public void notificarShippers(Buque b) {
		List<OrdenShipper> ordenesFiltradas = this.ordenesShipperFiltradasPorBuque(b);
		for (OrdenShipper orden : ordenesFiltradas) {
			String emailShipper = orden.getEmailCliente();
			this.correo.enviarCorreo(emailShipper, "Buque saliendo de la terminal");
		}
	}
	
	public List<OrdenShipper> ordenesShipperFiltradasPorBuque(Buque b){
		return this.getOrdenesShipper()
                .stream()
                .filter(orden -> orden.getNombreBuque().equals(b.getNombre()))
                .collect(Collectors.toList());
	}
	
	
	
	public void enviarFacturas(Buque b) {
		List<OrdenConsignee> ordenesConsignee = this.ordenesConsigneeFiltradasPorBuque(b);
		List<OrdenShipper> ordenesShipper = this.ordenesShipperFiltradasPorBuque(b);
		
		List<Orden> ordenes = new ArrayList<>();
		
		ordenes.addAll(ordenesConsignee);
		ordenes.addAll(ordenesShipper);
				
		for (Orden orden : ordenes) {
			String emailCliente = orden.getEmailCliente();
			Factura facturaCliente = orden.facturar();
			this.correo.enviarCorreoConFactura(emailCliente, "Facturacion por servicios", facturaCliente);
		}
	}
	
	public void registrarCircuitos() {
		// Registrar los circuitos marítimos que incluyen a la terminal, considerando las fechas
		//de las distintas programaciones de viajes (fechas de arribo a la propia terminal y a
		//		todas las demás de los circuitos que la incluyen)7
		
		List<Viaje> viajes = this.getViajes();
		for (Viaje viaje : viajes) {
			Circuito circuitoARegistrar = viaje.getCircuito();
			circuitos.add(circuitoARegistrar);
		}
	}
	
	public Set<Circuito> getCircuitos() {
		return this.circuitos;
	}
	
	public List<Integer> tiempoHasta(Naviera n, TerminalPortuaria destino) {
		return n.tiempoDesdeHasta(this, destino);
	}
	
	public Optional <LocalDateTime> proximaFechaHacia(TerminalPortuaria destino) {
		return this.navieras
	            .stream()
	            .flatMap(n -> n.proximaFechaHacia(destino).stream()) 
	            .min(LocalDateTime::compareTo);
	}

	public Point getPosicion() {
		// TODO Auto-generated method stub
		return this.ubicacion;
	}
	
	public void iniciarTrabajos(Buque b) {
		b.iniciarTrabajos();
	}

	public void finalizarTrabajos(Buque b) {
		b.finalizarTrabajos();
	}
}