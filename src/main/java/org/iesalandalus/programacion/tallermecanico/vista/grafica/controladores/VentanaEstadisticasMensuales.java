package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.time.LocalDate;
import java.util.Map;

public class VentanaEstadisticasMensuales extends Controlador {

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label lbInicial;

    @FXML
    private Label lbSinEstadisticas;

    @FXML
    private Label lbSinEstadisticasMesActual;

    @FXML
    private PieChart pcTrabajos;

    private boolean estadisticasMesActual;

    public boolean isEstadisticasMesActual() {
        return estadisticasMesActual;
    }

    @FXML
    void estadisticasMesActual() {
        estadisticasMesActual = true;
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MOSTRAR_ESTADISTICAS_MENSUALES);
    }

    @FXML
    void verEstadisticas() {
        if (dpFecha.getEditor().getText().isBlank()) {
            Dialogos.mostrarDialogoError("MOSTRAR ESTADÍSTICAS MENSUALES", "Error: Selecciona antes una fecha para mostrarte las estadísticas del mes seleccionado.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MOSTRAR_ESTADISTICAS_MENSUALES);
        }
        dpFecha.getEditor().clear();
    }

    public LocalDate leerMes() {
        System.out.println(dpFecha.getValue());
        return dpFecha.getValue();
    }

    public void rellenarPieChart(Map<TipoTrabajo, Integer> estadisticas) {
        int algunResultado = 0;
        for (Integer valor : estadisticas.values()) {
            algunResultado += valor;
        }
        if (algunResultado > 0) {
            lbInicial.setVisible(false);
            lbSinEstadisticas.setVisible(false);
            lbSinEstadisticasMesActual.setVisible(false);
            pcTrabajos.setVisible(true);
            pcTrabajos.setTitle(String.format("ESTADÍSTICAS DE LOS TRABAJOS EN EL MES DE %s DEL %s", obtenerNombreMes(dpFecha.getValue().getMonthValue()).toUpperCase(), dpFecha.getValue().getYear()));
            pcTrabajos.getData().clear();
            for (Map.Entry<TipoTrabajo, Integer> entry : estadisticas.entrySet()) {
                if (entry.getValue() != null && entry.getValue() > 0) {
                    PieChart.Data data = new PieChart.Data(entry.getKey().toString(), entry.getValue());
                    pcTrabajos.getData().add(data);
                    aniadirTooltipAData(data, entry.getValue(), algunResultado);
                }
            }
        } else {
            pcTrabajos.setVisible(false);
            lbInicial.setVisible(false);
            if (dpFecha.getValue() == null || (dpFecha.getValue().getMonth().equals(LocalDate.now().getMonth()) && (dpFecha.getValue().getYear() == LocalDate.now().getYear()))) {
                lbSinEstadisticasMesActual.setVisible(true);
                lbSinEstadisticas.setVisible(false);
            } else {
                lbSinEstadisticasMesActual.setVisible(false);
                lbSinEstadisticas.setVisible(true);
            }
        }
        estadisticasMesActual = false;
    }

    private String obtenerNombreMes(int numMes) {
        String mesEspaniol = null;
        switch (numMes) {
            case 1 -> mesEspaniol = "Enero";
            case 2 -> mesEspaniol = "Febrero";
            case 3 -> mesEspaniol = "Marzo";
            case 4 -> mesEspaniol = "Abril";
            case 5 -> mesEspaniol = "Mayo";
            case 6 -> mesEspaniol = "Junio";
            case 7 -> mesEspaniol = "Julio";
            case 8 -> mesEspaniol = "Agosto";
            case 9 -> mesEspaniol = "Septiembre";
            case 10 -> mesEspaniol = "Octubre";
            case 11 -> mesEspaniol = "Noviembre";
            case 12 -> mesEspaniol = "Diciembre";
        }
        return mesEspaniol;
    }

    private void aniadirTooltipAData(PieChart.Data data, int valor, int total) {
        double porcentaje = (valor * 100.0) / total;
        Tooltip tooltip = new Tooltip(String.format("%.2f%%", porcentaje));
        Tooltip.install(data.getNode(), tooltip);
    }

    public void limpiarVentana() {
        pcTrabajos.setLabelsVisible(true);
        pcTrabajos.setVisible(false);
        lbInicial.setVisible(true);
        lbSinEstadisticas.setVisible(false);
        lbSinEstadisticasMesActual.setVisible(false);
    }

    @FXML
    void initialize() {
        dpFecha.setEditable(false);
        pcTrabajos.setLabelsVisible(true);
        pcTrabajos.setVisible(false);
        lbSinEstadisticas.setVisible(false);
        lbSinEstadisticasMesActual.setVisible(false);
    }

}
