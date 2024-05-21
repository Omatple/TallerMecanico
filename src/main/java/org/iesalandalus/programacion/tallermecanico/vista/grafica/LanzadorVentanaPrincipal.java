package org.iesalandalus.programacion.tallermecanico.vista.grafica;

import javafx.application.Application;
import javafx.stage.Stage;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.VentanaClientes;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

public class LanzadorVentanaPrincipal extends Application {

    public static void comenzar() {
        launch(LanzadorVentanaPrincipal.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controlador ventanaPrincipal =  Controladores.get("/vistas/VentanaClientes.fxml", "TALLER MECÁNICO", null);
        VistaGrafica.getInstancia().setVentanaPrincipal(ventanaPrincipal);
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        ventanaPrincipal.getEscenario().show();
        ventanaPrincipal.getEscenario().setOnCloseRequest(e -> VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR));
    }
}
