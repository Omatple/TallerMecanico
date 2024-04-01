package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;

    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo");
        Objects.requireNonNull(vista, "La vista no puede ser nula");
        this.modelo = modelo;
        this.vista = vista;
        this.vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
        this.modelo.comenzar();
        this.vista.comenzar();
    }

    @Override
    public void terminar() {
        this.modelo.terminar();
        this.vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        String texto = "";
        boolean exito = false;
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> {
                    modelo.insertar(vista.leerCliente());
                    texto = "¡Cliente insertado exitosamente en el sistema!";
                }
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerClienteDni()));
                case BORRAR_CLIENTE -> {
                    modelo.borrar(vista.leerClienteDni());
                    texto = "¡Cliente eliminado exitosamente del sistema!";
                }
                case MODIFICAR_CLIENTE -> {
                    modelo.modificar(modelo.buscar(vista.leerClienteDni()), vista.leerNuevoNombre(), vista.leerNuevoTelefono());
                    texto = "¡Cliente modificado exitosamente en el sistema!";
                }
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case INSERTAR_VEHICULO -> {
                    modelo.insertar(vista.leerVehiculo());
                    texto = "¡Vehículo insertado exitosamente en el sistema!";
                }
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculoMatricula()));

                case BORRAR_VEHICULO -> {
                    modelo.borrar(vista.leerVehiculoMatricula());
                    texto = "¡Vehículo eliminado exitosamente del sistema!";
                }
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());

                case INSERTAR_REVISION -> {
                    modelo.insertar(vista.leerRevision());
                    texto = "¡Revisión insertado exitosamente en el sistema!";
                }
                case INSERTAR_MECANICO -> {
                    modelo.insertar(vista.leerMecanico());
                    texto = "¡Trabajo mecánico insertado exitosamente en el sistema!";
                }
                case BUSCAR_TRABAJO -> {
                    Trabajo trabajo = vista.leerRevision();
                    vista.mostrarTrabajo(modelo.buscar(trabajo));
                }

                case BORRAR_TRABAJO -> {
                    modelo.borrar(vista.leerRevision());
                    texto = "¡Trabajo eliminado exitosamente del sistema!";
                }
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());

                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));

                case LISTAR_TRABAJOS_VEHICULO ->
                        vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));

                case ANADIR_HORAS_TRABAJO -> {
                    modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
                    texto = "¡La cantidad de horas ha sido añadida exitosamente al trabajo en el sistema!";
                }
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                    modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial());
                    texto = "¡El precio del material ha sido añadido exitosamente al trabajo en el sistema!";
                }
                case CERRAR_TRABAJO -> {
                    modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre());
                    texto = "¡Trabajo cerrado exitosamente en el sistema!";
                }
                case SALIR -> terminar();

            }
            exito = true;
            if (!texto.isBlank()) {
                vista.notificarResultado(evento, texto, exito);
            }
        } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
            vista.notificarResultado(evento, e.getMessage(), exito);
        }

    }
}
