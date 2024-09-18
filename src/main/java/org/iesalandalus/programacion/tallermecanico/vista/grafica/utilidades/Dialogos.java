package org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class Dialogos {

    private static final String ID_BT_ACEPTAR = "btAccept";
    private static final String ID_BT_CANCELAR = "btCancel";
    private static String css;

    private Dialogos() {
    }

    public static void setHojaEstilos(String url) {
        if (url == null) {
            throw new NullPointerException();
        }
        css = url;
    }

    private static void setHojaEstilos(Dialog<?> dialogo) {
        if (css != null) {
            dialogo.getDialogPane().getStylesheets().add(css);
        }
    }

    public static void mostrarDialogoError(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.ERROR);
        setHojaEstilos(dialogo);
        dialogo.getDialogPane().lookupButton(ButtonType.OK).setId(ID_BT_ACEPTAR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(propietario);
        dialogo.showAndWait();
    }

    public static void mostrarDialogoInformacion(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        setHojaEstilos(dialogo);
        dialogo.getDialogPane().lookupButton(ButtonType.OK).setId(ID_BT_ACEPTAR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(propietario);
        dialogo.showAndWait();
    }

    public static void mostrarDialogoAdvertencia(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.WARNING);
        setHojaEstilos(dialogo);
        dialogo.getDialogPane().lookupButton(ButtonType.OK).setId(ID_BT_ACEPTAR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(propietario);
        dialogo.showAndWait();
    }

    public static boolean mostrarDialogoConfirmacion(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.CONFIRMATION);
        setHojaEstilos(dialogo);
        dialogo.getDialogPane().lookupButton(ButtonType.OK).setId(ID_BT_ACEPTAR);
        dialogo.getDialogPane().lookupButton(ButtonType.CANCEL).setId(ID_BT_CANCELAR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(propietario);

        Optional<ButtonType> respuesta = dialogo.showAndWait();
        return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
    }

    public static String mostrarDialogoTexto(String titulo, String contenido, Stage propietario, String expresionRegular) {
        TextInputDialog dialogo = new TextInputDialog();
        setHojaEstilos(dialogo);
        dialogo.getEditor().textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(expresionRegular, dialogo.getEditor()));
        dialogo.setGraphic(null);
        dialogo.getDialogPane().lookupButton(ButtonType.OK).setId(ID_BT_ACEPTAR);
        dialogo.getDialogPane().lookupButton(ButtonType.CANCEL).setId(ID_BT_CANCELAR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(propietario);

        Optional<String> respuesta = dialogo.showAndWait();
        return (respuesta.orElse(null));
    }

    public static <T> T mostrarDialogoEleccion(List<T> opciones, String titulo, String contenido, Stage propietario) {
        ChoiceDialog<T> dialogo = new ChoiceDialog<>(opciones.get(1), opciones);
        setHojaEstilos(dialogo);
        dialogo.getDialogPane().lookupButton(ButtonType.OK).setId(ID_BT_ACEPTAR);
        dialogo.getDialogPane().lookupButton(ButtonType.CANCEL).setId(ID_BT_CANCELAR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(propietario);
        Optional<T> respuesta = dialogo.showAndWait();
        return respuesta.orElse(null);
    }

}