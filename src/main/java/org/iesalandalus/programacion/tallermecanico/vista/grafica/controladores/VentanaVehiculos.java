package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.util.List;

public class VentanaVehiculos extends Controlador {

    private final ObservableList<Vehiculo> coleccionVehiculos = FXCollections.observableArrayList();

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERTAR VEHICULO", getEscenario());

    private final VentanaTrabajosVehiculo ventanaTrabajosVehiculo = (VentanaTrabajosVehiculo) Controladores.get("/vistas/VentanaTrabajosVehiculo.fxml", "LISTADO TRABAJOS VEHICULO", getEscenario());

    @FXML
    private Button btVehiculos;

    @FXML
    private TableView<Vehiculo> tvVehiculos;

    @FXML
    private TableColumn<Vehiculo, String> tcMatricula;

    @FXML
    private TableColumn<Vehiculo, String> tcMarca;

    @FXML
    private TableColumn<Vehiculo, String> tcModelo;

    @FXML
    private TextField tfMatricula;

    @FXML
    private Button btlistar;

    boolean btBuscarEsPulsado;

    @FXML
    void ventanaTrabajos() {
    //implementar los mismos metodos para los menuiitem que clientes, salir, acercade, estsadisticas, poner confirmacion y guardado en salir. Tomar referencia clase clientes
    }

    @FXML
    void ventanaVehiculos() {

    }

    public String getTfMatricula() {
        return tfMatricula.getText();
    }

    public VentanaInsertarVehiculo getVentanaInsertarVehiculo() {
        return ventanaInsertarVehiculo;
    }

    public VentanaTrabajosVehiculo getVentanaTrabajosVehiculo() {
        return ventanaTrabajosVehiculo;
    }

    public boolean esVisibleListar() {
        return btlistar.isVisible();
    }

    @FXML
    void listar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
        btlistar.setVisible(false);
    }

    public void rellenarTabla(List<Vehiculo> vehiculos) {
        if (btlistar.isVisible()) {
            btlistar.setVisible(false);
        }
        tvVehiculos.getItems().clear();
        coleccionVehiculos.clear();
        coleccionVehiculos.addAll(vehiculos);
    }

    public Vehiculo getVehiculo() {
        Vehiculo vehiculo;
        if (esVisibleListar()) {
            vehiculo = tvVehiculos.getSelectionModel().getSelectedItem();
        } else if (btBuscarEsPulsado) {
            vehiculo = Vehiculo.get(getTfMatricula());
        } else {
            vehiculo = tvVehiculos.getSelectionModel().getSelectedItem();
        }
        btBuscarEsPulsado = false;
        return vehiculo;
    }

    public void filaBuscada(Vehiculo vehiculo) {
        coleccionVehiculos.clear();
        coleccionVehiculos.add(vehiculo);
        btlistar.setVisible(true);
        tfMatricula.clear();
    }

    @FXML
    void borrar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_VEHICULO);
    }

    @FXML
    void buscar() {
        btBuscarEsPulsado = true;
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_VEHICULO);
    }

    @FXML
    void insertar() {
        ventanaInsertarVehiculo.limpiarCampos();
        ventanaInsertarVehiculo.getEscenario().show();
    }

    @FXML
    void listarTrabajos() {
        if (tvVehiculos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("LISTAR TRABAJOS VEHICULO", "ERROR: Selecciona un vehiculo para listar sus trabajos.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
        }
    }

    @FXML
    void initialize() {
        btBuscarEsPulsado = false;
        btlistar.setVisible(false);
        btVehiculos.setDisable(true);
        tvVehiculos.setItems(coleccionVehiculos);
        tvVehiculos.editableProperty().setValue(true);
        tcMatricula.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().matricula()));
        tcMarca.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().marca()));
        tcModelo.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().modelo()));
        tvVehiculos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ventanaTrabajosVehiculo.setStrMatriculaVehiculo(newValue.matricula()));
    }
}
