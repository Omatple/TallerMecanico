package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class VentanaAgregarPrecioMaterial extends Controlador {

    @FXML
    private TextField tfPrecioMaterial;

    public float getPrecioMaterial() {
        float precioMaterial;
        if (tfPrecioMaterial.getText().isBlank()){
            precioMaterial = 0;
        }else{
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

    void validarPrecioMaterial(String nuevo){
        if(!nuevo.matches("^[1-9]{1}[0-9]*(,)?[0-9]{0,2}$")){
           tfPrecioMaterial.clear();
        }
    }

    @FXML
    void initialize() {
        tfPrecioMaterial.textProperty().addListener((observable, oldValue, newValue) -> validarPrecioMaterial(newValue));
    }
}
