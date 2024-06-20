package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

import java.util.List;

public class VentanaInsertarTrabajo extends Controlador {

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERCAR CLIENTE", getEscenario());

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERCAR VEHICULO", getEscenario());

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private ComboBox<Vehiculo> cbVehiculo;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfVehiculo;

    private final ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();
    private final ObservableList<Vehiculo> coleccionVehiculos = FXCollections.observableArrayList();
    private static final String MECANICO = "Mec�nico";
    private static final String REVISION = "Revisi�n";

    public Trabajo getTrabajo() {
        Trabajo trabajo = null;
        if (cbTipo.getSelectionModel().getSelectedItem().equals(REVISION)) {
            trabajo = new Revision(Cliente.get(tfCliente.getText()), Vehiculo.get(tfVehiculo.getText()), dpFechaInicio.getValue());
        } else if (cbTipo.getSelectionModel().getSelectedItem().equals(MECANICO)) {
            trabajo = new Mecanico(Cliente.get(tfCliente.getText()), Vehiculo.get(tfVehiculo.getText()), dpFechaInicio.getValue());
        }
        return trabajo;
    }

    public VentanaInsertarCliente getVentanaInsertarCliente() {
        return ventanaInsertarCliente;
    }

    public VentanaInsertarVehiculo getVentanaInsertarVehiculo() {
        return ventanaInsertarVehiculo;
    }

    public void limpiarCampos() {
        tfCliente.clear();
        tfVehiculo.clear();
        dpFechaInicio.editorProperty().get().clear();
        cbTipo.getSelectionModel().select(0);
    }

    public void rellenarCbClientes(List<Cliente> clientes) {
        coleccionClientes.clear();
        coleccionClientes.addAll(clientes);
    }

    public void rellenarCbVehiculos(List<Vehiculo> vehiculos) {
        coleccionVehiculos.clear();
        coleccionVehiculos.addAll(vehiculos);
    }

    @FXML
    void aceptar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_MECANICO);
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    @FXML
    void insertarCliente() {
        ventanaInsertarCliente.limpiarCampos();
        ventanaInsertarCliente.getEscenario().show();
    }

    @FXML
    void insertarVehiculo() {
        ventanaInsertarVehiculo.limpiarCampos();
        ventanaInsertarVehiculo.getEscenario().show();
    }

    @FXML
    void initialize() {
        cbCliente.setItems(coleccionClientes);
        cbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cbCliente.getSelectionModel().clearSelection();
            cbCliente.setPromptText("--- L. CLIENTES ---");
            if (newValue == null) {
                tfCliente.setText("");
            } else {
                tfCliente.setText(newValue.getDni());
            }
        });
        cbVehiculo.setItems(coleccionVehiculos);
        cbVehiculo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cbVehiculo.getSelectionModel().clearSelection();
            cbVehiculo.setPromptText("-- L.VEH�CULOS --");
            if (newValue == null) {
                tfVehiculo.setText("");
            } else {
                tfVehiculo.setText(newValue.matricula());
            }
        });
        cbTipo.setItems(FXCollections.observableArrayList(REVISION, MECANICO));
        cbTipo.getSelectionModel().select(0);
        dpFechaInicio.setEditable(false);
    }

}
