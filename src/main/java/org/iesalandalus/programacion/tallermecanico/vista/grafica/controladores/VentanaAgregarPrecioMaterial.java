package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;

public class VentanaAgregarPrecioMaterial extends Controlador {

    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;

    @FXML
    private TextField tfPrecioMaterial;

    public float getPrecioMaterial() {
        float precioMaterial;
        if (tfPrecioMaterial.getText().isBlank()) {
            precioMaterial = 0;
        } else {
            precioMaterial = Float.parseFloat(tfPrecioMaterial.getText().replace(",", "."));
        }
        return precioMaterial;
    }

    @FXML
    void aceptar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.ANADIR_PRECIO_MATERIAL_TRABAJO);
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    public void limpiarCampo(){
        tfPrecioMaterial.clear();
    }

    @FXML
    void initialize() {
        btAceptar.setOnMouseEntered(e -> btAceptar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white;"));
        btAceptar.setOnMouseExited(e -> btAceptar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCancelar.setOnMouseEntered(e -> btCancelar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelar.setOnMouseExited(e -> btCancelar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        tfPrecioMaterial.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto("^[1-9]{1}[0-9]*(,)?[0-9]{0,2}$", tfPrecioMaterial));
    }
}
