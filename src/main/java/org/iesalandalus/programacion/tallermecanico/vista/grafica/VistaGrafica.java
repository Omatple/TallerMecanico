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
        return ((VentanaClientes) ventanaPrincipal).getVentanaInsertarCliente().getCliente();
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
        Vehiculo vehiculo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaInsertarVehiculo().getVehiculo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getVentanaInsertarVehiculo().getVehiculo();
        }
        return vehiculo;
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVehiculo();
    }

    @Override
    public Trabajo leerRevision() {
        Trabajo trabajo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getTrabajo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getTrabajo();
        }
        return trabajo;
    }

    @Override
    public Trabajo leerMecanico() {
        Trabajo trabajo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().getTrabajo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getTrabajo();
        }
        return trabajo;
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        Trabajo trabajo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getTrabajo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getTrabajo();
        }
        return trabajo;
    }

    @Override
    public int leerHoras() {
        int horas = 0;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            horas = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaAgregarHoras().getHoras();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            horas = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarHoras().getHoras();
        }
        return horas;
    }

    @Override
    public float leerPrecioMaterial() {
        float precioMaterial = 0;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            precioMaterial = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaAgregarPrecioMaterial().getPrecioMaterial();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            precioMaterial = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarPrecioMaterial().getPrecioMaterial();
        }
        return precioMaterial;
    }

    @Override
    public LocalDate leerFechaCierre() {
        LocalDate fechaCierre = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            fechaCierre = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getFechaCierre();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            fechaCierre = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getFechaCierre();
        }
        return fechaCierre;
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
        ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().filaBuscada(vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {

    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().rellenarCbClientes(clientes);
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().limpiarClienteFechaInicio();
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().getEscenario().show();
        } else {
            ((VentanaClientes) ventanaPrincipal).rellenarTabla(clientes);
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().rellenarTabla(vehiculos);
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().rellenarCbVehiculos(vehiculos);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().limpiarVehiculoFechaInicio();
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getEscenario().show();
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().rellenarTabla(trabajos);
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getEscenario().show();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().rellenarTabla(trabajos);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getEscenario().show();
        }
    }

    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {

    }

    private void cerrarVentana(Evento evento) {
        switch (evento) {
            case INSERTAR_CLIENTE -> {
                getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
                ((VentanaClientes) ventanaPrincipal).getVentanaInsertarCliente().getEscenario().close();
            }
            case INSERTAR_VEHICULO -> {
                gestorEventos.notificar(Evento.LISTAR_VEHICULOS);
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaInsertarVehiculo().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getVentanaInsertarVehiculo().getEscenario().close();
                }
            }
            case BORRAR_CLIENTE -> getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
            case BORRAR_VEHICULO -> getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
            case BORRAR_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                }
            }
            case ANADIR_HORAS_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaAgregarHoras().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarHoras().getEscenario().close();
                }
            }
            case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaAgregarPrecioMaterial().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarPrecioMaterial().getEscenario().close();
                }
            }
            case CERRAR_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                }
            }
            case INSERTAR_MECANICO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getEscenario().close();
                }
            }
        }
    }
}
