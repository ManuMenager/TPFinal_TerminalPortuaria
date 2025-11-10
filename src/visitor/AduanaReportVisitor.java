package visitor;

import buque.Buque;
import orden.Orden;

public class AduanaReportVisitor implements ReportVisitor {
	private StringBuilder html = new StringBuilder();

    @Override
    public void visit(Buque buque) {
        html.append("<h1>Reporte Aduana</h1>")
            .append("<p>Buque: ").append(buque.getNombre()).append("</p>")
            .append("<p>Arribo: ").append(buque.getViaje().fechaLlegadaTerminalGestionada()).append("</p>")
            .append("<p>Partida: ").append(buque.getViaje().fechaSalidaTerminalGestionada()).append("</p>")
            .append("<ul>");
    }

    @Override
    public void visit(Orden orden) {
        html.append("<li>")
            .append(orden.getContainer().getTipo()).append(" - ").append(orden.getContainer().getId())// REVISAR
            .append("</li>");
    }

    public String generarHTML() {
        html.append("</ul>");
        return html.toString();
    }
}
