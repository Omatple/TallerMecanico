package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

public class BuscarCliente extends Controlador {

    private final ClienteEncontrado clienteEncontrado = (ClienteEncontrado) Controladores.get("/vistas/ClienteEncontrado.fxml", "CLIENTE BUSCADO", getEscenario());

    @FXML
    private TextField tfDNI;

    public Cliente getClienteDNI() {
        return Cliente.get(tfDNI.getText());
    }

    @FXML
    void buscarCliente() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
    }

    @FXML
    void cerrarVentana() {
        getEscenario().close();
    }

    public ClienteEncontrado getClienteEncontrado() {
        return clienteEncontrado;
    }

}
