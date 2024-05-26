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

public class VentanaInsertarTrabajoCliente extends Controlador {

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERCAR VEHICULO", getEscenario());

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private ComboBox<Vehiculo> cbVehiculo;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfVehiculo;

    @FXML
    private DatePicker dpFechaInicio;

    private final ObservableList<Vehiculo> coleccionVehiculos = FXCollections.observableArrayList();
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

    public VentanaInsertarVehiculo getVentanaInsertarVehiculo() {
        return ventanaInsertarVehiculo;
    }

    public void limpiarVehiculoFechaInicio() {
        tfVehiculo.clear();
        dpFechaInicio.editorProperty().get().clear();
    }

    public void rellenarCbVehiculos(List<Vehiculo> vehiculos) {
        coleccionVehiculos.clear();
        coleccionVehiculos.addAll(vehiculos);
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
    void insertarVehiculo() {
        ventanaInsertarVehiculo.limpiarCampos();
        ventanaInsertarVehiculo.getEscenario().show();
    }

    public void setTextTfCliente(String textTfCliente) {
        tfCliente.setText(textTfCliente);
    }

    @FXML
    void initialize() {
        tfCliente.setEditable(false);
        tfCliente.setDisable(true);
        cbVehiculo.setItems(coleccionVehiculos);
        cbVehiculo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cbVehiculo.getSelectionModel().clearSelection();
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
