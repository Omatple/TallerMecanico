package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class ClienteEncontrado extends Controlador {
    @FXML
    private TextField lbDNI;

    @FXML
    private TextField lbNombre;

    @FXML
    private TextField lbTelefono;

    public String getNombre() {
        return lbNombre.getText();
    }

    public String getTelefono() {
        return lbTelefono.getText();
    }

    public void rellenarCampos(Cliente cliente) {
        lbNombre.setText(cliente.getNombre());
        lbDNI.setText(cliente.getDni());
        lbTelefono.setText(cliente.getTelefono());
    }

    @FXML
    void aniadirTrabajoCliente() {

    }

    @FXML
    void cerrarVentana() {
        getEscenario().close();
    }

    @FXML
    void eliminarCliente() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
    }

    @FXML
    void listarTrabajosCliente() {

    }

    @FXML
    void modificarCliente() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
    }

    @FXML
    void initialize() {
        lbDNI.setEditable(false);
    }
}
