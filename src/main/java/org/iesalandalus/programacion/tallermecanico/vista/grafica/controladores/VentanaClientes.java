package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

import java.util.List;

public class VentanaClientes extends Controlador {
    private ObservableList<Cliente> coleccionClientes = FXCollections.observableArrayList();

    private final LeerCliente leerCliente = (LeerCliente) Controladores.get("/vistas/LeerCliente.fxml", "INSERCION CLIENTE", getEscenario());

    private final VentanaTrabajosCliente ventanaTrabajosCliente = (VentanaTrabajosCliente) Controladores.get("/vistas/VentanaTrabajosCliente.fxml", "LISTADO TRABAJOS CLIENTE", getEscenario());
    private String nuevoNombre;
    private String nuevoTelefono;

    @FXML
    private Button btClientes;

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableColumn<Cliente, TextField> tcDni;

    @FXML
    private TableColumn<Cliente, TextField> tcNombre;

    @FXML
    private TableColumn<Cliente, TextField> tcTelefono;

    @FXML
    private TextField tfDni;

    @FXML
    private Button btlistar;

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

    public LeerCliente getLeerCliente() {
        return leerCliente;
    }

    public VentanaTrabajosCliente getVentanaTrabajosCliente() {
        return ventanaTrabajosCliente;
    }

    @FXML
    void listar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        btlistar.setVisible(false);
    }

    // PENSAR Y RESOLVER PROBLEMA ELIMINAR INSERTAR, APARECEN DOBLES CELDAS EN LA TABLA
    // TAMBIEN AÑADIR BOTON Y METODOS PARA LISTAR LOS TRABAJOS DE UN CLIENTE
    public void rellenarTabla(List<Cliente> clientes) {
        coleccionClientes.clear();
        coleccionClientes.addAll(clientes);
    }

    public Cliente getCliente() {
        return tvClientes.getSelectionModel().getSelectedItem();
    }

    public void filaBuscada(Cliente cliente) {
        coleccionClientes.clear();
        coleccionClientes.add(cliente);
        btlistar.setVisible(true);
    }

    @FXML
    void borrar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
    }

    @FXML
    void buscar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
    }

    @FXML
    void insertar() {
        leerCliente.limpiarCampos();
        leerCliente.getEscenario().show();
    }

    @FXML
    void modificar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
    }

    @FXML
    void listarTrabajos() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
    }

    void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    void setNuevoTelefono(String nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    void activarTextFieldFila() {
        tcNombre.setCellValueFactory(c -> {
            TextField textField = new TextField();
            textField.setText(c.getValue().getNombre());
            textField.setEditable(false);
            Cliente clienteSeleccionado = getCliente();
            if ((clienteSeleccionado != null) && (clienteSeleccionado.getDni().equals(c.getValue().getDni()))) {
                setNuevoNombre(null);
                textField.setEditable(true);
                textField.textProperty().addListener((observable, oldValue, newValue) -> setNuevoNombre(newValue));
            }
            return new SimpleObjectProperty<>(textField);
        });
        tcTelefono.setCellValueFactory(c -> {
            TextField textField = new TextField();
            textField.setText(c.getValue().getTelefono());
            textField.setEditable(false);
            Cliente clienteSeleccionado = getCliente();
            if ((clienteSeleccionado != null) && (clienteSeleccionado.getDni().equals(c.getValue().getDni()))) {
                setNuevoTelefono(null);
                textField.setEditable(true);
                textField.textProperty().addListener((observable, oldValue, newValue) -> setNuevoTelefono(newValue));
            }
            return new SimpleObjectProperty<>(textField);
        });
        listar();
    }

    @FXML
    void initialize() {
        btlistar.setVisible(false);
        btClientes.setDisable(true);
        tvClientes.setItems(coleccionClientes);
        tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tcNombre.setCellValueFactory(c -> {
            TextField textField = new TextField();
            textField.setText(c.getValue().getNombre());
            textField.setEditable(false);
            return new SimpleObjectProperty<>(textField);
        });
        tcTelefono.setCellValueFactory(c -> {
            TextField textField = new TextField();
            textField.setText(c.getValue().getTelefono());
            textField.setEditable(false);
            return new SimpleObjectProperty<>(textField);
        });
        tvClientes.getSelectionModel().selectedIndexProperty().addListener(observable -> activarTextFieldFila());
    }
}
