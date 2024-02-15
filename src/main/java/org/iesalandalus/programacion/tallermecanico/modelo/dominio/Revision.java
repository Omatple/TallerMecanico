package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

// DECIR AL PORFESOR QUE LE HA FALTADO PONER LA CONT DE PRECIO_MATERIAL EN SU DOCUMENTO DEL PROYECTO,
// AUNQUE ES FACIL DE CALCULAR MIRANDO LOS TEST, QUIZAS ALLA GENTE QUENO SEA CAPAZ DE AVERIGURARLO. DECIRSELO

// PREGUNTAR PROFESOR SOBRE GETPRECIO(), IMAGINO QUE EL DUEÑO DEL TALLER O EL CLIENTE
// PUEDE QUERER VER COMO VA EL PRECIO DEL VEHICULO TRAIDO, Y POR ESO NO SE LE DEBE PONER EXCEPCION
// A GETPRECIO() Y GETDIAS() SI TODAVIA NO SE HA CERRADO LA REVISION. YA QUE SE PUEDE DAR UN PRECIO
// AUNQUE NO TENGAMOS EL DIA DE FINALIZACION, YO HABIA PENSADO EN UN INICIO PONER DESDE LA FECHAAINICIO()
// HASTA EL DIA ACTUAL YA QUE COMO NO SE HA CERRADO LA ULTIMA INSTANCIA ES HOY, ENTONCES CALCULARIA EL PRECIO TOTAL
// HASTA EL MOMENTO. SIN EMBARGO AL PONERLO ASI EN EL METOD NO ME PASABA LOS TEST Y HE TENIDO QUE PONER
// QUE SI NO SE HA CERRADO NO CUENTE LOS DIAS Y CALCULE EL RESTO, NECESITO EXPLICACION DEL ¿PORQUE DEBE SER ASI?

// TAMBIEN PREGUNTAR PORQUE PONER O NO NUEVAS INSTANCIAS EN LOS GETS Y SETS(DEBERIA SER NEGATIVO CREAR INSTANCIAS
// NUEVAS CADA VEZ QUE LLAMAS A ESTOS METODOS), EN VEZ DE ELLOS SIMPLEMENTE SE HACE UNA NUEVA  INSTANCIA EN LA COPIA,
// PARA QUE ESTA NO APUNTE AL MISMO OBJETO QUE EL CLIENTE PASADO, ¿SI NO ESTUVIERA LA COPIA, HARIA FALTA PONER INSTANCIAS
// NUEVAS EN EL GET Y SET DE CLIENTE?

public class Revision {
    private static final float PRECIO_HORA = 30;
    private static final float PRECIO_DIA = 10;
    private static final float PRECIO_MATERIAL = 1.5F;
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas = 0;
    private float precioMaterial = 0;
    private Cliente cliente;
    private Vehiculo vehiculo;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    public Revision(Revision revision) {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        this.cliente = new Cliente(revision.getCliente());
        setVehiculo(revision.getVehiculo());
        setFechaInicio(revision.getFechaInicio());
        this.fechaFin = revision.getFechaFin();
        this.horas = revision.getHoras();
        this.precioMaterial = revision.getPrecioMaterial();
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        if (fechaFin.isBefore(getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    private void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public int getHoras() {
        return horas;
    }

    public void anadirHoras(int horas) throws OperationNotSupportedException {
        if (estaCerrada()) {
            throw new OperationNotSupportedException("No se puede añadir horas, ya que la revisión está cerrada.");
        }
        if (horas < 1) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        this.horas += horas;
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

    public void cerrar(LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (estaCerrada()) {
            throw new OperationNotSupportedException("La revisión ya está cerrada.");
        }
        setFechaFin(fechaFin);
    }

    public boolean estaCerrada() {
        return (getFechaFin() != null);
    }

    public float getPrecio() {
        return ((getHoras() * PRECIO_HORA) + (getDias() * PRECIO_DIA) + (getPrecioMaterial() * PRECIO_MATERIAL));
    }

    private float getDias() {
        return (estaCerrada()) ? (ChronoUnit.DAYS.between(getFechaInicio(), getFechaFin())) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return Objects.equals(fechaInicio, revision.fechaInicio) && Objects.equals(cliente, revision.cliente) && Objects.equals(vehiculo, revision.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, cliente, vehiculo);
    }

    @Override
    public String toString() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "ES"));
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", simbolos);
        return (estaCerrada()) ? String.format("%s - %s: (%s - %s), %s horas, %s € en material, %s € total", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.fechaFin.format(FORMATO_FECHA), this.horas, decimalFormat.format(this.precioMaterial), decimalFormat.format(getPrecio())) : String.format("%s - %s: (%s - ), %s horas, %s € en material", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.horas, decimalFormat.format(this.precioMaterial));
    }
}
