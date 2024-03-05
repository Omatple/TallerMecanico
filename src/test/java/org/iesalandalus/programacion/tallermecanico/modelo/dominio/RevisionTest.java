package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RevisionTest {

    private static Cliente cliente;
    private static Vehiculo vehiculo;
    private static LocalDate hoy;
    private static LocalDate ayer;
    private static LocalDate manana;
    private static LocalDate semanaPasada;

    private Revision revision;

    @BeforeAll
    static void setup() {
        hoy = LocalDate.now();
        ayer = hoy.minusDays(1);
        manana = hoy.plusDays(1);
        semanaPasada = hoy.minusDays(7);
    }

    @BeforeEach
    void init() {
        creaComportamientoCliente();
        creaComportamientoVehiculo();
        revision = new Revision(cliente, vehiculo, ayer);
    }

    private void creaComportamientoVehiculo() {
        vehiculo = mock();
        when(vehiculo.marca()).thenReturn("Seat");
        when(vehiculo.modelo()).thenReturn("León");
        when(vehiculo.matricula()).thenReturn("1234BCD");
    }

    private void creaComportamientoCliente() {
        cliente = mock();
        when(cliente.getNombre()).thenReturn("Bob Esponja");
        when(cliente.getDni()).thenReturn("11223344B");
        when(cliente.getTelefono()).thenReturn("950112233");
    }

    @Test
    void constructorClienteValidoVehiculoValidoFechaInicioValidaCreaRevisionCorrectamente() {
        assertEquals(cliente, revision.getCliente());
        assertSame(cliente, revision.getCliente());
        assertEquals(vehiculo, revision.getVehiculo());
        assertSame(vehiculo, revision.getVehiculo());
        assertEquals(ayer, revision.getFechaInicio());
        assertNull(revision.getFechaFin());
        assertEquals(0, revision.getHoras());
        assertEquals(0, revision.getPrecioMaterial());
        assertEquals(0, revision.getPrecio());
        Revision revisonSemanaPasada = new Revision(cliente, vehiculo, semanaPasada);
        assertEquals(semanaPasada, revisonSemanaPasada.getFechaInicio());
    }

    @Test
    void constructorClienteNuloTurismoValidoFechaInicioValidaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(null, vehiculo, hoy));
        assertEquals("El cliente no puede ser nulo.", npe.getMessage());
    }

    @Test
    void constructorClienteValidoTurismoNuloFechaInicioValidaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(cliente, null, hoy));
        assertEquals("El vehículo no puede ser nulo.", npe.getMessage());
    }

    @Test
    void constructorClienteValidoTurismoValidoFechaInicioNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(cliente, vehiculo, null));
        assertEquals("La fecha de inicio no puede ser nula.", npe.getMessage());
    }

    @Test
    void constructorClienteValidoTurismoValidoFechaInicioNoValidaLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Revision(cliente, vehiculo, manana));
        assertEquals("La fecha de inicio no puede ser futura.", iae.getMessage());
    }

    @Test
    void constructorRevisionValidaCopiaRevisionCorrectamente() {
        assertDoesNotThrow(() -> revision.anadirHoras(5));
        assertDoesNotThrow(() -> revision.anadirPrecioMaterial(100));
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        Revision revisionCopia = new Revision(revision);
        assertNotSame(cliente, revisionCopia.getCliente());
        assertSame(vehiculo, revisionCopia.getVehiculo());
        assertEquals(ayer, revisionCopia.getFechaInicio());
        assertEquals(hoy, revisionCopia.getFechaFin());
        assertEquals(5, revisionCopia.getHoras());
        assertEquals(100, revisionCopia.getPrecioMaterial());
    }

    @Test
    void constructorRevisionNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(null));
        assertEquals("La revisión no puede ser nula.", npe.getMessage());
    }

    @Test
    void anadirHorasHorasValidasSumaHorasCorrectamente() {
        assertDoesNotThrow(() -> revision.anadirHoras(5));
        assertEquals(5, revision.getHoras());
        assertDoesNotThrow(() -> revision.anadirHoras(5));
        assertEquals(10, revision.getHoras());
    }

    @Test
    void anadirHorasHorasNoValidasLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> revision.anadirHoras(0));
        assertEquals("Las horas a añadir deben ser mayores que cero.", iae.getMessage());
    }

    @Test
    void anadirHorasRevisionCerradaLanzaExcepcion() {
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> revision.anadirHoras(5));
        assertEquals("No se puede añadir horas, ya que la revisión está cerrada.", onse.getMessage());
    }

    @Test
    void anadirPrecioMaterialPrecioMaterialValidoSumaPrecioMaterialCorrectamente() {
        assertDoesNotThrow(() -> revision.anadirPrecioMaterial(100));
        assertEquals(100, revision.getPrecioMaterial());
        assertDoesNotThrow(() -> revision.anadirPrecioMaterial(100));
        assertEquals(200, revision.getPrecioMaterial());
    }

    @Test
    void anadirPrecioMaterialPrecioMaterialNoValidoLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> revision.anadirPrecioMaterial(0));
        assertEquals("El precio del material a añadir debe ser mayor que cero.", iae.getMessage());
    }

    @Test
    void anadirPrecioMaterialRevisionCerradaLanzaExcepcion() {
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> revision.anadirPrecioMaterial(100));
        assertEquals("No se puede añadir precio del material, ya que la revisión está cerrada.", onse.getMessage());
    }

    @Test
    void cerrarFechaFinValidaCierraCorrectamente() {
        assertFalse(revision.estaCerrada());
        assertNull(revision.getFechaFin());
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        assertTrue(revision.estaCerrada());
        assertEquals(hoy, revision.getFechaFin());
    }

    @Test
    void cerrarFechaFinNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> revision.cerrar(null));
        assertEquals("La fecha de fin no puede ser nula.", npe.getMessage());
    }

    @Test
    void cerrarFechaFinFuturaLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> revision.cerrar(manana));
        assertEquals("La fecha de fin no puede ser futura.", iae.getMessage());
    }

    @Test
    void cerrarFechaFinAnteriorFechaInicioLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> revision.cerrar(semanaPasada));
        assertEquals("La fecha de fin no puede ser anterior a la fecha de inicio.", iae.getMessage());
    }

    @Test
    void cerrarRevisionCerradaLanzaExcepcion() {
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> revision.cerrar(hoy));
        assertEquals("La revisión ya está cerrada.", onse.getMessage());
    }

    @ParameterizedTest(name = "Cuando llamamos a getPrecio y se han añadido {0} horas y {1} precio de material calcula el precio como {2}")
    @CsvSource({"0, 1, 1, 31.5", "1, 1, 1, 41.5", "5, 1, 1, 81.5", "0, 2, 2, 63.0", "1, 2, 2, 73.0", "5, 2, 2, 113.0",
            "0, 5, 5, 157.5", "1, 5, 5, 167.5", "5, 5, 5, 207.5", "0, 10, 10, 315.0", "1, 10, 10, 325.0", "5, 10, 10, 365.0",
            "0, 10, 100, 450.0", "1, 10, 100, 460.0", "5, 10, 100, 500.0"})
    void getPrecioCalculaCorrectamentePrecio(int dias, int horas, float precioMaterial, float precio) {
        Revision revisonSemanaPasada = new Revision(cliente, vehiculo, semanaPasada);
        assertDoesNotThrow(() -> revisonSemanaPasada.anadirHoras(horas));
        assertDoesNotThrow(() -> revisonSemanaPasada.anadirPrecioMaterial(precioMaterial));
        LocalDate fechaFin = semanaPasada.plusDays(dias);
        assertDoesNotThrow(() -> revisonSemanaPasada.cerrar(fechaFin));
        assertEquals(precio, revisonSemanaPasada.getPrecio());
    }

    @Test
    void equalsHashCodeSeBasanSoloEnClienteVehiculoFechaInicio() {
        Revision otraRevision = new Revision(cliente, vehiculo, ayer);
        assertEquals(revision, otraRevision);
        assertEquals(revision.hashCode(), otraRevision.hashCode());
        assertDoesNotThrow(() -> otraRevision.cerrar(hoy));
        assertEquals(revision, otraRevision);
        assertEquals(revision.hashCode(), otraRevision.hashCode());
    }

    @Test
    void toStringDevuelveLaCadenaEsperada() {
        String cadenaCliente = "Bob Esponja - 11223344B (950112233)";
        String caenaVehiculo = "Seat León - 1234BCD";
        when(cliente.toString()).thenReturn(cadenaCliente);
        when(vehiculo.toString()).thenReturn(caenaVehiculo);
        String cadenaAyer = ayer.format(Revision.FORMATO_FECHA);
        String cadenaHoy = hoy.format(Revision.FORMATO_FECHA);
        String cadena = String.format("%s - %s: (%s - ), 0 horas, 0,00 € en material", cadenaCliente, caenaVehiculo, cadenaAyer);
        assertEquals(cadena, revision.toString());
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        cadena = String.format("%s - %s: (%s - %s), 0 horas, 0,00 € en material, 10,00 € total", cadenaCliente, caenaVehiculo, cadenaAyer, cadenaHoy);
        assertEquals(cadena, revision.toString());
    }

}