package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

import java.util.List;

public class VentanaInfoVehiculo extends Controlador {

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERTAR CLIENTE", getEscenario());

    @FXML
    private ComboBox<Cliente> cbClientes;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfMarca;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfModelo;

    private ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();

    public VentanaInsertarCliente getVentanaInsertarCliente() {
        return ventanaInsertarCliente;
    }

    public void infoVehiculo(Vehiculo vehiculo) {
        tfMatricula.setText(vehiculo.matricula());
        tfMarca.setText(vehiculo.marca());
        tfModelo.setText(vehiculo.modelo());
    }

    public void rellenarCbClientes(List<Cliente> clientes) {
        coleccionClientes.clear();
        coleccionClientes.addAll(clientes);
    }

    @FXML
    void aceptar() {

    }

    @FXML
    void borrar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_VEHICULO);
    }

    @FXML
    void cancelarAniadir() {

    }

    @FXML
    void cerrar() {
        getEscenario().close();
    }

    @FXML
    void insertar() {
        ventanaInsertarCliente.getEscenario().show();
    }

    @FXML
    void initialize() {
        tfMatricula.setEditable(false);
        tfMarca.setEditable(false);
        tfModelo.setEditable(false);
        cbClientes.setItems(coleccionClientes);
    }
}
