package org.iesalandalus.programacion.tallermecanico.vista.grafica;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.*;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VistaGrafica implements Vista {
    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    private Controlador ventanaPrincipal;

    private List<Vehiculo> vehiculos;
    private static VistaGrafica instancia;

    private VistaGrafica() {
    }

    public static VistaGrafica getInstancia() {
        if (instancia == null) {
            instancia = new VistaGrafica();
        }
        return instancia;
    }

    void setVentanaPrincipal(Controlador ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public Controlador getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public BuscarCliente getBuscarCliente() {
        return ((VentanaPrincipal) ventanaPrincipal).getBuscarCliente();
    }

    public ClienteEncontrado getClienteEncontrado() {
        return getBuscarCliente().getClienteEncontrado();
    }

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        LanzadorVentanaPrincipal.comenzar();
    }

    @Override
    public void terminar() {
        getVentanaPrincipal().getEscenario().close();
    }

    @Override
    public Cliente leerCliente() {
        return ((VentanaClientes) ventanaPrincipal).getLeerCliente().getCliente();
    }

    @Override
    public Cliente leerClienteDni() {
       // return Cliente.get(((VentanaClientes) getVentanaPrincipal()).getTfDni());
        return ((VentanaClientes) getVentanaPrincipal()).getCliente();
    }

    @Override
    public String leerNuevoNombre() {
        return ((VentanaClientes) getVentanaPrincipal()).getNuevoNombre();
    }

    @Override
    public String leerNuevoTelefono() {
        return ((VentanaClientes) getVentanaPrincipal()).getNuevoTelefono();

    }

    @Override
    public Vehiculo leerVehiculo() {
        return null;
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return null;
    }

    @Override
    public Trabajo leerRevision() {
        return null;
    }

    @Override
    public Trabajo leerMecanico() {
        return null;
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return null;
    }

    @Override
    public int leerHoras() {
        return 0;
    }

    @Override
    public float leerPrecioMaterial() {
        return 0;
    }

    @Override
    public LocalDate leerFechaCierre() {
        return null;
    }

    @Override
    public LocalDate leerMes() {
        return null;
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito) {
            Dialogos.mostrarDialogoInformacion(String.format("%s", evento.toString()), texto, null);
            cerrarVentana(evento);
        } else {
            Dialogos.mostrarDialogoError(String.format("%s", evento.toString()), String.format("ERROR: %s%n", texto), null);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        ((VentanaClientes) getVentanaPrincipal()).filaBuscada(cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {

    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {

    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        ((VentanaClientes) getVentanaPrincipal()).rellenarTabla(clientes);
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {

    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().rellenarTabla(trabajos);
        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getEscenario().show();
    }

    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {

    }

    private void cerrarVentana(Evento evento) {
        switch (evento) {
            case INSERTAR_CLIENTE -> {
                getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
                ((VentanaClientes) ventanaPrincipal).getLeerCliente().getEscenario().close();
            }
            case BORRAR_CLIENTE -> getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        }
    }
}
