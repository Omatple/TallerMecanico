package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

	private static final String NOMBRE = "Patricio Estrella";
	private static final String DNI = "11111111H";
	private static final String TELEFONO = "950111111";
	
	private final Cliente cliente = new Cliente(NOMBRE, DNI, TELEFONO);

	@ParameterizedTest(name = "Cuando llamamos al constructor con nombre: {0}, dni: {1}, teléfono: {2} crea el cliente correctamente")
	@CsvSource({"José, 11223344B, 123456789", "José Ramón, 11223344B, 123456789", "José Ramón Jiménez, 11223344B, 123456789", "José Ramón Jiménez Reyes, 11223344B, 123456789",
			"José, 11111111H, 123456789", "José, 11223344B, 111111111", "José, 11223344B, 999999999"})
	void constructorNombreValidoDniValidoTelefonoValidoCreaClienteCorrectamente(String nombre, String dni, String telefono) {
		Cliente cliente = new Cliente(nombre, dni, telefono);
		assertEquals(nombre, cliente.getNombre());
		assertEquals(dni, cliente.getDni());
		assertEquals(telefono, cliente.getTelefono());
	}

	@Test
	void constructorNombreNuloDniValidoTelefonoValidoLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Cliente(null, DNI, TELEFONO));
		assertEquals("El nombre no puede ser nulo.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos al constructor con un nombre no válido: {0} lanza una excepción")
	@CsvSource({"''", "' '", "'   '", "bob esponja", "BOB ESPONJA", "Bob  Esponja"})
	void constructorNombreNoValidoDniValidoTelefonoValidoLanzaExcepcion(String nombre) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Cliente(nombre, DNI, TELEFONO));
		assertEquals("El nombre no tiene un formato válido.", iae.getMessage());
	}

	@Test
	void constructoNombreValidoDniNuloTelefonoValidoLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Cliente(NOMBRE, null, TELEFONO));
		assertEquals("El DNI no puede ser nulo.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos al constructor con un dni no válido: {0} lanza una excepción")
	@CsvSource({"''", "' '", "'   '", "1234567A", "123456789A", "12345678"})
	void constructorNombreValidoDniNoValidoTelefonoValidoLanzaExcepcion(String dni) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Cliente(NOMBRE, dni, TELEFONO));
		assertEquals("El DNI no tiene un formato válido.", iae.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos al constructor con un dni cuya letra no es válida: {0} lanza una excepción")
	@CsvSource({"11111111V", "11111111L"})
	void constructorNombreValidoLetraDniNoValidaTelefonoValidoLanzaExcepcion(String dni) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Cliente(NOMBRE, dni, TELEFONO));
		assertEquals("La letra del DNI no es correcta.", iae.getMessage());
	}

	@Test
	void constructorNombreValidoDniValidoTelefonoNuloLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Cliente(NOMBRE, DNI, null));
		assertEquals("El teléfono no puede ser nulo.", npe.getMessage());
	}
	
	@ParameterizedTest(name = "Cuando llamamos al constructor con un teléfono no válido {0} lanza una excepción")
	@CsvSource({"''", "' '", "'   '", "12345678", "1234567890", "abcdefghi", "ABCDEFGHI"})
	void constructorNombreValidoDniValidoTelefonoNoValidoLanzaExcepcion(String telefono) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Cliente(NOMBRE, DNI, telefono));
		assertEquals("El teléfono no tiene un formato válido.", iae.getMessage());
	}
	
	@Test
	void constrctorClienteValidoCopiaClienteCorrectamente() {
		Cliente clienteCopia = new Cliente(cliente);
		assertEquals(cliente, clienteCopia);
		assertNotSame(cliente, clienteCopia);
	}

	@Test
	void constructorClienteNuloLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Cliente(null));
		assertEquals("No es posible copiar un cliente nulo.", npe.getMessage());
	}
	
	@Test
	void getClienteDniValidoDevuelveClienteConDichoDni() {
		Cliente cliente1 = Cliente.get(DNI);
		assertEquals(cliente, cliente1);
	}

	@Test
	void getClienteDniNuloLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> Cliente.get(null));
		assertEquals("El DNI no puede ser nulo.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos a getClienteConDni con un dni no válido: {0} lanza una excepción")
	@CsvSource({"''", "' '", "'   '", "1234567A", "123456789A", "12345678"})
	void getClienteDniNoValidoLanzaExcepcion(String dni) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> Cliente.get(dni));
		assertEquals("El DNI no tiene un formato válido.", iae.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos a getClienteConDni con un dni cuya letra no es válida: {0} lanza una excepción")
	@CsvSource({"11111111V", "11111111L"})
	void getClienteLetraDniNoValidaLanzaExcepcion(String dni) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> Cliente.get(dni));
		assertEquals("La letra del DNI no es correcta.", iae.getMessage());
	}

	@Test
	void equalsHashCodeSeBasanSoloEnDni() {
		assertEquals(cliente, Cliente.get("11111111H"));
		assertEquals(cliente.hashCode(), Cliente.get("11111111H").hashCode());
		assertNotEquals(Cliente.get("11223344B"), Cliente.get("11111111H"));
		assertNotEquals(Cliente.get("11223344B").hashCode(), Cliente.get("11111111H").hashCode());
	}
	
	@Test
	void toStringDevuelveLaCadenaEsperada() {
		assertEquals(String.format("%s - %s (%s)", NOMBRE, DNI, TELEFONO), cliente.toString());
	}

}
