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

public class VentanaInsertarTrabajoVehiculo extends Controlador {

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERCAR CLIENTE", getEscenario());

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfVehiculo;

    @FXML
    private DatePicker dpFechaInicio;

    private final ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();
    private static final String MECANICO = "Mecánico";
    private static final String REVISION = "Revisión";

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

    public void limpiarClienteFechaInicio() {
        tfCliente.clear();
        dpFechaInicio.editorProperty().get().clear();
    }

    public void rellenarCbClientes(List<Cliente> clientes) {
        coleccionClientes.clear();
        coleccionClientes.addAll(clientes);
    }

    @FXML
    void aceptar() {
       /* if (cbTipo.getSelectionModel().getSelectedItem().equals(REVISION)) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_REVISION);
        } else if (cbTipo.getSelectionModel().getSelectedItem().equals(MECANICO)) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_MECANICO);
        }*/
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

    public void setTextTfVehiculo(String textTfVehiculo){
        tfVehiculo.setText(textTfVehiculo);
    }

    @FXML
    void initialize() {
        tfVehiculo.setEditable(false);
        tfVehiculo.setDisable(true);
        cbCliente.setItems(coleccionClientes);
        cbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cbCliente.getSelectionModel().clearSelection();
            if (newValue == null) {
                tfCliente.setText("");
            } else {
                tfCliente.setText(newValue.getDni());
            }
        });
        cbTipo.setItems(FXCollections.observableArrayList(REVISION, MECANICO));
        cbTipo.getSelectionModel().select(0);
        dpFechaInicio.setEditable(false);
    }
}
