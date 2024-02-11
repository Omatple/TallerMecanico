package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

// HE IMPORTATADO EN EL TEST DE VEHICULOS, LA CLASE DE VEHICULO YA QUE NO ESTABA IMPORTADA
// Y NO HE ENCONTRADO OTRA FORMA PARA QUE EL TEST PUEDA CREAR INSTANCIAS DE LA CLASE VEHICULO
// NO SE SI HA SIDO UN ACTO CORRECTO Y AL PROFESRO SE LE PASO IMPORTARLO, O LO MAS SEGURO ->
// QUE NO HAYA SABIDO CORREGIR EL ERROR DESDE MIS CLASES Y METODOS ¿SI ES ASI?¿QUE DERIA HABER HECHO?
// EN VEZ DE IMPORTAR MI CLASE VEHICULO
public class Vehiculos {
    List<Vehiculo> listaVehiculos;

    public Vehiculos() {
        listaVehiculos = new ArrayList<>();
    }

    public List<Vehiculo> get() {
        return listaVehiculos;
    }

    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        for (Iterator<Vehiculo> iterator = get().iterator(); iterator.hasNext(); ) {
            Vehiculo vehiculoLista = iterator.next();
            if (vehiculoLista.equals(vehiculo)) {
                throw new OperationNotSupportedException("Ya existe un vehículo con esa matrícula.");
            }
        }
        listaVehiculos.add(vehiculo);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        Vehiculo vehiculoBuscado = null;
        for (Iterator<Vehiculo> iterator = get().iterator(); iterator.hasNext(); ) {
            Vehiculo vehiculoLista = iterator.next();
            if (vehiculoLista.equals(vehiculo)) {
                vehiculoBuscado = vehiculoLista;
            }
        }
        return vehiculoBuscado;
    }

    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        boolean vehiculoBorrado = false;
        int indiceVehiculoLista = 0;
        int indiceVehiculoABorrar = 0;
        for (Iterator<Vehiculo> iterator = get().iterator(); iterator.hasNext(); ) {
            Vehiculo vehiculoLista = iterator.next();
            if (vehiculoLista.equals(vehiculo)) {
                indiceVehiculoABorrar = indiceVehiculoLista;
                vehiculoBorrado = true;
            }
            indiceVehiculoLista++;
        }
        if (!vehiculoBorrado) {
            throw new OperationNotSupportedException("No existe ningún vehículo con esa matrícula.");
        } else {
            listaVehiculos.remove(indiceVehiculoABorrar);
        }
    }
}
