package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class VentanaInsertarCliente extends Controlador {

    @FXML
    private TextField tfDNI;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    void cerrar() {
        getEscenario().close();
    }

    public Cliente getCliente() {
        String nombre = tfNombre.getText();
        String DNI = tfDNI.getText();
        String telefono = tfTelefono.getText();
        return new Cliente(nombre, DNI, telefono);
    }

    @FXML
    void insertar(){
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_CLIENTE);
    }

    void bordesCamposValidacion() {

    }

    void limpiarCampos(){
        tfDNI.clear();
        tfNombre.clear();
        tfTelefono.clear();
    }
}
