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
        ventanaPrincipal.getEscenario().close();
    }

    @Override
    public Cliente leerCliente() {
        return ((VentanaClientes) ventanaPrincipal).getLeerCliente().getCliente();
    }

    @Override
    public Cliente leerClienteDni() {
        return ((VentanaClientes) ventanaPrincipal).getCliente();
    }

    @Override
    public String leerNuevoNombre() {
        return ((VentanaClientes) ventanaPrincipal).getNuevoNombre();
    }

    @Override
    public String leerNuevoTelefono() {
        return ((VentanaClientes) ventanaPrincipal).getNuevoTelefono();

    }

    @Override
    public Vehiculo leerVehiculo() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().getVentanaInsertarVehiculo().getVehiculo();
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return null;
    }

    @Override
    public Trabajo leerRevision() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getTrabajo();
    }

    @Override
    public Trabajo leerMecanico() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().getTrabajo();
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getTrabajo();
    }

    @Override
    public int leerHoras() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarHoras().getHoras();
    }

    @Override
    public float leerPrecioMaterial() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarPrecioMaterial().getPrecioMaterial();
    }

    @Override
    public LocalDate leerFechaCierre() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getFechaCierre();
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
            if (evento.equals(Evento.MODIFICAR_CLIENTE)) {
                if (((VentanaClientes) ventanaPrincipal).esVisibleListar()) {
                    getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
                } else
                    getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
            }
        }
    }


    @Override
    public void mostrarCliente(Cliente cliente) {
        ((VentanaClientes) ventanaPrincipal).filaBuscada(cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {

    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {

    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        ((VentanaClientes) ventanaPrincipal).rellenarTabla(clientes);
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().rellenarCbVehiculos(vehiculos);
        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().limpiarVehiculoFechaInicio();
        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().getEscenario().show();
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
            case ANADIR_HORAS_TRABAJO -> {
                getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarHoras().getEscenario().close();
            }
            case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarPrecioMaterial().getEscenario().close();
            }
            case BORRAR_TRABAJO -> getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
            case CERRAR_TRABAJO -> getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
            case INSERTAR_MECANICO -> {
                getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().getEscenario().close();
            }
            case INSERTAR_VEHICULO -> {
                gestorEventos.notificar(Evento.LISTAR_VEHICULOS);
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajo().getVentanaInsertarVehiculo().getEscenario().close();
            }
        }
    }
}
