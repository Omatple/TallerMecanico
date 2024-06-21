package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;

public class VentanaAgregarHoras extends Controlador {

    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;

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

    public void limpiarCampo(){
        tfHoras.clear();
    }

    @FXML
    void initialize() {
        tfHoras.textProperty().addListener((observable, oldValue, newValue) -> validarHoras(newValue));
        btAceptar.setOnMouseEntered(e -> btAceptar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white;"));
        btAceptar.setOnMouseExited(e -> btAceptar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCancelar.setOnMouseEntered(e -> btCancelar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelar.setOnMouseExited(e -> btCancelar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        tfHoras.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto("^[1-9]{1}[0-9]*$", tfHoras));
    }
}
