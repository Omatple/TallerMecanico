package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Objects;

public class Mecanico extends Trabajo {
    private static final float PRECIO_HORA = 30;
    private static final float PRECIO_MATERIAL = 1.5F;
    private float precioMaterial = 0;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    public Mecanico(Mecanico revision) {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        this.cliente = new Cliente(revision.getCliente());
        this.vehiculo = revision.getVehiculo();
        this.fechaInicio = revision.getFechaInicio();
        this.fechaFin = revision.getFechaFin();
        this.horas = revision.getHoras();
        this.precioMaterial = revision.getPrecioMaterial();
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws OperationNotSupportedException {
        if (estaCerrada()) {
            throw new OperationNotSupportedException("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        this.precioMaterial += precioMaterial;
    }

    @Override
    public String toString() {
        return (estaCerrada()) ? String.format("%s - %s: (%s - %s), %s horas, %.2f € en material, %.2f € total", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.fechaFin.format(FORMATO_FECHA), this.horas, this.precioMaterial, getPrecio()) : String.format("%s - %s: (%s - ), %s horas, %.2f € en material", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.horas, this.precioMaterial);
    }
}