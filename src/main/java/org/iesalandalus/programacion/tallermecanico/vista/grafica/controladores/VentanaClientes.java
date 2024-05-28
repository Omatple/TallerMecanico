package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.util.List;

public class VentanaClientes extends Controlador {

    private final ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERTAR CLIENTE", getEscenario());

    private final VentanaTrabajosCliente ventanaTrabajosCliente = (VentanaTrabajosCliente) Controladores.get("/vistas/VentanaTrabajosCliente.fxml", "LISTADO TRABAJOS CLIENTE", getEscenario());

    private final VentanaVehiculos ventanaVehiculos = (VentanaVehiculos) Controladores.get("/vistas/VentanaVehiculos.fxml", "TALLER MECANICO", getEscenario());

    private final VentanaAcercaDe ventanaAcercaDe = (VentanaAcercaDe) Controladores.get("/vistas/VentanaAcercaDe.fxml", "ACERCA DE ...", getEscenario());

    private String nuevoNombre;

    private String nuevoTelefono;

    @FXML
    private Button btClientes;

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableColumn<Cliente, String> tcDni;

    @FXML
    private TableColumn<Cliente, String> tcNombre;

    @FXML
    private TableColumn<Cliente, String> tcTelefono;

    @FXML
    private TextField tfDni;

    @FXML
    private Button btlistar;

    boolean btBuscarEsPulsado;

    public String getNuevoNombre() {
        System.out.println(nuevoNombre);
        return nuevoNombre;
    }

    public String getNuevoTelefono() {
        System.out.println(nuevoTelefono);
        return nuevoTelefono;
    }

    public String getTfDni() {
        return tfDni.getText();
    }

    public VentanaVehiculos getVentanaVehiculos() {
        return ventanaVehiculos;
    }

    public VentanaInsertarCliente getVentanaInsertarCliente() {
        return ventanaInsertarCliente;
    }

    public VentanaTrabajosCliente getVentanaTrabajosCliente() {
        return ventanaTrabajosCliente;
    }

    public boolean esVisibleListar() {
        return btlistar.isVisible();
    }

    @FXML
    void listar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        btlistar.setVisible(false);
    }

    public void rellenarTabla(List<Cliente> clientes) {
        if (btlistar.isVisible()) {
            btlistar.setVisible(false);
        }
        tvClientes.getItems().clear();
        coleccionClientes.clear();
        coleccionClientes.addAll(clientes);
    }

    public Cliente getCliente() {
        Cliente cliente;
        if (esVisibleListar()) {
            cliente = tvClientes.getSelectionModel().getSelectedItem();
        } else if (btBuscarEsPulsado) {
            cliente = Cliente.get(getTfDni());
        } else {
            cliente = tvClientes.getSelectionModel().getSelectedItem();
        }
        btBuscarEsPulsado = false;
        return cliente;
    }

    public void filaBuscada(Cliente cliente) {
        coleccionClientes.clear();
        coleccionClientes.add(cliente);
        btlistar.setVisible(true);
        tfDni.clear();
    }

    @FXML
    void borrar() {
        if (Dialogos.mostrarDialogoConfirmacion("BORRAR CLIENTE", "¿Estás seguro de que quieres borrar este cliente?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
        }
    }

    @FXML
    void buscar() {
        btBuscarEsPulsado = true;
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
    }

    @FXML
    void insertar() {
        ventanaInsertarCliente.limpiarCampos();
        ventanaInsertarCliente.getEscenario().show();
    }

    @FXML
    void listarTrabajos() {
        if (tvClientes.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("LISTAR TRABAJOS CLIENTES", "ERROR: Selecciona un cliente para listar sus trabajos.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
        }
    }

    void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    void setNuevoTelefono(String nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    @FXML
    void miAcercaDe() {
        ventanaAcercaDe.getEscenario().show();
    }

    @FXML
    void miEstadisticasMensuales() {

    }

    @FXML
    void miSalir() {
        if (Dialogos.mostrarDialogoConfirmacion("SALIR", "¿Estás seguro de que quieres salir de la aplicación?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
            getEscenario().close();
        }
    }

    @FXML
    void ventanaTrabajos() {

    }

    @FXML
    void ventanaVehiculos() {
        getEscenario().close();
        ventanaVehiculos.getEscenario().show();
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    @FXML
    void initialize() {
        btBuscarEsPulsado = false;
        btlistar.setVisible(false);
        btClientes.setDisable(true);
        tvClientes.setItems(coleccionClientes);
        tvClientes.editableProperty().setValue(true);
        tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        tcNombre.setOnEditCommit(cEditada -> {
            if (!cEditada.getNewValue().equals(tvClientes.getSelectionModel().getSelectedItem().getNombre())) {
                setNuevoNombre(cEditada.getNewValue());
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
                setNuevoNombre(null);
            }
        });
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tcTelefono.setCellFactory(TextFieldTableCell.forTableColumn());
        tcTelefono.setOnEditCommit(cEditada -> {
            if (!cEditada.getNewValue().equals(tvClientes.getSelectionModel().getSelectedItem().getTelefono())) {
                setNuevoTelefono(cEditada.getNewValue());
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
                setNuevoTelefono(null);
            }
        });
        tvClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ventanaTrabajosCliente.setStrDniCliente(newValue.getDni()));
    }
}
