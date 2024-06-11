package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaTrabajos extends Controlador {

    private final ObservableList<Trabajo> coleccionTrabajos = FXCollections.observableArrayList();

    private final VentanaAgregarHoras ventanaAgregarHoras = (VentanaAgregarHoras) Controladores.get("/vistas/VentanaAgregarHoras.fxml", "AÑADIR HORAS", getEscenario());

    private final VentanaAgregarPrecioMaterial ventanaAgregarPrecioMaterial = (VentanaAgregarPrecioMaterial) Controladores.get("/vistas/VentanaAgregarPrecioMaterial.fxml", "AÑADIR PRECIO MATERIAL", getEscenario());

    private final VentanaAcercaDe ventanaAcercaDe = (VentanaAcercaDe) Controladores.get("/vistas/VentanaAcercaDe.fxml", "ACERCA DE ...", getEscenario());

    private final VentanaInsertarTrabajo ventanaInsertarTrabajo = (VentanaInsertarTrabajo) Controladores.get("/vistas/VentanaInsertarTrabajo.fxml", "INSERTAR TRABAJO", getEscenario());

    private final VentanaInfoCliente ventanaInfoCliente = (VentanaInfoCliente) Controladores.get("/vistas/VentanaInfoCliente.fxml", "INFORMACIÓN CLIENTE", getEscenario());

    private final VentanaInfoVehiculo ventanaInfoVehiculo = (VentanaInfoVehiculo) Controladores.get("/vistas/VentanaInfoVehiculo.fxml", "INFORMACIÓN VEHICULO", getEscenario());

    @FXML
    private Button btClientes;

    @FXML
    private Button btTrabajos;

    @FXML
    private Button btVehiculos;

    @FXML
    private Button btlistar;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TableColumn<Trabajo, String> tcCliente;

    @FXML
    private TableColumn<Trabajo, DatePicker> tcFechaFin;

    @FXML
    private TableColumn<Trabajo, String> tcFechaInicio;

    @FXML
    private TableColumn<Trabajo, String> tcHoras;

    @FXML
    private TableColumn<Trabajo, String> tcPrecioFinal;

    @FXML
    private TableColumn<Trabajo, String> tcPrecioMaterial;

    @FXML
    private TableColumn<Trabajo, String> tcTipo;

    @FXML
    private TableColumn<Trabajo, String> tcVehiculo;

    @FXML
    private TableView<Trabajo> tvTrabajos;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfVehiculo;

    private DatePicker dpFechaFin;

    boolean btBuscarEsPulsado;

    public VentanaInfoCliente getVentanaInfoCliente() {
        return ventanaInfoCliente;
    }

    public VentanaInfoVehiculo getVentanaInfoVehiculo() {
        return ventanaInfoVehiculo;
    }

    public VentanaInsertarTrabajo getVentanaInsertarTrabajo() {
        return ventanaInsertarTrabajo;
    }

    public VentanaAgregarHoras getVentanaAgregarHoras() {
        return ventanaAgregarHoras;
    }

    public VentanaAgregarPrecioMaterial getVentanaAgregarPrecioMaterial() {
        return ventanaAgregarPrecioMaterial;
    }

    public boolean esVisibleListar() {
        return btlistar.isVisible();
    }

    public void rellenarTabla(List<Trabajo> trabajos) {
        if (btlistar.isVisible()) {
            btlistar.setVisible(false);
        }
        tvTrabajos.getItems().clear();
        coleccionTrabajos.clear();
        coleccionTrabajos.addAll(trabajos);
    }

    public Trabajo getTrabajo() {
        Trabajo trabajo = null;
        if (esVisibleListar()) {
            trabajo = tvTrabajos.getSelectionModel().getSelectedItem();
        } else if (btBuscarEsPulsado) {
            //otro boton buscar para buscar por cliente, vehiculo y fecha//trabajo = Trabajo.get(ve);
        } else {
            trabajo = tvTrabajos.getSelectionModel().getSelectedItem();
        }
        btBuscarEsPulsado = false;
        return trabajo;
    }

    public LocalDate getFechaCierre() {
        return dpFechaFin.getValue();
    }

    @FXML
    void aniadir() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    @FXML
    void aniadirHoras() {
        Trabajo trabajo = getTrabajo();
        if (trabajo == null) {
            Dialogos.mostrarDialogoError("AÑADIR HORAS", "ERROR: Selecciona un trabajo para añadir horas.", getEscenario());
        } else if (trabajo.estaCerrado()) {
            Dialogos.mostrarDialogoError("AÑADIR HORAS", "ERROR: No se puede añadir horas, ya que el trabajo está cerrado.", getEscenario());
        } else {
            ventanaAgregarHoras.getEscenario().show();
        }
    }

    @FXML
    void aniadirPrecioMaterial() {
        Trabajo trabajo = getTrabajo();
        if (trabajo == null) {
            Dialogos.mostrarDialogoError("AÑADIR PRECIO MATERIAL", "ERROR: Selecciona un trabajo para añadir precio del material.", getEscenario());
        } else if (trabajo instanceof Revision) {
            Dialogos.mostrarDialogoError("AÑADIR PRECIO MATERIAL", "ERROR: No se puede añadir precio del material a un tipo de trabajo distinto a mecánico.", getEscenario());
        } else if (trabajo.estaCerrado()) {
            Dialogos.mostrarDialogoError("AÑADIR PRECIO MATERIAL", "ERROR: No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.", getEscenario());
        } else {
            ventanaAgregarPrecioMaterial.getEscenario().show();
        }
    }

    @FXML
    void borrar() {
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("BORRAR TRABAJO", "ERROR: Selecciona un trabajo para borrarlo.", getEscenario());
        } else {
            if (Dialogos.mostrarDialogoConfirmacion("BORRAR TRABAJO", "¿Estás seguro de que quieres borrar este trabajo?", getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_TRABAJO);
            }
        }
    }

    @FXML
    void buscar() {
        btBuscarEsPulsado = true;
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_TRABAJO);
    }

    @FXML
    void cerrar() {
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("CERRAR TRABAJO", "ERROR: Selecciona un trabajo para cerrarlo.", getEscenario());
        } else if (tvTrabajos.getSelectionModel().getSelectedItem().estaCerrado()) {
            Dialogos.mostrarDialogoError("CERRAR TRABAJO", "ERROR: El trabajo ya está cerrado.", getEscenario());
        } else {
            if (Dialogos.mostrarDialogoConfirmacion("CERRAR TRABAJO", String.format("¿Estás seguro de que quieres cerrar este trabajo con la fecha '%s'?", getFechaCierre()), getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.CERRAR_TRABAJO);
            }
        }
    }

    @FXML
    void infoCliente() {
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("INFORMACIÓN CLIENTE", "ERROR: Selecciona un trabajo para obtener la información del cliente.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
        }
    }

    @FXML
    void infoVehiculo() {
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("INFORMACIÓN VEHICULO", "ERROR: Selecciona un trabajo para obtener la información del vehiculo.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_VEHICULO);
        }
    }

    @FXML
    void listar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
        btlistar.setVisible(false);
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

    void activarDatePicker() {
        if (!btlistar.isVisible()) {
            tcFechaFin.setCellValueFactory(c -> {
                dpFechaFin = new DatePicker();
                DatePicker datePicker = new DatePicker();
                datePicker.setEditable(false);
                if (!c.getValue().equals(getTrabajo()) || c.getValue().estaCerrado()) {
                    datePicker.setDisable(true);
                    if (c.getValue().estaCerrado()) {
                        datePicker.setValue(c.getValue().getFechaFin());
                    }
                } else {
                    datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> setFechaFin(newValue));
                }
                return new SimpleObjectProperty<>(datePicker);
            });
            listar();
        }
    }

    void setFechaFin(String strFechaFin) {
        System.out.println(strFechaFin);
        LocalDate fechaFin;
        try {
            fechaFin = LocalDate.parse(strFechaFin, Trabajo.FORMATO_FECHA);
        } catch (Exception e) {
            try {
                fechaFin = LocalDate.parse(strFechaFin, DateTimeFormatter.ofPattern("d/M/yyyy"));
            } catch (Exception ex) {
                try {
                    fechaFin = LocalDate.parse(strFechaFin, DateTimeFormatter.ofPattern("dd/M/yyyy"));
                } catch (Exception exc) {
                    try {
                        fechaFin = LocalDate.parse(strFechaFin, DateTimeFormatter.ofPattern("d/MM/yyyy"));
                    } catch (Exception exce) {
                        throw new IllegalArgumentException("ERROR: Fecha fin fuera del rango.");
                    }
                }
            }
        }
        dpFechaFin.setValue(fechaFin);
    }

    @FXML
    void initialize() {
        dpFechaInicio.setEditable(false);
        btlistar.setVisible(false);
        btTrabajos.setDisable(true);
        tvTrabajos.setItems(coleccionTrabajos);
        tcTipo.setCellValueFactory(c -> {
            String tipo = TipoTrabajo.get(c.getValue()).toString();
            return new SimpleObjectProperty<>(tipo);
        });
        tcCliente.setCellValueFactory(c -> {
            String cliente = c.getValue().getCliente().getDni();
            return new SimpleObjectProperty<>(cliente);
        });
        tcVehiculo.setCellValueFactory(c -> {
            String vehiculo = c.getValue().getVehiculo().matricula();
            return new SimpleObjectProperty<>(vehiculo);
        });
        tcFechaInicio.setCellValueFactory(c -> {
            LocalDate fechaInicio = c.getValue().getFechaInicio();
            return new SimpleObjectProperty<>(fechaInicio.format(Trabajo.FORMATO_FECHA));
        });
        tcFechaFin.setCellValueFactory(c -> {
            DatePicker datePicker = new DatePicker();
            datePicker.setEditable(false);
            datePicker.setDisable(true);
            if (c.getValue().estaCerrado()) {
                datePicker.setValue(c.getValue().getFechaFin());
            }
            return new SimpleObjectProperty<>(datePicker);
        });
        tcHoras.setCellValueFactory(new PropertyValueFactory<>("horas"));
        tcPrecioMaterial.setCellValueFactory(c -> {
            String precioMaterial = "";
            if (c.getValue() instanceof Mecanico mecanico) {
                precioMaterial = String.format("%s €", mecanico.getPrecioMaterial());
            }
            return new SimpleObjectProperty<>(precioMaterial);
        });
        tcPrecioFinal.setCellValueFactory(c -> {
            String precio = String.format("%s €", c.getValue().getPrecio());
            return new SimpleObjectProperty<>(precio);
        });
        tvTrabajos.getSelectionModel().selectedIndexProperty().addListener(observable -> activarDatePicker());
    }

}
