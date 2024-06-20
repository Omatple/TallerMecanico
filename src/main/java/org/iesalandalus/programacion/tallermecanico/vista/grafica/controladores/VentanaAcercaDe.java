package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

import java.awt.*;
import java.net.URI;

public class VentanaAcercaDe extends Controlador {

    @FXML
    void abrirEnlace() {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI("https://github.com/Omatple");
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
