package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
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

    @FXML
    private MenuItem miEstadisticasMensuales;

    private DatePicker dpFechaFin;

    private boolean btBuscarEsPulsado;

    private boolean btListarYaEraVisible;

    private boolean btBuscarPulsadoDesdeFuera;

    private boolean cogerUltimosDatosBuscados;

    private String ultimaBusquedaValidaDni;

    private String ultimaBusquedaValidaMatricula;

    private LocalDate ultimaBusquedaValidaFechaInicio;

    private boolean ultimoBuscadaValidaTfClienteEsBlanco;

    private boolean ultimoBuscadaValidaTfVehiculoEsBlanco;

    private boolean ultimoBuscadaValidaDpFechaInicioEsBlanco;

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

    public Button getBtClientes() {
        return btClientes;
    }

    public Button getBtVehiculos() {
        return btVehiculos;
    }

    public boolean esVisibleListar() {
        return btlistar.isVisible();
    }

    public void rellenarTabla(List<Trabajo> trabajos) {
        tvTrabajos.getItems().clear();
        coleccionTrabajos.clear();
        coleccionTrabajos.addAll(trabajos);
    }

    public Trabajo getTrabajo() {
        Trabajo trabajo;
        if (btBuscarEsPulsado) {
            trabajo = new Revision(Cliente.get(getStringTfCliente()), Vehiculo.get(getStringTfVehiculo()), dpFechaInicio.getValue());
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
        btBuscarEsPulsado = false;
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    @FXML
    void aniadirHoras() {
        btBuscarEsPulsado = false;
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
        btBuscarEsPulsado = false;
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
        btBuscarEsPulsado = false;
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("BORRAR TRABAJO", "ERROR: Selecciona un trabajo para borrarlo.", getEscenario());
        } else {
            if (Dialogos.mostrarDialogoConfirmacion("BORRAR TRABAJO", "¿Estás seguro de que quieres borrar este trabajo?", getEscenario())) {
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_TRABAJO);
            }
        }
    }

    @FXML
    public void buscar() {
        btBuscarEsPulsado = true;
        if (!btBuscarPulsadoDesdeFuera) {
            if (tfCliente.getText().isBlank() && tfVehiculo.getText().isBlank()) {
                if (isDpFechaInicioEstaVacio()) {
                    Dialogos.mostrarDialogoError("BUSCAR TRABAJO", "ERROR: Ingrese algún criterio para realizar la búsqueda.", getEscenario());
                } else {
                    if (esVisibleListar()) {
                        btListarYaEraVisible = true;
                    } else {
                        btListarYaEraVisible = false;
                        btlistar.setVisible(true);
                    }
                    VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                }
            } else if (tfCliente.getText().isBlank() && !tfVehiculo.getText().isBlank()) {
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
            } else if (!tfCliente.getText().isBlank() && tfVehiculo.getText().isBlank()) {
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
            } else if (!tfCliente.getText().isBlank() && !tfVehiculo.getText().isBlank()) {
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                if (isDpFechaInicioEstaVacio()) {
                    VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                } else {
                    VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_TRABAJO);
                }
            }
        } else {
            System.out.println("ENTRADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            btBuscarPulsadoDesdeFuera = false;
            if (ultimoBuscadaValidaTfClienteEsBlanco && ultimoBuscadaValidaTfVehiculoEsBlanco && !ultimoBuscadaValidaDpFechaInicioEsBlanco) {
                System.out.println("ENTRADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1");
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
            } else if (ultimoBuscadaValidaTfClienteEsBlanco && !ultimoBuscadaValidaTfVehiculoEsBlanco) {
                System.out.println("ENTRADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA2");
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
            } else if (!ultimoBuscadaValidaTfClienteEsBlanco && ultimoBuscadaValidaTfVehiculoEsBlanco) {
                System.out.println("ENTRADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA3");
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
            } else if (!ultimoBuscadaValidaTfClienteEsBlanco && !ultimoBuscadaValidaTfVehiculoEsBlanco) {
                System.out.println("ENTRADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA4");
                if (esVisibleListar()) {
                    btListarYaEraVisible = true;
                } else {
                    btListarYaEraVisible = false;
                    btlistar.setVisible(true);
                }
                if (ultimoBuscadaValidaDpFechaInicioEsBlanco) {
                    VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                } else {
                    VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_TRABAJO);
                }
            }
        }
    }

    public boolean isBtBuscarPulsadoDesdeFuera() {
        return btBuscarPulsadoDesdeFuera;
    }

    public void setBtBuscarPulsadoDesdeFuera(boolean btBuscarPulsadoDesdeFuera) {
        this.btBuscarPulsadoDesdeFuera = btBuscarPulsadoDesdeFuera;
    }

    public boolean isBtListarYaEraVisible() {
        return btListarYaEraVisible;
    }

    public boolean isBtBuscarEsPulsado() {
        return btBuscarEsPulsado;
    }

    public void setCogerUltimosDatosBuscados(boolean cogerUltimosDatosBuscados) {
        this.cogerUltimosDatosBuscados = cogerUltimosDatosBuscados;
    }

    public void setVisibleBtlistar(boolean esVisible) {
        btlistar.setVisible(esVisible);
    }

    public void guardarUltimaBusquedaValida(String dni, String matricula, LocalDate fechaInicio) {
        ultimaBusquedaValidaDni = dni;
        ultimaBusquedaValidaMatricula = matricula;
        ultimaBusquedaValidaFechaInicio = fechaInicio;
    }

    public void guardarUltimaBusquedaValidaTfsEnBlanco(String dni, String matricula, LocalDate fechaInicio) {
        ultimoBuscadaValidaTfClienteEsBlanco = dni.isBlank();
        ultimoBuscadaValidaTfVehiculoEsBlanco = matricula.isBlank();
        ultimoBuscadaValidaDpFechaInicioEsBlanco = fechaInicio == null;
    }

    public LocalDate getLocalDateDpFechaInicio() {
        LocalDate fechaInicio;
        if (!cogerUltimosDatosBuscados) {
            if (dpFechaInicio.getEditor().getText().isBlank()) {
                fechaInicio = null;
            } else {
                fechaInicio = dpFechaInicio.getValue();
            }
            System.out.println("Fecha textfield: " + fechaInicio);
        } else {
            fechaInicio = ultimaBusquedaValidaFechaInicio;
            System.out.println("ultima Fecha: " + fechaInicio);

        }
        System.out.println(fechaInicio);
        return fechaInicio;
    }

    public String getStringTfCliente() {
        String dni;
        if (!cogerUltimosDatosBuscados) {
            dni = tfCliente.getText();
            System.out.println("Dni textfield: " + dni);
        } else {
            dni = ultimaBusquedaValidaDni;
            System.out.println("ultimo Dni: " + dni);
        }
        return dni;
    }

    public String getStringTfVehiculo() {
        String matricula;
        if (!cogerUltimosDatosBuscados) {
            matricula = tfVehiculo.getText();
            System.out.println("Matricula textfield: " + matricula);
        } else {
            matricula = ultimaBusquedaValidaMatricula;
            System.out.println("ultima Matricula: " + matricula);
        }
        return matricula;
    }

    public boolean isDpFechaInicioEstaVacio() {
        return dpFechaInicio.getEditor().getText().isBlank();
    }

    @FXML
    void cerrar() {
        btBuscarEsPulsado = false;
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
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
    void infoCliente() {
        btBuscarEsPulsado = false;
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("INFORMACIÓN CLIENTE", "ERROR: Selecciona un trabajo para obtener la información del cliente.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
        }
    }

    @FXML
    void infoVehiculo() {
        btBuscarEsPulsado = false;
        if (tvTrabajos.getSelectionModel().getSelectedIndex() == -1) {
            Dialogos.mostrarDialogoError("INFORMACIÓN VEHICULO", "ERROR: Selecciona un trabajo para obtener la información del vehiculo.", getEscenario());
        } else {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_VEHICULO);
        }
    }

    @FXML
    void listar() {
        btlistar.setVisible(false);
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
    }

    @FXML
    void miAcercaDe() {
        ventanaAcercaDe.getEscenario().show();
    }

    @FXML
    void miSalir() {
        if (Dialogos.mostrarDialogoConfirmacion("SALIR", "¿Estás seguro de que quieres salir de la aplicación?", getEscenario())) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
            getEscenario().close();
        }
    }

    void activarDatePicker() {
        btBuscarEsPulsado = false;
        tcFechaFin.setCellValueFactory(c -> {
            dpFechaFin = new DatePicker();
            DatePicker datePicker = new DatePicker();
            datePicker.setMinSize(168, 10);
            datePicker.getEditor().setMinSize(168, 10);
            datePicker.setPromptText("Selecciona fecha");
            datePicker.setEditable(false);
            if (!c.getValue().equals(tvTrabajos.getSelectionModel().getSelectedItem()) || c.getValue().estaCerrado()) {
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
            btBuscarPulsadoDesdeFuera = true;
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
        }
    }

    void setFechaFin(String strFechaFin) {
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

    public MenuItem getMiEstadisticasMensuales() {
        return miEstadisticasMensuales;
    }

    public void limpiarDpFechaInicio() {
        dpFechaInicio.getEditor().clear();
    }

    @FXML
    void initialize() {
        btBuscarPulsadoDesdeFuera = false;
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
            datePicker.setMinSize(168, 10);
            datePicker.getEditor().setMinSize(168, 10);
            datePicker.setPromptText("Selecciona fecha");
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
