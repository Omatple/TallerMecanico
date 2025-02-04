package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.util.List;

public class VentanaClientes extends Controlador {

    private final ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();

    private final VentanaInsertarCliente ventanaInsertarCliente = (VentanaInsertarCliente) Controladores.get("/vistas/VentanaInsertarCliente.fxml", "INSERTAR CLIENTE", getEscenario());

    private final VentanaTrabajosCliente ventanaTrabajosCliente = (VentanaTrabajosCliente) Controladores.get("/vistas/VentanaTrabajosCliente.fxml", "LISTADO TRABAJOS CLIENTE", getEscenario());

    private final VentanaVehiculos ventanaVehiculos = (VentanaVehiculos) Controladores.get("/vistas/VentanaVehiculos.fxml", "TALLER MEC�NICO", getEscenario());

    private final VentanaTrabajos ventanaTrabajos = (VentanaTrabajos) Controladores.get("/vistas/VentanaTrabajos.fxml", "TALLER MEC�NICO", getEscenario());

    private final VentanaAcercaDe ventanaAcercaDe = (VentanaAcercaDe) Controladores.get("/vistas/VentanaAcercaDe.fxml", "ACERCA DE ...", getEscenario());

    private final VentanaEstadisticasMensuales ventanaEstadisticasMensuales = (VentanaEstadisticasMensuales) Controladores.get("/vistas/VentanaEstadisticasMensuales.fxml", "ESTAD�STICAS MENSUALES", getEscenario());

    private String nuevoNombre;

    private String nuevoTelefono;

    @FXML
    private Button btBorrar;

    @FXML
    private Button btInsertar;

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

    public VentanaTrabajos getVentanaTrabajos() {
        return ventanaTrabajos;
    }

    public VentanaEstadisticasMensuales getVentanaEstadisticasMensuales() {
        return ventanaEstadisticasMensuales;
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
        /*if (esVisibleListar()) {
            cliente = tvClientes.getSelectionModel().getSelectedItem();
        /} else */ // <-- Despues de 3 meses sin tocar el proyecto al entrar vi un fallo en la busqueda
        // de clientes y vehiculos creo que el fallo residia en este condicional que despues de 3 meses
        // sin ver el proyecto parece irrelevante, ademas de a�adir el boolean false al btPulsado nada
        // mas entrar al siguiente if. Aparentemente ya esta solucionado, pero si he fastidiado otra cosa
        // al arreglar esta es solo cuestion de tiempo para notarlo
        if (btBuscarEsPulsado) {
            btBuscarEsPulsado = false;
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
        if (tvClientes.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.setHojaEstilos("/estilos/aplicacion.css");
            Dialogos.mostrarDialogoError("BORRAR CLIENTE", "ERROR: Selecciona un cliente para poder borrarlo.", getEscenario());
        } else {
            if (Dialogos.mostrarDialogoConfirmacion("BORRAR CLIENTE", "�Est�s seguro de que quieres borrar este cliente?", getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
            }
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
            Dialogos.mostrarDialogoError("LISTAR TRABAJOS CLIENTE", "ERROR: Selecciona un cliente para listar sus trabajos.", getEscenario());
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
        ventanaEstadisticasMensuales.limpiarDp();
        ventanaEstadisticasMensuales.limpiarVentana();
        ventanaEstadisticasMensuales.getEscenario().show();
    }

    @FXML
    void miSalir() {
        if (Dialogos.mostrarDialogoConfirmacion("SALIR", "�Est�s seguro de que quieres salir de la aplicaci�n?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
            getEscenario().close();
        }
    }

    @FXML
    void ventanaTrabajos() {
        getEscenario().close();
        ventanaTrabajos.limpiarCampos();
        ventanaTrabajos.getEscenario().show();
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
    }

    @FXML
    void ventanaVehiculos() {
        getEscenario().close();
        ventanaVehiculos.limpiarCampos();
        ventanaVehiculos.getEscenario().show();
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    public void limpiarCampos() {
        tfDni.clear();
    }

    @FXML
    void initialize() {
        Dialogos.setHojaEstilos("/estilos/aplicacion.css");
        ventanaEstadisticasMensuales.getEscenario().setResizable(false);
        ventanaVehiculos.getEscenario().setResizable(false);
        ventanaTrabajos.getEscenario().setResizable(false);
        ventanaTrabajosCliente.getEscenario().setResizable(false);
        ventanaInsertarCliente.getEscenario().setResizable(false);
        ventanaAcercaDe.getEscenario().setResizable(false);
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
        ventanaVehiculos.getBtClientes().setOnAction(e -> {
            ventanaVehiculos.getEscenario().close();
            limpiarCampos();
            getEscenario().show();
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        });
        ventanaVehiculos.getEscenario().setOnCloseRequest(event -> {
            if (Dialogos.mostrarDialogoConfirmacion("SALIR", "�Est�s seguro de que quieres salir de la aplicaci�n?", ventanaVehiculos.getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
            } else {
                event.consume();
            }
        });
        ventanaVehiculos.getBtTrabajos().setOnAction(e -> {
            ventanaVehiculos.getEscenario().close();
            ventanaTrabajos.limpiarCampos();
            ventanaTrabajos.getEscenario().show();
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
        });
        ventanaVehiculos.getMiEstadisticasMensuales().setOnAction(e -> miEstadisticasMensuales());
        ventanaTrabajos.getBtClientes().setOnAction(e -> {
            ventanaTrabajos.getEscenario().close();
            limpiarCampos();
            getEscenario().show();
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        });
        ventanaTrabajos.getBtVehiculos().setOnAction(e -> {
            ventanaTrabajos.getEscenario().close();
            ventanaVehiculos.limpiarCampos();
            ventanaVehiculos.getEscenario().show();
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
        });
        ventanaTrabajos.getMiEstadisticasMensuales().setOnAction(e -> miEstadisticasMensuales());
        ventanaTrabajos.getEscenario().setOnCloseRequest(event -> {
            if (Dialogos.mostrarDialogoConfirmacion("SALIR", "�Est�s seguro de que quieres salir de la aplicaci�n?", ventanaVehiculos.getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
            } else {
                event.consume();
            }
        });
        btlistar.setOnMouseEntered(e -> btlistar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 271;"));
        btlistar.setOnMouseExited(e -> btlistar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 271;"));
        btInsertar.setOnMouseEntered(e -> btInsertar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white;"));
        btInsertar.setOnMouseExited(e -> btInsertar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btBorrar.setOnMouseEntered(e -> btBorrar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btBorrar.setOnMouseExited(e -> btBorrar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        tfDni.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
    }
}
