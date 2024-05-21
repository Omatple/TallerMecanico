package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public enum TipoTrabajo {
    MECANICO("Mecánico"),
    REVISION("Revisión");

    private final String nombre;

    TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        TipoTrabajo tipoTrabajo = null;
        if (trabajo instanceof Mecanico) {
            tipoTrabajo = MECANICO;
        } else if (trabajo instanceof Revision) {
            tipoTrabajo = REVISION;
        }
        return tipoTrabajo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
