package org.iesalandalus.programacion.tallermecanico.modelo.negocio.mongodb;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

	private static final String SERVIDOR = "localhost";
	private static final int PUERTO = 27017;
	private static final String BD = "tallerMecanico";
	private static final String USUARIO = "taller";
	private static final String CONTRASENA = "taller";
	private static final String URI = String.format("mongodb://%s:%s@%s:%d", USUARIO, CONTRASENA, SERVIDOR, PUERTO);
	
	private static MongoClient conexion;

	private MongoDB() {
	}

	public static MongoDatabase getBD() {
		if (conexion == null) {
			establecerConexion();
		}
		return conexion.getDatabase(BD);
	}

	private static void establecerConexion() {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		if (conexion == null) {
			System.setErr(new PrintStream(new ByteArrayOutputStream()));
			conexion = MongoClients.create(MongoClientSettings.builder()
					.applyConnectionString(new ConnectionString(URI))
					.applyToClusterSettings(builder -> builder.serverSelectionTimeout(5, TimeUnit.SECONDS)).build());
			comparbarMongoEstaVivo();
			System.out.println("Conexi�n a MongoDB realizada correctamente.");
		}
	}

	private static void comparbarMongoEstaVivo() {
		Bson ping = new BasicDBObject("ping", "1");
		try {
			conexion.getDatabase(BD).runCommand(ping);
		} catch (MongoException e) {
			System.out.printf("No se ha podido establecer la conexi�n con MongoDB: %s%n",e.getMessage());
			System.exit(-1);
		}
	}

	public static void cerrarConexion() {
		if (conexion != null) {
			conexion.close();
			conexion = null;
			System.out.println("Conexi�n a MongoDB cerrada.");
		}
	}
}
