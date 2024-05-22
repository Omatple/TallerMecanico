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
    private final VentanaInsertarTrabajo ventanaInsertarTrabajo = (VentanaInsertarTrabajo) Controladores.get("/vistas/VentanaInsertarTrabajo.fxml", "INSERTAR TRABAJO CLIENTE", getEscenario());


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

    private ObservableList<Trabajo> coleccionTrabajos = FXCollections.observableArrayList();

    private DatePicker dpFechaFin;

    public VentanaAgregarHoras getVentanaAgregarHoras() {
        return ventanaAgregarHoras;
    }

    public VentanaAgregarPrecioMaterial getVentanaAgregarPrecioMaterial() {
        return ventanaAgregarPrecioMaterial;
    }

    public VentanaInsertarTrabajo getVentanaInsertarTrabajo() {
        return ventanaInsertarTrabajo;
    }


    // RESOOLVER PORQUE AL BUSCAR TENIENDO UNO SELECCIONADO ABAJO, NO SE ACTIVA EL DATEPIKER
    public void rellenarTabla(List<Trabajo> trabajos) {
        if(btlistar.isVisible()){
            btlistar.setVisible(false);
        }
        coleccionTrabajos.clear();
        tvTrabajos.getItems().clear();
        coleccionTrabajos.addAll(trabajos);
    }

    public Trabajo getTrabajo() {
        System.out.println(tvTrabajos.selectionModelProperty().getValue().getSelectedItem());
        return tvTrabajos.selectionModelProperty().getValue().getSelectedItem();
    }

    public LocalDate getFechaCierre() {
        System.out.println(dpFechaFin.getValue());
        return dpFechaFin.getValue();
    }

    @FXML
    void aniadir() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    @FXML
    void buscar() {
        ObservableList<Trabajo> coleccionTrabajosBuscados = FXCollections.observableArrayList();
        if (dpFechaInicio.getEditor().getText().isBlank()) {
            Dialogos.mostrarDialogoError("BUSCAR TRABAJO CLIENTE", "ERROR: Debes elegir una fecha de inicio antes de buscar un trabajo.", getEscenario());
        } else {
            for (Trabajo trabajo : coleccionTrabajos) {
                if (trabajo.getFechaInicio().equals(dpFechaInicio.getValue())) {
                    coleccionTrabajosBuscados.add(trabajo);
                }
            }
            tcFechaFin.setCellValueFactory(c -> {
                dpFechaFin = new DatePicker();
                DatePicker datePicker = new DatePicker();
                datePicker.setEditable(false);
                if (c.getValue().estaCerrado()) {
                    datePicker.setDisable(true);
                    if (c.getValue().estaCerrado()) {
                        datePicker.setValue(c.getValue().getFechaFin());
                    }
                } else {
                    datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> setFechaFin(newValue));
                }
                return new SimpleObjectProperty<>(datePicker);
            });
            rellenarTabla(coleccionTrabajosBuscados);
            btlistar.setVisible(true);
        }
    }

    @FXML
    void borrar() {
        if (getTrabajo() == null) {
            Dialogos.mostrarDialogoError("BORRAR TRABAJO", "ERROR: Selecciona un trabajo para borrarlo.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_TRABAJO);
        }
    }

    @FXML
    void listar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
        btlistar.setVisible(false);
    }

    @FXML
    void cerrar() {
        if (getTrabajo() == null) {
            Dialogos.mostrarDialogoError("CERRAR TRABAJO", "ERROR: Selecciona un trabajo para cerrarlo.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.CERRAR_TRABAJO);
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

    void setStrDniCliente(String dni) {
        ventanaInsertarTrabajo.setTextTfCliente(dni);
    }

    @FXML
    void initialize() {
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
    }

}
