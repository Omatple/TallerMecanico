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

public class Modelo {
    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public Modelo() {
        comenzar();
    }

    public void comenzar() {
        this.clientes = new Clientes();
        this.vehiculos = new Vehiculos();
        this.revisiones = new Revisiones();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado con éxito. ¡Gracias por utilizar nuestro sistema!");
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        this.clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        this.vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        this.revisiones.insertar(new Revision((buscar(revision.getCliente())), buscar(revision.getVehiculo()), revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        return clientes.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        return revisiones.buscar(revision);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return this.clientes.modificar(cliente, nombre, telefono);
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        this.revisiones.anadirHoras(revision, horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        this.revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        this.revisiones.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Revision revisionCliente : revisiones.get(cliente)) {
            this.revisiones.borrar(revisionCliente);
        }
        this.clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Revision revisionVehiculo : revisiones.get(vehiculo)) {
            this.revisiones.borrar(revisionVehiculo);
        }
        this.vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        this.revisiones.borrar(revision);
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