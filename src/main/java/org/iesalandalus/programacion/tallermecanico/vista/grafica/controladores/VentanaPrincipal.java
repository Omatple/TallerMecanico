package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

public class VentanaPrincipal extends Controlador {
    private final LeerCliente leerCliente = (LeerCliente) Controladores.get("/vistas/LeerCliente.fxml", "INSERCION CLIENTE", getEscenario());
    private final BuscarCliente buscarCliente = (BuscarCliente) Controladores.get("/vistas/BuscarCliente.fxml", "BUSCAR CLIENTE", getEscenario());


    @FXML
    void mostrarLeerCliente() {
        leerCliente.getEscenario().show();
    }

    @FXML
    void mostrarBuscarCliente() {
        buscarCliente.getEscenario().show();
    }

    @FXML
    void terminar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.SALIR);
    }

    public LeerCliente getLeerCliente() {
        return leerCliente;
    }

    public BuscarCliente getBuscarCliente() {
        return buscarCliente;
    }
}
