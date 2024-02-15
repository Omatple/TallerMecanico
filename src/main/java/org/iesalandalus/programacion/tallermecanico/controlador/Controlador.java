package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador {
    private Modelo modelo;

    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo");
        Objects.requireNonNull(vista, "La vista no puede ser nula");
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void comenzar() throws OperationNotSupportedException {
        this.modelo.comenzar();
        this.vista.comenzar();
    }

    public void terminar() {
        this.modelo.terminar();
        this.vista.terminar();
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        this.modelo.insertar(cliente);
    }

    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        this.modelo.insertar(vehiculo);
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        this.modelo.insertar(revision);
    }

    public Cliente buscar(Cliente cliente) {
        return this.modelo.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return this.modelo.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        return this.modelo.buscar(revision);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return this.modelo.modificar(cliente, nombre, telefono);
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        this.modelo.anadirHoras(revision, horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        this.modelo.anadirPrecioMaterial(revision, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        this.modelo.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        this.modelo.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        this.modelo.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        this.modelo.borrar(revision);
    }

    public List<Cliente> getClientes() {
        return this.modelo.getClientes();
    }

    public List<Vehiculo> getVehiculos() {
        return this.modelo.getVehiculos();
    }

    public List<Revision> getRevisiones() {
        return this.modelo.getRevisiones();
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        return this.modelo.getRevisiones(cliente);
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        return this.modelo.getRevisiones(vehiculo);
    }
}
