package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.util.List;

public class VentanaVehiculos extends Controlador {

    private final ObservableList<Vehiculo> coleccionVehiculos = FXCollections.observableArrayList();

    private final VentanaInsertarVehiculo ventanaInsertarVehiculo = (VentanaInsertarVehiculo) Controladores.get("/vistas/VentanaInsertarVehiculo.fxml", "INSERTAR VEH�CULO", getEscenario());

    private final VentanaTrabajosVehiculo ventanaTrabajosVehiculo = (VentanaTrabajosVehiculo) Controladores.get("/vistas/VentanaTrabajosVehiculo.fxml", "LISTADO TRABAJOS VEH�CULO", getEscenario());

    private final VentanaAcercaDe ventanaAcercaDe = (VentanaAcercaDe) Controladores.get("/vistas/VentanaAcercaDe.fxml", "ACERCA DE ...", getEscenario());

    @FXML
    private Button btBorrar;

    @FXML
    private Button btInsertar;

    @FXML
    private Button btVehiculos;

    @FXML
    private MenuItem miEstadisticasMensuales;

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

    @FXML
    private Button btClientes;

    @FXML
    private Button btTrabajos;

    boolean btBuscarEsPulsado;

    Button getBtTrabajos() {
        return btTrabajos;
    }

    Button getBtClientes() {
        return btClientes;
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

    MenuItem getMiEstadisticasMensuales() {
        return miEstadisticasMensuales;
    }

    public Vehiculo getVehiculo() {
        Vehiculo vehiculo;
        /*if (esVisibleListar()) {
            vehiculo = tvVehiculos.getSelectionModel().getSelectedItem();
        } else*/
        if (btBuscarEsPulsado) {
            btBuscarEsPulsado = false;
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
        if (tvVehiculos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("BORRAR VEHICULO", "ERROR: Selecciona un vehiculo para poder borrarlo.", getEscenario());
        } else {
            if (Dialogos.mostrarDialogoConfirmacion("BORRAR VEHICULO", "�Est�s seguro de que quieres borrar este vehiculo?", getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_VEHICULO);
            }
        }
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
    void miAcercaDe() {
        ventanaAcercaDe.getEscenario().show();
    }

    @FXML
    void miSalir() {
        if (Dialogos.mostrarDialogoConfirmacion("SALIR", "�Est�s seguro de que quieres salir de la aplicaci�n?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
            getEscenario().close();
        }
    }

    public void limpiarCampos() {
        tfMatricula.clear();
    }

    @FXML
    void initialize() {
        Dialogos.setHojaEstilos("/estilos/aplicacion.css");
        ventanaAcercaDe.getEscenario().setResizable(false);
        ventanaInsertarVehiculo.getEscenario().setResizable(false);
        ventanaTrabajosVehiculo.getEscenario().setResizable(false);
        btBuscarEsPulsado = false;
        btlistar.setVisible(false);
        btVehiculos.setDisable(true);
        tvVehiculos.setItems(coleccionVehiculos);
        tvVehiculos.editableProperty().setValue(true);
        tcMatricula.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().matricula()));
        tcMarca.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().marca()));
        tcModelo.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().modelo()));
        tvVehiculos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ventanaTrabajosVehiculo.setStrMatriculaVehiculo(newValue.matricula()));
        btlistar.setOnMouseEntered(e -> btlistar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 271;"));
        btlistar.setOnMouseExited(e -> btlistar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 271;"));
        btInsertar.setOnMouseEntered(e -> btInsertar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white;"));
        btInsertar.setOnMouseExited(e -> btInsertar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btBorrar.setOnMouseEntered(e -> btBorrar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btBorrar.setOnMouseExited(e -> btBorrar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        tfMatricula.textProperty().addListener((observable, oldValue, newValue) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
    }
}
