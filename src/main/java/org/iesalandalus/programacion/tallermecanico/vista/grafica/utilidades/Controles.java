package org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades;

import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controles {

    private static final String CSS_VALIDO = "valido";
    private static final String CSS_INVALIDO = "invalido";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Controles() {
        // Evito que se puedan instanciar objetos
    }

    public static void validarCampoTexto(String er, TextField campoTexto) {
        String texto = campoTexto.getText();
        if (texto.matches(er)) {
            campoTexto.setStyle("-fx-font-size: 14pt; " +
                    "-fx-background-color: #1b5e20; " +
                    "-fx-border-color: #28a745; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 10px; " +
                    "-fx-alignment: center; -fx-text-alignment: center; " +
                    "-fx-padding: 10px; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2); " +
                    "-fx-transition: all 0.3s ease-in-out; ");
        } else {
            campoTexto.setStyle("-fx-font-size: 14pt; " +
                    "-fx-background-color: #303030; " +
                    "-fx-border-color: #e50914; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 10px; " +
                    "-fx-alignment: center; -fx-text-alignment: center; " +
                    "-fx-padding: 10px; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2); " +
                    "-fx-transition: all 0.3s ease-in-out; ");
        }
    }

    /***
     * ORIGINAL PROFESOR -->
     * public static void validarCampoTexto(String er, TextField campoTexto) {
        String texto = campoTexto.getText();
        if (texto.matches(er)) {
            setValido(campoTexto);
        }
        else {
            setInvalido(campoTexto);
        }
    }
     ***/

    public static void setValido(Node nodo) {
        nodo.getStyleClass().remove(CSS_INVALIDO);
        if (!nodo.getStyleClass().contains(CSS_VALIDO)) {
            nodo.getStyleClass().add(CSS_VALIDO);
        }
    }

    public static void setInvalido(Node nodo) {
        nodo.getStyleClass().remove(CSS_VALIDO);
        if (!nodo.getStyleClass().contains(CSS_INVALIDO)) {
            nodo.getStyleClass().add(CSS_INVALIDO);
        }
    }

    private static void limpiarCampoTexto(TextField campoTexto) {
        campoTexto.setText("");
        Controles.setInvalido(campoTexto);
    }

    public static void limpiarCamposTexto(TextField... camposTexto) {
        for (TextField campoTexto : camposTexto) {
            limpiarCampoTexto(campoTexto);
        }
    }

    private static void deshabilitarCampoTexto(TextField campoTexto) {
        campoTexto.setDisable(true);
        limpiarCampoTexto(campoTexto);
        campoTexto.getStyleClass().remove(CSS_INVALIDO);
    }

    public static void deshabilitarCamposTexto(TextField... camposTexto) {
        for (TextField campoTexto : camposTexto) {
            deshabilitarCampoTexto(campoTexto);
        }
    }

    private static void habilitarCampoTexto(TextField campoTexto) {
        campoTexto.setDisable(false);
        Controles.setInvalido(campoTexto);
    }

    public static void habilitarCamposTexto(TextField... camposTexto) {
        for (TextField campoTexto : camposTexto) {
            habilitarCampoTexto(campoTexto);
        }
    }

    public static class FormateadorCeldaFecha<T> extends TableCell<T, LocalDate> {
        @Override
        protected void updateItem(LocalDate fecha, boolean vacio) {
            super.updateItem(fecha, vacio);
            if (vacio || fecha == null) {
                setText("");
            } else {
                setText(FORMATO_FECHA.format(fecha));
            }
        }
    }
}