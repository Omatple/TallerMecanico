package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class VentanaInsertarVehiculo extends Controlador {

    @FXML
    private TextField tfMarca;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfModelo;

    public void limpiarCampos() {
        tfMatricula.clear();
        tfMarca.clear();
        tfModelo.clear();
    }

    public Vehiculo getVehiculo() {
        String matricula = tfMatricula.getText();
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        return new Vehiculo(marca, modelo, matricula);
    }

    @FXML
    void cerrar() {
        getEscenario().close();
    }

    @FXML
    void insertar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_VEHICULO);
    }
}
