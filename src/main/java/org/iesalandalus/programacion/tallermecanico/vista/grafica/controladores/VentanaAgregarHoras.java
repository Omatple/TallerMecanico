package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class VentanaAgregarHoras extends Controlador {

    @FXML
    private TextField tfHoras;

    public int getHoras() {
        int horas;
        if (tfHoras.getText().isBlank()) {
            horas = 0;
        } else {
            horas = Integer.parseInt(tfHoras.getText());
        }
        return horas;
    }

    @FXML
    void aceptar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.ANADIR_HORAS_TRABAJO);
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    void validarHoras(String nuevo) {
        if (!nuevo.matches("^[1-9]{1}[0-9]*$")) {
            tfHoras.clear();
        }
    }

    @FXML
    void initialize() {
        tfHoras.textProperty().addListener((observable, oldValue, newValue) -> validarHoras(newValue));
    }
}
