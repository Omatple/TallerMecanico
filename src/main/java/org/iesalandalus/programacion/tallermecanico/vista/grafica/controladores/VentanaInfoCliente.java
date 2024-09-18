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
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.util.List;

public class VentanaInfoCliente extends Controlador {

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERTAR VEHÍCULO", getEscenario());

    @FXML
    private Button btAceptar;

    @FXML
    private Button btBorrar;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btCancelarTrabajo;

    @FXML
    private Button btInsertar;

    @FXML
    private Button btInsertarTrabajo;

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

    private static final String MECANICO = "Mecánico";

    private static final String REVISION = "Revisión";

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
        Dialogos.setHojaEstilos("/estilos/aplicacion.css");
        if (Dialogos.mostrarDialogoConfirmacion("BORRAR CLIENTE", "¿Estás seguro de que quieres borrar este cliente?", getEscenario())) {
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
        ventanaInsertarVehiculo.limpiarCampos();
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
        tfVehiculo.setVisible(true);
        cbVehiculos.setVisible(true);
        btInsertar.setVisible(true);
        dpFechaInicio.setVisible(true);
        cbTipo.setVisible(true);
        btCancelarTrabajo.setVisible(true);
        btAceptar.setVisible(true);
    }

    public void ocultarFormularioTrabajo() {
        tfVehiculo.setVisible(false);
        cbVehiculos.setVisible(false);
        btInsertar.setVisible(false);
        dpFechaInicio.setVisible(false);
        cbTipo.setVisible(false);
        btCancelarTrabajo.setVisible(false);
        btAceptar.setVisible(false);
    }

    @FXML
    void initialize() {
        Dialogos.setHojaEstilos("/estilos/aplicacion.css");
        ventanaInsertarVehiculo.getEscenario().setResizable(false);
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
        btAceptar.setOnMouseEntered(e -> btAceptar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white;"));
        btAceptar.setOnMouseExited(e -> btAceptar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btInsertarTrabajo.setOnMouseEntered(e -> btInsertarTrabajo.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 275;"));
        btInsertarTrabajo.setOnMouseExited(e -> btInsertarTrabajo.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 275;"));
        btInsertar.setOnMouseEntered(e -> btInsertar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 286;"));
        btInsertar.setOnMouseExited(e -> btInsertar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 286;"));
        btBorrar.setOnMouseEntered(e -> btBorrar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btBorrar.setOnMouseExited(e -> btBorrar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCancelarTrabajo.setOnMouseEntered(e -> btCancelarTrabajo.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelarTrabajo.setOnMouseExited(e -> btCancelarTrabajo.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCancelar.setOnMouseEntered(e -> btCancelar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelar.setOnMouseExited(e -> btCancelar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
        tfTelefono.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));
        tfVehiculo.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfVehiculo));
    }
}
