package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {
    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public Modelo() {
        comenzar();
    }

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado con éxito. ¡Gracias por utilizar nuestro sistema!");
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        clientes.insertar(new Cliente(Objects.requireNonNull(cliente, "No existe un cliente igual")));
    }

    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        revisiones.insertar(new Revision((clientes.buscar(revision.getCliente())), vehiculos.buscar(revision.getVehiculo()), revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        return new Cliente(Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual"));
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehículo igual");
    }

    public Revision buscar(Revision revision) {
        return new Revision(Objects.requireNonNull(revisiones.buscar(revision), "No existe una revisión igual"));
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        revisiones.anadirHoras(revision, horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        revisiones.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Revision revisionCliente : revisiones.get(cliente)) {
            revisiones.borrar(revisionCliente);
        }
        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Revision revisionVehiculo : revisiones.get(vehiculo)) {
            revisiones.borrar(revisionVehiculo);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> coleccionClientesInstanciada = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            coleccionClientesInstanciada.add(new Cliente(cliente));
        }
        return coleccionClientesInstanciada;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }

    public List<Revision> getRevisiones() {
        List<Revision> coleccionRevionesInstanciada = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            coleccionRevionesInstanciada.add(new Revision(revision));
        }
        return coleccionRevionesInstanciada;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> coleccionRevionesClienteInstanciada = new ArrayList<>();
        for (Revision revisionCliente : revisiones.get(cliente)) {
            coleccionRevionesClienteInstanciada.add(new Revision(revisionCliente));
        }
        return coleccionRevionesClienteInstanciada;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> coleccionRevionesVehiculoInstanciada = new ArrayList<>();
        for (Revision revisionVehiculo : revisiones.get(vehiculo)) {
            coleccionRevionesVehiculoInstanciada.add(new Revision(revisionVehiculo));
        }
        return coleccionRevionesVehiculoInstanciada;
    }
}