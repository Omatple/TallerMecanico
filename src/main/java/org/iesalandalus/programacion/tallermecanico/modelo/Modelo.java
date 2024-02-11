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
import java.util.Iterator;
import java.util.List;

public class Modelo {
    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    // PREGUNTAR SI DEBERIA HABERLE AÑADIDIDO ALGO AL CONSTRUCTOR,
    // COMO NO SE DICE NADA EN EL DIAGRAMA Y YA INICIALIZAMOS LA VARIABLES
    // EN EN EL METODO COMENZAR(), NO HE VISTO NECESARIO HACER NADA. ¿AUN ASI PREGUNTAR?
    // TAMBIEN MIRAR COMENTARIO DE LA LINEA 46. NO ENTIENDO ESA IMPLEMENTACION.
    public Modelo() {
    }

    public void comenzar() {
        this.clientes = new Clientes();
        this.vehiculos = new Vehiculos();
        this.revisiones = new Revisiones();
    }

    public void terminar() {
        System.out.println("Ha terminado");
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        this.clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        this.vehiculos.insertar(vehiculo);
    }

    //  HE CREADO CREADO EL METODO ASI, DEBIDO A LO QUE PIDEN LOS TESTS, PERO NO LO ENTIENDO PORQUE SE DEBEN BUSCAR
    //  SI BUSCAR LO QUE HACE ES PONER NULL O NO DEPENDIENDO SI EXISTE EL OBJETO EN LA LISTA, PERO NO QUEREMOS INCLUIR
    // UN OBJETO CON ATRIBUTOS NULL, AUNQUE EXISTEN OTROS CON LOS MISMOS ATRIBUTOS, PERO SI NO SON LOS MISMOS, SE DEBERIA CREAR BIEN
    public void insertar(Revision revision) throws OperationNotSupportedException {
        this.revisiones.insertar(new Revision(new Cliente(buscar(revision.getCliente())), buscar(revision.getVehiculo()), revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        return new Cliente(this.clientes.buscar(cliente));
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return this.vehiculos.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        return new Revision(this.revisiones.buscar(revision));
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
        for (Revision revisionCliente: revisiones.get(cliente)){
            this.revisiones.borrar(revisionCliente);
        }
        this.clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Revision revisionVehiculo: this.revisiones.get(vehiculo)){
            this.revisiones.borrar(revisionVehiculo);
        }
        this.vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        this.revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> nuevaListaInstanciada = new ArrayList<>();
        for (Iterator<Cliente> iterator = this.clientes.get().iterator(); iterator.hasNext(); ) {
            Cliente clienteLista = iterator.next();
            nuevaListaInstanciada.add(new Cliente(clienteLista));
        }
        return nuevaListaInstanciada;
    }

    public List<Vehiculo> getVehiculos() {
        return this.vehiculos.get();
    }

    public List<Revision> getRevisiones() {
        List<Revision> nuevaListaInstanciada = new ArrayList<>();
        for (Iterator<Revision> iterator = revisiones.get().iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            nuevaListaInstanciada.add(new Revision(revisionLista));
        }
        return nuevaListaInstanciada;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> nuevaListaInstanciada = new ArrayList<>();
        for (Iterator<Revision> iterator = revisiones.get(cliente).iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            nuevaListaInstanciada.add(new Revision(revisionLista));
        }
        return nuevaListaInstanciada;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> nuevaListaInstanciada = new ArrayList<>();
        for (Iterator<Revision> iterator = revisiones.get(vehiculo).iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            nuevaListaInstanciada.add(new Revision(revisionLista));
        }
        return nuevaListaInstanciada;
    }
}
