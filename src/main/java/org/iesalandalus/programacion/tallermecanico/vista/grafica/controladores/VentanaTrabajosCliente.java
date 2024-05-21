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
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

import java.time.LocalDate;
import java.util.List;

public class VentanaTrabajosCliente extends Controlador {

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

    public void rellenarTabla(List<Trabajo> trabajos) {
        coleccionTrabajos.clear();
        coleccionTrabajos.addAll(trabajos);
    }

    @FXML
    void aniadir() {

    }

    @FXML
    void buscar() {

    }

    @FXML
    void eliminar() {

    }

    @FXML
    void listar() {

    }

    @FXML
    void cerrar() {

    }

    @FXML
    void aniadirHoras() {

    }

    @FXML
    void aniadirPrecioMaterial() {

    }

    void

    @FXML
    void initialize() {
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
          if(c.getValue().estaCerrado()) {
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
        tvTrabajos.getSelectionModel().selectionModeProperty().addListener(observable -> );
    }

}
