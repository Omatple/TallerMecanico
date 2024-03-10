package org.iesalandalus.programacion.tallermecanico.modelo;

public class Modelo {
    /*private Clientes clientes;
    private Vehiculos vehiculos;
    private Trabajos trabajos;

    public Modelo() {
        comenzar();
    }

    public void comenzar() {
        this.clientes = new Clientes();
        this.vehiculos = new Vehiculos();
        this.trabajos = new Trabajos();
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
        this.trabajos.insertar(new Revision((buscar(revision.getCliente())), buscar(revision.getVehiculo()), revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        return clientes.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        //return trabajos.buscar(revision);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return this.clientes.modificar(cliente, nombre, telefono);
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        this.trabajos.anadirHoras(revision, horas);
    }

    public void anadirPrecioMaterial(Mecanico mecanico, float precioMaterial) throws OperationNotSupportedException {
        this.trabajos.anadirPrecioMaterial(mecanico, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        this.trabajos.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Revision revisionCliente : trabajos.get(cliente)) {
            this.trabajos.borrar(revisionCliente);
        }
        this.clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Revision revisionVehiculo : trabajos.get(vehiculo)) {
            this.trabajos.borrar(revisionVehiculo);
        }
        this.vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        this.trabajos.borrar(revision);
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
        for (Revision revision : trabajos.get()) {
            coleccionRevionesInstanciada.add(new Revision(revision));
        }
        return coleccionRevionesInstanciada;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> coleccionRevionesClienteInstanciada = new ArrayList<>();
        for (Revision revisionCliente : trabajos.get(cliente)) {
            coleccionRevionesClienteInstanciada.add(new Revision(revisionCliente));
        }
        return coleccionRevionesClienteInstanciada;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> coleccionRevionesVehiculoInstanciada = new ArrayList<>();
        for (Revision revisionVehiculo : trabajos.get(vehiculo)) {
            coleccionRevionesVehiculoInstanciada.add(new Revision(revisionVehiculo));
        }
        return coleccionRevionesVehiculoInstanciada;
    }
     */
}