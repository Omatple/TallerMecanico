package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;

import java.util.List;

public class VentanaInsertarTrabajoVehiculo extends Controlador {

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERTAR CLIENTE", getEscenario());

    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btInsertarCliente;

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

    public void limpiarClienteFechaInicio() {
        tfCliente.clear();
        dpFechaInicio.editorProperty().get().clear();
        cbTipo.getSelectionModel().select(0);
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

    public void setTextTfVehiculo(String textTfVehiculo) {
        tfVehiculo.setText(textTfVehiculo);
    }

    @FXML
    void initialize() {
        ventanaInsertarCliente.getEscenario().setResizable(false);
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
        btInsertarCliente.setOnMouseEntered(e -> btInsertarCliente.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 261;"));
        btInsertarCliente.setOnMouseExited(e -> btInsertarCliente.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 261;"));
        btAceptar.setOnMouseEntered(e -> btAceptar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white;"));
        btAceptar.setOnMouseExited(e -> btAceptar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCancelar.setOnMouseEntered(e -> btCancelar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelar.setOnMouseExited(e -> btCancelar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        tfCliente.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfCliente));
    }
}
