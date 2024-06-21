package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
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
    private Button btCancelar;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private PieChart pcTrabajos;

    @FXML
    void verEstadisticas() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MOSTRAR_ESTADISTICAS_MENSUALES);
        dpFecha.getEditor().clear();
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    public LocalDate leerMes() {
        return dpFecha.getValue();
    }

    public boolean rellenarPieChart(Map<TipoTrabajo, Integer> estadisticas) {
        int nTotalTrabajos = 0;
        for (Integer valor : estadisticas.values()) {
            nTotalTrabajos += valor;
        }
        if (nTotalTrabajos > 0) {
            pcTrabajos.setVisible(true);
            pcTrabajos.setTitle(String.format("ESTADÍSTICAS DE LOS TRABAJOS EN EL MES DE %s DEL %s", obtenerNombreMes(dpFecha.getValue().getMonthValue()).toUpperCase(), dpFecha.getValue().getYear()));
            pcTrabajos.getData().clear();
            for (Map.Entry<TipoTrabajo, Integer> entry : estadisticas.entrySet()) {
                if (entry.getValue() != null && entry.getValue() > 0) {
                    PieChart.Data data = new PieChart.Data(entry.getKey().toString(), entry.getValue());
                    pcTrabajos.getData().add(data);
                    aniadirTooltipAData(data, entry.getValue(), nTotalTrabajos);
                }
            }
        }
        return (nTotalTrabajos > 0);
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
    }

    public void limpiarDp(){
        dpFecha.setValue(null);
        dpFecha.getEditor().clear();
    }

    @FXML
    void initialize() {
        dpFecha.setEditable(false);
        pcTrabajos.setLabelsVisible(true);
        pcTrabajos.setVisible(false);
        btCancelar.setOnMouseEntered(e -> btCancelar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelar.setOnMouseExited(e -> btCancelar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
    }

}
