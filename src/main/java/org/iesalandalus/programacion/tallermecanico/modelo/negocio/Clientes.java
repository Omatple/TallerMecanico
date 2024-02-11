package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

// PREGUNTAR SI EL METODO GET() A PESAR DE PASAR LOS TEST ES CORRECTO
// PREGUNTAR SI TANTOS BOOLEANS EN EL METODO MODIFICAR ES IDONEO?
// Y DE QUE OTRA FORMA PODRIA HACERSE PARA QUE SEA MAS OPTIMO
// PREGUNTAR EL PORQUE DE LA EXCEPCION DE BORRAR ELEMENTO DE LA LISTA EN EL
// BULCLE CON ITERATOR, SI HAY OTRA FORMA DE HACERLO SIN UTILIZAR UN CONTADOR DE INDICE
// COMO HE REALIZADO YO, ME REFIERO, SI HAY ALGUN BUCLE CON LISTAS DONDE PUEDAS ELIMINAR
// UN ELEMENTO QUE ESTE RECORRIENDO DICHO BUCLE Y QUE NO SALTE UNA EXCEPCION

public class Clientes {
    List<Cliente> listaClientes;

    public Clientes() {
        listaClientes = new ArrayList<>();
    }

    public List<Cliente> get() {
        return listaClientes;
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        for (Iterator<Cliente> iterator = get().iterator(); iterator.hasNext(); ) {
            Cliente clienteLista = iterator.next();
            if (clienteLista.equals(cliente)) {
                throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
            }
        }
        listaClientes.add(cliente);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        boolean modificado = false;
        boolean existe = false;
        boolean nombreEsNulo = false;
        boolean telefonoEsNulo = false;
        boolean nombreEsvacio = false;
        boolean telefonoEsVacio = false;
        if (nombre == null) {
            nombreEsNulo = true;
        } else if (nombre.isBlank()) {
            nombreEsvacio = true;
        }
        if (telefono == null) {
            telefonoEsNulo = true;
        } else if (telefono.isBlank()) {
            telefonoEsVacio = true;
        }
        for (Iterator<Cliente> iterator = get().iterator(); iterator.hasNext(); ) {
            Cliente clienteLista = iterator.next();
            if (clienteLista.equals(cliente)) {
                if (!((nombreEsNulo && telefonoEsNulo) || (nombreEsvacio && telefonoEsVacio) || (nombreEsNulo && telefonoEsVacio) || (nombreEsvacio && telefonoEsNulo))) {
                    if (nombreEsNulo || nombreEsvacio) {
                        clienteLista.setTelefono(telefono);
                    } else if (telefonoEsNulo || telefonoEsVacio) {
                        clienteLista.setNombre(nombre);
                    } else {
                        clienteLista.setNombre(nombre);
                        clienteLista.setTelefono(telefono);
                    }
                    modificado = true;
                }
                existe = true;
            }
        }
        if (!existe) {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        return modificado;
    }

    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        Cliente clienteBuscado = null;
        for (Iterator<Cliente> iterator = get().iterator(); iterator.hasNext(); ) {
            Cliente clienteLista = iterator.next();
            if (clienteLista.equals(cliente)) {
                clienteBuscado = clienteLista;
            }
        }
        return clienteBuscado;
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        boolean clienteBorrado = false;
        int indiceClienteLista = 0;
        int indiceClienteBorrar = 0;
        for (Iterator<Cliente> iterator = get().iterator(); iterator.hasNext(); ) {
            Cliente clienteLista = iterator.next();
            if (clienteLista.equals(cliente)) {
                indiceClienteBorrar = indiceClienteLista;
                clienteBorrado = true;
            }
            indiceClienteLista++;
        }
        if (!clienteBorrado) {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        } else {
            listaClientes.remove(indiceClienteBorrar);
        }
    }
}
