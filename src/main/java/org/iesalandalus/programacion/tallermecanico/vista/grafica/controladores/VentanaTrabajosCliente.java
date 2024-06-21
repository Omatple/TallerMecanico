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

public class VentanaTrabajosCliente extends Controlador {

    private final VentanaAgregarHoras ventanaAgregarHoras = (VentanaAgregarHoras) Controladores.get("/vistas/VentanaAgregarHoras.fxml", "AÑADIR HORAS", getEscenario());
    private final VentanaAgregarPrecioMaterial ventanaAgregarPrecioMaterial = (VentanaAgregarPrecioMaterial) Controladores.get("/vistas/VentanaAgregarPrecioMaterial.fxml", "AÑADIR PRECIO MATERIAL", getEscenario());
    private final VentanaInsertarTrabajoCliente ventanaInsertarTrabajoCliente = (VentanaInsertarTrabajoCliente) Controladores.get("/vistas/VentanaInsertarTrabajoCliente.fxml", "INSERTAR TRABAJO CLIENTE", getEscenario());

    @FXML
    private Button btBorrar;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btCerrar;

    @FXML
    private Button btHoras;

    @FXML
    private Button btInsertar;

    @FXML
    private Button btMaterial;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private Button btlistar;

    @FXML
    private TableColumn<Trabajo, DatePicker> tcFechaFin;

    @FXML
    private TableColumn<Trabajo, String> tcFechaInicio;

    @FXML
    private TableColumn<Trabajo, String> tcHoras;

    @FXML
    private TableColumn<Trabajo, String> tcPrecioMaterial;

    @FXML
    private TableColumn<Trabajo, String> tcPrecioFinal;

    @FXML
    private TableColumn<Trabajo, String> tcTipo;

    @FXML
    private TableColumn<Trabajo, String> tcVehiculo;

    @FXML
    private TableView<Trabajo> tvTrabajos;

    private final ObservableList<Trabajo> coleccionTrabajos = FXCollections.observableArrayList();

    private DatePicker dpFechaFin;

    private LocalDate ultimaFechaBuscadaValida;

    private boolean esPulsadoBtBuscar;

    public VentanaAgregarHoras getVentanaAgregarHoras() {
        return ventanaAgregarHoras;
    }

    public VentanaAgregarPrecioMaterial getVentanaAgregarPrecioMaterial() {
        return ventanaAgregarPrecioMaterial;
    }

    public VentanaInsertarTrabajoCliente getVentanaInsertarTrabajoCliente() {
        return ventanaInsertarTrabajoCliente;
    }


    // RESOOLVER PORQUE AL BUSCAR TENIENDO UNO SELECCIONADO ABAJO, NO SE ACTIVA EL DATEPIKER
    public void rellenarTabla(List<Trabajo> trabajos) {
        coleccionTrabajos.clear();
        tvTrabajos.getItems().clear();
        coleccionTrabajos.addAll(trabajos);
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    public Trabajo getTrabajo() {
        System.out.println(tvTrabajos.selectionModelProperty().getValue().getSelectedItem());
        return tvTrabajos.selectionModelProperty().getValue().getSelectedItem();
    }

    public LocalDate getFechaCierre() {
        return dpFechaFin.getValue();
    }

    @FXML
    void aniadir() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    public LocalDate getLocalDateDpFechaInicio() {
        return dpFechaInicio.getValue();
    }

    public boolean isEsPulsadoBtBuscar() {
        return esPulsadoBtBuscar;
    }

    public DatePicker getDpFechaInicio() {
        return dpFechaInicio;
    }

    public boolean isVisibleBtListar() {
        return btlistar.isVisible();
    }

    public void setEsPulsadoBtBuscar(boolean esPulsadoBtBuscar) {
        this.esPulsadoBtBuscar = esPulsadoBtBuscar;
    }

    public void limpiarDpFechaInicio() {
        dpFechaInicio.setValue(null);
    }

    @FXML
    public void buscar() {
        if (dpFechaInicio.getEditor().getText().isBlank()) {
            Dialogos.mostrarDialogoError("BUSCAR TRABAJO CLIENTE", "ERROR: Debes elegir una fecha de inicio antes de buscar un trabajo.", getEscenario());
        } else {
            if (dpFechaInicio.getValue().isAfter(LocalDate.now())) {
                Dialogos.mostrarDialogoError("BUSCAR TRABAJO CLIENTE", "ERROR: No es posible buscar un trabajo en el futuro.", getEscenario());
            } else {
                esPulsadoBtBuscar = true;
                btlistar.setVisible(true);
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
            }
        }
    }

    public void setUltimaFechaBuscadaValida(LocalDate fechaValida) {
        ultimaFechaBuscadaValida = fechaValida;
    }

    public LocalDate getUltimaFechaBuscadaValida() {
        return ultimaFechaBuscadaValida;
    }

    @FXML
    void borrar() {
        if (getTrabajo() == null) {
            Dialogos.mostrarDialogoError("BORRAR TRABAJO", "ERROR: Selecciona un trabajo para borrarlo.", getEscenario());
        } else {
            if (Dialogos.mostrarDialogoConfirmacion("BORRAR TRABAJO", "¿Estás seguro de que quieres borrar este trabajo?", getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_TRABAJO);
            }
        }
    }

    @FXML
    void listar() {
        btlistar.setVisible(false);
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
    }

    @FXML
    void cerrar() {
        if (getTrabajo() == null) {
            Dialogos.mostrarDialogoError("CERRAR TRABAJO", "ERROR: Selecciona un trabajo para cerrarlo.", getEscenario());
        } else if (tvTrabajos.getSelectionModel().getSelectedItem().estaCerrado()) {
            Dialogos.mostrarDialogoError("CERRAR TRABAJO", "ERROR: El trabajo ya está cerrado.", getEscenario());
        } else {
            if (getFechaCierre() == null) {
                Dialogos.mostrarDialogoError("CERRAR TRABAJO", "ERROR: Debes seleccionar una fecha para cerrar el trabajo.", getEscenario());
            } else {
                if (Dialogos.mostrarDialogoConfirmacion("CERRAR TRABAJO", String.format("¿Estás seguro de que quieres cerrar este trabajo con la fecha '%s'?", getFechaCierre()), getEscenario())) {
                    VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.CERRAR_TRABAJO);
                }
            }
        }
    }

    @FXML
    void aniadirHoras() {
        Trabajo trabajo = getTrabajo();
        if (trabajo == null) {
            Dialogos.mostrarDialogoError("AÑADIR HORAS", "ERROR: Selecciona un trabajo para añadir horas.", getEscenario());
        } else if (trabajo.estaCerrado()) {
            Dialogos.mostrarDialogoError("AÑADIR HORAS", "ERROR: No se puede añadir horas, ya que el trabajo está cerrado.", getEscenario());
        } else {
            ventanaAgregarHoras.limpiarCampo();
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
            ventanaAgregarPrecioMaterial.limpiarCampo();
            ventanaAgregarPrecioMaterial.getEscenario().show();
        }
    }

    void activarDatePicker() {
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
        if (!btlistar.isVisible()) {
            listar();
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
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

    void setStrDniCliente(String dni) {
        ventanaInsertarTrabajoCliente.setTextTfCliente(dni);
    }

    @FXML
    void initialize() {
        ventanaAgregarHoras.getEscenario().setResizable(false);
        ventanaAgregarPrecioMaterial.getEscenario().setResizable(false);
        ventanaInsertarTrabajoCliente.getEscenario().setResizable(false);
        dpFechaInicio.setEditable(false);
        btlistar.setVisible(false);
        tvTrabajos.setItems(coleccionTrabajos);
        tcTipo.setCellValueFactory(c -> {
            String tipo = TipoTrabajo.get(c.getValue()).toString();
            return new SimpleObjectProperty<>(tipo);
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
        btlistar.setOnMouseEntered(e -> btlistar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 271;"));
        btlistar.setOnMouseExited(e -> btlistar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 271;"));
        btInsertar.setOnMouseEntered(e -> btInsertar.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 275;"));
        btInsertar.setOnMouseExited(e -> btInsertar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 275;"));
        btBorrar.setOnMouseEntered(e -> btBorrar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btBorrar.setOnMouseExited(e -> btBorrar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCerrar.setOnMouseEntered(e -> btCerrar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCerrar.setOnMouseExited(e -> btCerrar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btCancelar.setOnMouseEntered(e -> btCancelar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"));
        btCancelar.setOnMouseExited(e -> btCancelar.setStyle("-fx-background-color: #e50914; -fx-text-fill: white;"));
        btHoras.setOnMouseEntered(e -> btHoras.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 239;"));
        btHoras.setOnMouseExited(e -> btHoras.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 239;"));
        btMaterial.setOnMouseEntered(e -> btMaterial.setStyle("-fx-background-color: #3c9d3c; -fx-text-fill: white; -fx-pref-width: 287;"));
        btMaterial.setOnMouseExited(e -> btMaterial.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-pref-width: 287;"));
    }

}
