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

import javax.swing.plaf.PanelUI;
import java.util.List;

public class VentanaInfoCliente extends Controlador {

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERTAR VEHICULO", getEscenario());

    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelarTrabajo;

    @FXML
    private Button btInsertar;

    @FXML
    private Label lbFechaInicio;

    @FXML
    private Label lbTipo;

    @FXML
    private Label lbVehiculo;

    @FXML
    private ComboBox<Vehiculo> cbVehiculos;

    @FXML
    private ComboBox<String> cbTipo;

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

    private Cliente clienteOriginal;

    private static final String MECANICO = "Mec�nico";

    private static final String REVISION = "Revisi�n";

    private ObservableList<Vehiculo> coleccionVehiculos = FXCollections.observableArrayList();

    public VentanaInsertarVehiculo getVentanaInsertarVehiculo() {
        return ventanaInsertarVehiculo;
    }

    public Trabajo getTrabajo() {
        Trabajo trabajo = null;
        if (cbTipo.getSelectionModel().getSelectedItem().equals(REVISION)) {
            trabajo = new Revision(Cliente.get(tfDNI.getText()), Vehiculo.get(tfVehiculo.getText()), dpFechaInicio.getValue());
        } else if (cbTipo.getSelectionModel().getSelectedItem().equals(MECANICO)) {
            trabajo = new Mecanico(Cliente.get(tfDNI.getText()), Vehiculo.get(tfVehiculo.getText()), dpFechaInicio.getValue());
        }
        return trabajo;
    }

    public void limpiarCamposTrabajo() {
        tfVehiculo.clear();
        dpFechaInicio.editorProperty().get().clear();
        cbTipo.getSelectionModel().select(0);
    }

    public void infoCliente(Cliente cliente) {
        tfDNI.setText(cliente.getDni());
        tfNombre.setText(cliente.getNombre());
        tfTelefono.setText(cliente.getTelefono());
        clienteOriginal = cliente;
    }

    public void rellenarCbVehiculos(List<Vehiculo> vehiculos) {
        coleccionVehiculos.clear();
        coleccionVehiculos.addAll(vehiculos);
    }

    @FXML
    void aceptar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_REVISION);
    }

    @FXML
    void borrar() {
        ocultarFormularioTrabajo();
        if (Dialogos.mostrarDialogoConfirmacion("BORRAR CLIENTE", "�Est�s seguro de que quieres borrar este cliente?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
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
        ventanaInsertarVehiculo.getEscenario().show();
    }

    @FXML
    void modificar() {
        ocultarFormularioTrabajo();
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
    }

    public Cliente getClienteOriginal() {
        return clienteOriginal;
    }

    public String nuevoNombre() {
        String nuevoNombre = null;
        if (!getClienteOriginal().getNombre().equals(tfNombre.getText())) {
            nuevoNombre = tfNombre.getText();
        }
        return nuevoNombre;
    }

    public String nuevoTelefono() {
        String nuevoTelefono = null;
        if (!getClienteOriginal().getTelefono().equals(tfTelefono.getText())) {
            nuevoTelefono = tfTelefono.getText();
        }
        return nuevoTelefono;
    }

    @FXML
    void aniadirTrabajo() {
        mostrarFormularioTrabajo();
    }

    private void mostrarFormularioTrabajo() {
        limpiarCamposTrabajo();
        lbVehiculo.setVisible(true);
        tfVehiculo.setVisible(true);
        cbVehiculos.setVisible(true);
        btInsertar.setVisible(true);
        lbFechaInicio.setVisible(true);
        dpFechaInicio.setVisible(true);
        lbTipo.setVisible(true);
        cbTipo.setVisible(true);
        btCancelarTrabajo.setVisible(true);
        btAceptar.setVisible(true);
    }

    public void ocultarFormularioTrabajo() {
        lbVehiculo.setVisible(false);
        tfVehiculo.setVisible(false);
        cbVehiculos.setVisible(false);
        btInsertar.setVisible(false);
        lbFechaInicio.setVisible(false);
        dpFechaInicio.setVisible(false);
        lbTipo.setVisible(false);
        cbTipo.setVisible(false);
        btCancelarTrabajo.setVisible(false);
        btAceptar.setVisible(false);
    }

    @FXML
    void initialize() {
        ocultarFormularioTrabajo();
        dpFechaInicio.setEditable(false);
        tfDNI.setEditable(false);
        cbVehiculos.setItems(coleccionVehiculos);
        cbVehiculos.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            String matricula = cbVehiculos.getSelectionModel().getSelectedItem().matricula();
            cbVehiculos.getSelectionModel().clearSelection();
            tfVehiculo.setText(matricula);
        });
        cbTipo.setItems(FXCollections.observableArrayList(REVISION, MECANICO));
        cbTipo.getSelectionModel().select(0);
    }
}
