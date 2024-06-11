package org.iesalandalus.programacion.tallermecanico.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Clientes implements IClientes {

    private static final String COLECCION = "clientes";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";

    private static Clientes instancia;
    private MongoCollection<Document> coleccionClientes;

    private Clientes() {
    }

    public static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        coleccionClientes = MongoDB.getBD().getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }

    Cliente getCliente(Document documento) {
        Cliente cliente = null;
        if (documento != null) {
            cliente = new Cliente(documento.getString(NOMBRE), documento.getString(DNI), documento.getString(TELEFONO));
        }
        return cliente;
    }

    Document getDocumento(Cliente cliente) {
        Document documento = null;
        if (cliente != null) {
            String nombre = cliente.getNombre();
            String dni = cliente.getDni();
            String telefono = cliente.getTelefono();
            documento = new Document().append(NOMBRE, nombre).append(DNI, dni).append(TELEFONO, telefono);
        }
        return documento;
    }

    private Bson getCriterioBusqueda(Cliente cliente) {
        return eq(DNI, cliente.getDni());
    }

    @Override
    public List<Cliente> get() {
        List<Cliente> clientes = new ArrayList<>();
        for (Document documento : coleccionClientes.find()) {
            clientes.add(getCliente(documento));
        }
        return clientes;
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        FindOneAndReplaceOptions opciones = new FindOneAndReplaceOptions().upsert(true);
        Document resultado = coleccionClientes.findOneAndReplace(getCriterioBusqueda(cliente), getDocumento(cliente), opciones);
        if (resultado != null) {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Bson modificacion = null;
        if (nombre != null && !nombre.isBlank()) {
            modificacion = set(NOMBRE, nombre);
        }
        if (telefono != null && !telefono.isBlank()) {
            modificacion = (modificacion == null) ? set(TELEFONO, telefono) : combine(modificacion, set(TELEFONO, telefono));
        }
        if (modificacion != null) {
            FindOneAndUpdateOptions opciones = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            Document clienteModificado = coleccionClientes.findOneAndUpdate(getCriterioBusqueda(cliente), modificacion, opciones);
            if (clienteModificado == null) {
                throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
            } else {
                cliente.setNombre(clienteModificado.getString(NOMBRE));
                cliente.setTelefono(clienteModificado.getString(TELEFONO));
                //Trabajos.getInstancia().modificarCliente(clienteModificado);
            }
        }
        return modificacion != null;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        return getCliente(coleccionClientes.find(getCriterioBusqueda(cliente)).first());
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        DeleteResult resultado = coleccionClientes.deleteOne(getCriterioBusqueda(cliente));
        if (resultado.getDeletedCount() == 0) {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}
