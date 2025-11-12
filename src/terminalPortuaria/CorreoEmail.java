package terminalPortuaria;

import factura.Factura;

public interface CorreoEmail {

	public void enviarCorreo(String mail, String mensaje);
	public void enviarCorreoConFactura(String mail,String mensaje,Factura factura);
}
