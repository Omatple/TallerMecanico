package org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class Controlador {

    private Stage escenario;

    public Stage getEscenario() {
        return escenario;
    }

    void setEscenario(Stage escenario) {
        Objects.requireNonNull(escenario, "ERROR: El escenario no puede ser nulo.");
        this.escenario = escenario;
    }

    public void addHojaEstilos(String hojaEstilos) {
        getEscenario().getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource(hojaEstilos)).toExternalForm());
    }

    public void addIcono(String icono) {
        getEscenario().getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(icono))));
    }

    public void centrar() {
        Rectangle2D limitesPantalla = Screen.getPrimary().getVisualBounds();
        escenario.setX((limitesPantalla.getWidth() - escenario.getWidth()) / 2);
        escenario.setY((limitesPantalla.getHeight() - escenario.getHeight()) / 2);
    }

}