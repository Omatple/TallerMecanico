package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {
	
	private static final String MARCA = "Renault";
	private static final String MODELO = "Megane";
	private static final String MATRICULA = "1111BBB";
	
	private final Vehiculo vehiculo = new Vehiculo(MARCA, MODELO, MATRICULA);

	@ParameterizedTest(name = "Cuando llamamos al constructor con marca: {0}, modelo: {1}, matrícula: {2} crea correctamente el vehículo")
	@CsvSource({"Seat, León, 1234BCD", "Land Rover, León, 1234BCD", "KIA, León, 1234BCD", "Rolls-Royce, León, 1234BCD", "SsangYong, León, 1234BCD",
			"Seat, KK, 1234BCD", "Seat, 123, 1234BCD", "Seat, León, 1111BBB", "Seat, León, 9999ZZZ"})
	void constructorMarcaValidaModeloValidoMatrivaValidaCreaVehiculoCorrectamente(String marca, String modelo, String matricula) {
		Vehiculo vehiculo = new Vehiculo(marca, modelo, matricula);
		assertEquals(marca, vehiculo.marca());
		assertEquals(modelo, vehiculo.modelo());
		assertEquals(matricula, vehiculo.matricula());
	}

	@Test
	void constructorMarcaNulaModeloValidoMatrivaValidaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Vehiculo(null, MODELO, MATRICULA));
		assertEquals("La marca no puede ser nula.", npe.getMessage());
	}
	
	@ParameterizedTest(name = "Cuando llamamos al constructor con marca no válida: {0} lanza excepción")
	@CsvSource({"''", "' '", "'   '", "AA-BB", "aa", "aa bb"})
	void constructorMarcaNoValidaModeloValidoMatrivaValidaLanzaExcepcion(String marca) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Vehiculo(marca, MODELO, MATRICULA));
		assertEquals("La marca no tiene un formato válido.", iae.getMessage());
	}

	@Test
	void constructorMarcaValidaModeloNuloMatrivaValidaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Vehiculo(MARCA, null, MATRICULA));
		assertEquals("El modelo no puede ser nulo.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos al constructor con un modelo no válido: {0} lanza excepción")
	@CsvSource({"''", "' '", "'    '"})
	void constructorMarcaValidaModeloNoValidoMatrivaValidaLanzaExcepcion(String modelo) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Vehiculo(MARCA, modelo, MATRICULA));
		assertEquals("El modelo no puede estar en blanco.", iae.getMessage());
	}

	@Test
	void constructorMarcaValidaModeloValidoMatrivaNulaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Vehiculo(MARCA, MODELO, null));
		assertEquals("La matrícula no puede ser nula.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos al constructor con una matrícula no válida: {0} lanza excepción")
	@CsvSource({"''", "' '", "'   '", "123BCD", "12345BCD", "1234ABC", "BCD1234"})
	void constructorMarcaValidaModeloValidoMatrivaNoValidaLanzaExcepcion(String matricula) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,() -> new Vehiculo(MARCA,MODELO,matricula));
		assertEquals("La matrícula no tiene un formato válido.", iae.getMessage());
	}
	
	@Test
	void getVehiculoMatriculaValidaDevuelveVehiculoConDichaMatricula() {
		Vehiculo vehiculo1 = Vehiculo.get(MATRICULA);
		assertEquals(vehiculo , vehiculo1);
	}

	@Test
	void getVehiculoMatriculaNulaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> Vehiculo.get(null));
		assertEquals("La matrícula no puede ser nula.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando llamamos a getVehiculo con una matrícula no válida: {0} lanza excepción")
	@CsvSource({"''", "' '", "'   '", "123BCD", "12345BCD", "1234ABC", "BCD1234"})
	void getVehiculoMatriculaNoValidaLanzaExcepcion(String matricula) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,() -> Vehiculo.get(matricula));
		assertEquals("La matrícula no tiene un formato válido.", iae.getMessage());
	}

	@Test
	void equalsHashCodeSeBasanSoloEnMatricula() {
		assertEquals(vehiculo, Vehiculo.get("1111BBB"));
		assertEquals(vehiculo.hashCode(), Vehiculo.get("1111BBB").hashCode());
		assertNotEquals(Vehiculo.get("1234BCD"), Vehiculo.get("1111BBB"));
		assertNotEquals(Vehiculo.get("1234BCD").hashCode(), Vehiculo.get("1111BBB").hashCode());
	}
	
	@Test
	void toStringDevuelveLaCadenaEsperada() {
		assertEquals(String.format("%s %s - %s", MARCA, MODELO, MATRICULA), vehiculo.toString());
	}

}
