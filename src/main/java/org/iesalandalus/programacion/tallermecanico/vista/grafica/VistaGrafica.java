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
import java.util.ArrayList;
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
        Cliente cliente;
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().isShowing()) {
            cliente = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getVentanaInsertarCliente().getCliente();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            cliente = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getVentanaInsertarCliente().getCliente();
        } else {
            cliente = ((VentanaClientes) ventanaPrincipal).getVentanaInsertarCliente().getCliente();
        }
        return cliente;
    }

    @Override
    public Cliente leerClienteDni() {
        Cliente cliente;
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtBuscarEsPulsado()) {
            cliente = Cliente.get(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente());
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            cliente = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getTrabajo().getCliente();
        } else {
            cliente = ((VentanaClientes) ventanaPrincipal).getCliente();
        }
        return cliente;
    }

    @Override
    public String leerNuevoNombre() {
        String nuevoNombre;
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
            nuevoNombre = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().nuevoNombre();
        } else {
            nuevoNombre = ((VentanaClientes) ventanaPrincipal).getNuevoNombre();
        }
        return nuevoNombre;
    }

    @Override
    public String leerNuevoTelefono() {
        String nuevoTelefono;
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
            nuevoTelefono = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().nuevoTelefono();
        } else {
            nuevoTelefono = ((VentanaClientes) ventanaPrincipal).getNuevoTelefono();
        }
        return nuevoTelefono;
    }

    @Override
    public Vehiculo leerVehiculo() {
        Vehiculo vehiculo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaInsertarVehiculo().getVehiculo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getVentanaInsertarVehiculo().getVehiculo();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getVentanaInsertarVehiculo().getVehiculo();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getVentanaInsertarVehiculo().getVehiculo();
        }
        return vehiculo;
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        Vehiculo vehiculo;
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtBuscarEsPulsado()) {
            vehiculo = Vehiculo.get(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo());
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getTrabajo().getVehiculo();
        } else {
            vehiculo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVehiculo();
        }
        return vehiculo;
    }

    @Override
    public Trabajo leerRevision() {
        Trabajo trabajo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getTrabajo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getTrabajo();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getTrabajo();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getTrabajo();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtBuscarEsPulsado()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getTrabajo();
            System.out.println("trabajo valisoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValida(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValidaTfsEnBlanco(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getTrabajo();
        }//EL IF DE SI ES VISIBLE LISTAR ESTA PARA QUE CUANDO SE BORRAR TRABAJO Y ESTE VISIBLE LISTAR LISTE LOS TRABAJOS SEGN CRITERIO DE BUSQUEDA
        return trabajo;
    }

    @Override
    public Trabajo leerMecanico() {
        Trabajo trabajo = null;
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().getTrabajo();
        } else if (ventanaPrincipal.getEscenario().isShowing()) {
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getTrabajo();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getTrabajo();
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
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setBtBuscarPulsadoDesdeFuera(true);
            }
            trabajo = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getTrabajo();
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
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            horas = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaAgregarHoras().getHoras();
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
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            precioMaterial = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaAgregarPrecioMaterial().getPrecioMaterial();
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
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            fechaCierre = ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getFechaCierre();
        }
        return fechaCierre;
    }

    @Override
    public LocalDate leerMes() {
        LocalDate mes;
        if (((VentanaClientes) ventanaPrincipal).getVentanaEstadisticasMensuales().isEstadisticasMesActual()) {
            System.out.println(LocalDate.now());
            mes = LocalDate.now();
        } else {
            mes = ((VentanaClientes) ventanaPrincipal).getVentanaEstadisticasMensuales().leerMes();
        }
        return mes;
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito) {
            Dialogos.mostrarDialogoInformacion(String.format("%s", evento.toString()), texto, null);
            cerrarVentana(evento);
        } else {
            if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().ocultarFormularioTrabajo();
            } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().isShowing()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().ocultarFormularioTrabajo();
            } else if (evento.equals(Evento.BUSCAR_TRABAJO)) {
                if (!((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtListarYaEraVisible()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setVisibleBtlistar(false);
                }
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().limpiarDpFechaInicio();
            } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtBuscarEsPulsado() && ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar() && !((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtListarYaEraVisible()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setVisibleBtlistar(false);
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().limpiarDpFechaInicio();
            } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtBuscarEsPulsado()) {
                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().limpiarDpFechaInicio();
            }
            Dialogos.mostrarDialogoError(String.format("%s", evento.toString().toUpperCase()), String.format("ERROR: %s%n", texto), null);
            if (evento.equals(Evento.MODIFICAR_CLIENTE)) {
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
                } else {
                    if (((VentanaClientes) ventanaPrincipal).esVisibleListar()) {
                        getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
                    } else
                        getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
                }
            }
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().infoCliente(cliente);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().show();
            getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
        } else {
            ((VentanaClientes) ventanaPrincipal).filaBuscada(cliente);
        }
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().infoVehiculo(vehiculo);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().show();
            getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        } else {
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().filaBuscada(vehiculo);
        }
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        List<Trabajo> trabajoBuscado = new ArrayList<>();
        trabajoBuscado.add(trabajo);
        System.out.println("mostrar trabajooooooooooooooooooooooooo");
        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().rellenarTabla(trabajoBuscado);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().rellenarCbClientes(clientes);
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().limpiarClienteFechaInicio();
            ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().getEscenario().show();
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().rellenarCbClientes(clientes);
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().rellenarCbClientes(clientes);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().limpiarCampos();
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
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().rellenarCbVehiculos(vehiculos);
        } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().rellenarCbVehiculos(vehiculos);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().limpiarCampos();
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getEscenario().show();
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isBtBuscarPulsadoDesdeFuera()) {
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setCogerUltimosDatosBuscados(true);
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().buscar();
            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().setCogerUltimosDatosBuscados(false);
        } else {
            if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().isVisibleBtListar()) {
                    List<Trabajo> trabajosBuscados = new ArrayList<>();
                    if (!((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().isEsPulsadoBtBuscar()) {
                        for (Trabajo trabajo : trabajos) {
                            if (trabajo.getFechaInicio().equals(((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getUltimaFechaBuscadaValida())) {
                                trabajosBuscados.add(trabajo);
                            }
                        }
                    } else {
                        for (Trabajo trabajo : trabajos) {
                            if (trabajo.getFechaInicio().equals(((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getLocalDateDpFechaInicio())) {
                                trabajosBuscados.add(trabajo);
                            }
                        }
                        ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().setUltimaFechaBuscadaValida(((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getLocalDateDpFechaInicio());
                        ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().setEsPulsadoBtBuscar(false);
                        ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().limpiarDpFechaInicio();
                    }
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().rellenarTabla(trabajosBuscados);
                } else {
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().rellenarTabla(trabajos);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getEscenario().show();
                }
            } else if (ventanaPrincipal.getEscenario().isShowing()) {
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().isVisibleBtListar()) {
                    List<Trabajo> trabajosBuscados = new ArrayList<>();
                    if (!((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().isEsPulsadoBtBuscar()) {
                        for (Trabajo trabajo : trabajos) {
                            if (trabajo.getFechaInicio().equals(((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getUltimaFechaBuscadaValida())) {
                                trabajosBuscados.add(trabajo);
                            }
                        }
                    } else {
                        for (Trabajo trabajo : trabajos) {
                            if (trabajo.getFechaInicio().equals(((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getLocalDateDpFechaInicio())) {
                                trabajosBuscados.add(trabajo);
                            }
                        }
                        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().setUltimaFechaBuscadaValida(((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getLocalDateDpFechaInicio());
                        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().setEsPulsadoBtBuscar(false);
                        ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().limpiarDpFechaInicio();
                    }
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().rellenarTabla(trabajosBuscados);
                } else {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().rellenarTabla(trabajos);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getEscenario().show();
                }
            } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().esVisibleListar()) {
                    List<Trabajo> trabajosBuscados = new ArrayList<>();
                    if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().isDpFechaInicioEstaVacio()) {
                        if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente().isBlank() || ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo().isBlank()) {
                            trabajosBuscados.addAll(trabajos);
                            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValida(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
                            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValidaTfsEnBlanco(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
                        } else {
                            for (Trabajo trabajo : trabajos) {
                                if (trabajo.getCliente().equals(Cliente.get(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente())) && trabajo.getVehiculo().equals(Vehiculo.get(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo()))) {
                                    trabajosBuscados.add(trabajo);
                                }
                                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValida(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
                                ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValidaTfsEnBlanco(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
                            }
                        }
                    } else {
                        for (Trabajo trabajo : trabajos) {
                            System.out.println("FECHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                            if (trabajo.getFechaInicio().equals(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio())) {
                                trabajosBuscados.add(trabajo);
                            }
                            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValida(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
                            ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().guardarUltimaBusquedaValidaTfsEnBlanco(((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfCliente(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getStringTfVehiculo(), ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getLocalDateDpFechaInicio());
                        }
                    }
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().limpiarDpFechaInicio();
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().rellenarTabla(trabajosBuscados);

                } else {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().rellenarTabla(trabajos);
                }
            }
        }
    }

    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {
        ((VentanaClientes) ventanaPrincipal).getVentanaEstadisticasMensuales().rellenarPieChart(estadisticas);
    }

    private void cerrarVentana(Evento evento) {
        switch (evento) {
            case INSERTAR_CLIENTE -> {
                getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getVentanaInsertarCliente().getEscenario().close();
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getVentanaInsertarCliente().getEscenario().close();
                } else {
                    ((VentanaClientes) ventanaPrincipal).getVentanaInsertarCliente().getEscenario().close();
                }
            }
            case INSERTAR_VEHICULO -> {
                gestorEventos.notificar(Evento.LISTAR_VEHICULOS);
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getVentanaInsertarVehiculo().getEscenario().close();
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaInsertarVehiculo().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getVentanaInsertarVehiculo().getEscenario().close();
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getVentanaInsertarVehiculo().getEscenario().close();
                }
            }
            case BORRAR_CLIENTE -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().close();
                } else {
                    getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
                }
            }
            case BORRAR_VEHICULO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().close();
                } else {
                    getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
                }
            }
            case BORRAR_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                }
            }
            case ANADIR_HORAS_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaAgregarHoras().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarHoras().getEscenario().close();
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaAgregarHoras().getEscenario().close();
                }
            }
            case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaAgregarPrecioMaterial().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaAgregarPrecioMaterial().getEscenario().close();
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaAgregarPrecioMaterial().getEscenario().close();
                }
            }
            case CERRAR_TRABAJO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                }
            }
            case INSERTAR_MECANICO -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
                    ((VentanaClientes) ventanaPrincipal).getVentanaVehiculos().getVentanaTrabajosVehiculo().getVentanaInsertarTrabajoVehiculo().getEscenario().close();
                } else if (ventanaPrincipal.getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajosCliente().getVentanaInsertarTrabajoCliente().getEscenario().close();
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getEscenario().isShowing()) {
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInsertarTrabajo().getEscenario().close();
                }
            }

            case INSERTAR_REVISION -> {
                if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoCliente().ocultarFormularioTrabajo();
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                } else if (((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().getEscenario().isShowing()) {
                    ((VentanaClientes) ventanaPrincipal).getVentanaTrabajos().getVentanaInfoVehiculo().ocultarFormularioTrabajo();
                    getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
                }
            }

            case MODIFICAR_CLIENTE -> getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
        }
    }
}
