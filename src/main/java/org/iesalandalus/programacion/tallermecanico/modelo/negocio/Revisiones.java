package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;
// AUN NO HAGO COMMIT DE ESTA CLASE PORQUE NO ME PASA TODOS LOS TEST

// LLEVO DEMASIADO TIEMPO INTENTANDO SOLUCIONAR EL ERROR DEL TEST EN EL METODO INSTERTAR, QUE IMAGINO QUE
// PROVIENE DEL METODO COMPROBAR, SIN EMBARGO POR MAS VUELTAS QUE LE DE A ESE METODO, PIENSO QUE ESTA BIEN ESTRUCUTURADO,
// Y LANZA LAS EXCEPCIONES QUE DEBE LANZAR. PERO DA IGUAL LO QUE PIENSE PORQUE LOS TEST MANDAN, Y SI DICE QUE ESTA MAL LO ESTARA,
// LE PREGUNTARE AL PROFESOR O PORFESORA PARA QUE ME AYUDE A RESOLVERLO PORQUE POR MI CUENTA NO HE SIDO CAPAZ

public class Revisiones {
    private final List<Revision> listaRevisiones;

    public Revisiones() {
        listaRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        List<Revision> nuevaLista = listaRevisiones;
        return nuevaLista;
    }

    public List<Revision> get(Cliente cliente) {
        List<Revision> nuevaLista = new ArrayList<>();
        for (Iterator<Revision> iterator = get().iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            if (revisionLista.getCliente().equals(cliente)) {
                nuevaLista.add(revisionLista);
            }
        }
        return nuevaLista;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> nuevaLista = new ArrayList<>();
        for (Iterator<Revision> iterator = get().iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            if (revisionLista.getVehiculo().equals(vehiculo)) {
                nuevaLista.add(revisionLista);
            }
        }
        return nuevaLista;
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        listaRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws OperationNotSupportedException {
        for (Iterator<Revision> iterator = listaRevisiones.iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            if(revisionLista.getCliente().equals(cliente)) {
                if (!revisionLista.estaCerrada()) {
                    throw new OperationNotSupportedException("El cliente tiene otra revisión en curso.");
                } else if (!fechaRevision.isAfter(revisionLista.getFechaFin())) {
                    throw new OperationNotSupportedException("El cliente tiene una revisión posterior.");
                }
            }
            if(revisionLista.getVehiculo().equals(vehiculo)){
                if (!revisionLista.estaCerrada()) {
                    throw new OperationNotSupportedException("El vehículo está actualmente en revisión.");
                } else if (!fechaRevision.isAfter(revisionLista.getFechaFin())) {
                    throw new OperationNotSupportedException("El vehículo tiene una revisión posterior.");
                }
            }
        }
    }

    private Revision getRevision(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        int indiceRevisionesLista = 0;
        int indiceRevisionBuscada = 0;
        boolean existeRevision = false;
        for (Iterator<Revision> iterator = get().iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            if (revisionLista.equals(revision)) {
                indiceRevisionBuscada = indiceRevisionesLista;
                existeRevision = true;
            }
            indiceRevisionesLista++;
        }
        if (!existeRevision) {
            throw new OperationNotSupportedException("No existe ninguna revisión igual.");
        }
        return listaRevisiones.get(indiceRevisionBuscada);
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        if (horas < 1) {
            throw new IllegalArgumentException("No puedes ingresar horas negativas.");
        }
        getRevision(revision).anadirHoras(horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material no puede ser inferior a 0.");
        }
        getRevision(revision).anadirPrecioMaterial(precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(fechaFin, "No puedo operar sobre una fecha de fin nula.");
        getRevision(revision).cerrar(fechaFin);
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        Revision revisionBuscada = null;
        for (Iterator<Revision> iterator = get().iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            if (revisionLista.equals(revision)) {
                revisionBuscada = revisionLista;
            }
        }
        return revisionBuscada;
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        int indiceListaRevisiones = 0;
        int indiceRevisionABorrar = 0;
        boolean revisionBorrada = false;
        for (Iterator<Revision> iterator = get().iterator(); iterator.hasNext(); ) {
            Revision revisionLista = iterator.next();
            if (revisionLista.equals(revision)) {
                indiceRevisionABorrar = indiceListaRevisiones;
                revisionBorrada = true;
            }
            indiceListaRevisiones++;
        }
        if (!revisionBorrada) {
            throw new OperationNotSupportedException("No existe ninguna revisión igual.");
        } else {
            listaRevisiones.remove(indiceRevisionABorrar);
        }
    }
}
