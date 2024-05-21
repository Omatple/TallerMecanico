package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class LeerCliente extends Controlador {

    @FXML
    private TextField lbDNI;

    @FXML
    private TextField lbNombre;

    @FXML
    private TextField lbTelefono;

    @FXML
    void cerrarVentana() {
        getEscenario().close();
    }

    public Cliente getCliente() {
        String nombre = lbNombre.getText();
        String DNI = lbDNI.getText();
        String telefono = lbTelefono.getText();
        return new Cliente(nombre, DNI, telefono);
    }

    @FXML
    void insertarCliente(){
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_CLIENTE);
    }

    void bordesCamposValidacion() {

    }

    void limpiarCampos(){
        lbDNI.clear();
        lbNombre.clear();
        lbTelefono.clear();
    }
}
