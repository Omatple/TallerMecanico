package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.util.List;

public class VentanaInfoVehiculo extends Controlador {

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERTAR CLIENTE", getEscenario());

    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelarTrabajo;

    @FXML
    private Button btInsertar;

    @FXML
    private Label lbCliente;

    @FXML
    private Label lbFechaInicio;

    @FXML
    private Label lbTipo;

    @FXML
    private ComboBox<Cliente> cbClientes;

    @FXML
    private ComboBox<String> cbTipo;

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

    private static final String MECANICO = "Mecánico";

    private static final String REVISION = "Revisión";

    private ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();

    public VentanaInsertarCliente getVentanaInsertarCliente() {
        return ventanaInsertarCliente;
    }

    public Trabajo getTrabajo() {
        Trabajo trabajo = null;
        if (cbTipo.getSelectionModel().getSelectedItem().equals(REVISION)) {
            trabajo = new Revision(Cliente.get(tfCliente.getText()), Vehiculo.get(tfMatricula.getText()), dpFechaInicio.getValue());
        } else if (cbTipo.getSelectionModel().getSelectedItem().equals(MECANICO)) {
            trabajo = new Mecanico(Cliente.get(tfCliente.getText()), Vehiculo.get(tfMatricula.getText()), dpFechaInicio.getValue());
        }
        return trabajo;
    }

    public void limpiarCamposTrabajo() {
        tfCliente.clear();
        dpFechaInicio.editorProperty().get().clear();
        cbTipo.getSelectionModel().select(0);
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
    void aniadirTrabajo() {
        mostrarFormularioTrabajo();
    }

    private void mostrarFormularioTrabajo() {
        limpiarCamposTrabajo();
        lbCliente.setVisible(true);
        tfCliente.setVisible(true);
        cbClientes.setVisible(true);
        btInsertar.setVisible(true);
        lbFechaInicio.setVisible(true);
        dpFechaInicio.setVisible(true);
        lbTipo.setVisible(true);
        cbTipo.setVisible(true);
        btCancelarTrabajo.setVisible(true);
        btAceptar.setVisible(true);
    }

    public void ocultarFormularioTrabajo() {
        lbCliente.setVisible(false);
        tfCliente.setVisible(false);
        cbClientes.setVisible(false);
        btInsertar.setVisible(false);
        lbFechaInicio.setVisible(false);
        dpFechaInicio.setVisible(false);
        lbTipo.setVisible(false);
        cbTipo.setVisible(false);
        btCancelarTrabajo.setVisible(false);
        btAceptar.setVisible(false);
    }

    @FXML
    void aceptar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_REVISION);
    }

    @FXML
    void borrar() {
        ocultarFormularioTrabajo();
        if (Dialogos.mostrarDialogoConfirmacion("BORRAR VEHÍCULO", "¿Estás seguro de que quieres borrar este vehículo?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_VEHICULO);
        }
    }

    @FXML
    void cancelarAniadir() {
        ocultarFormularioTrabajo();
    }

    @FXML
    void cerrar() {
        ocultarFormularioTrabajo();
        getEscenario().close();
    }

    @FXML
    void insertar() {
        ventanaInsertarCliente.getEscenario().show();
    }

    @FXML
    void initialize() {
        ocultarFormularioTrabajo();
        dpFechaInicio.setEditable(false);
        tfMatricula.setEditable(false);
        tfMarca.setEditable(false);
        tfModelo.setEditable(false);
        cbClientes.setItems(coleccionClientes);
        cbClientes.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            String dni = cbClientes.getSelectionModel().getSelectedItem().getDni();
            cbClientes.getSelectionModel().clearSelection();
            tfCliente.setText(dni);
        });
        cbTipo.setItems(FXCollections.observableArrayList(REVISION, MECANICO));
        cbTipo.getSelectionModel().select(0);
    }
}
