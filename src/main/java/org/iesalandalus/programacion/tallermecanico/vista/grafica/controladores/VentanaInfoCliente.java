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

public class VentanaInfoCliente extends Controlador {

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERTAR VEHICULO", getEscenario());

    @FXML
    private ComboBox<Vehiculo> cbVehiculos;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextField tfDNI;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfVehiculo;

    private ObservableList<Vehiculo> coleccionVehiculos = FXCollections.observableArrayList();

    public VentanaInsertarVehiculo getVentanaInsertarVehiculo() {
        return ventanaInsertarVehiculo;
    }

    public void infoCliente(Cliente cliente) {
        tfDNI.setText(cliente.getDni());
        tfNombre.setText(cliente.getNombre());
        tfTelefono.setText(cliente.getTelefono());
    }

    public void rellenarCbVehiculos(List<Vehiculo> vehiculos) {
        coleccionVehiculos.clear();
        coleccionVehiculos.addAll(vehiculos);
    }

    @FXML
    void aceptar() {

    }

    @FXML
    void borrar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
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
        ventanaInsertarVehiculo.getEscenario().show();
    }

    @FXML
    void modificar() {

    }

    @FXML
    void initialize() {
        tfDNI.setEditable(false);
        cbVehiculos.setItems(coleccionVehiculos);
    }
}
