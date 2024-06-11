package org.iesalandalus.programacion.tallermecanico.modelo.negocio.mariadb;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private static final String MATRICULA = "matricula";

    private static final String MARCA = "marca";

    private static final String MODELO = "modelo";

    private Connection conexion;

    private static Vehiculos instancia;

    private Vehiculos() {
    }

    static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        conexion = MariaDB.getConexion();
    }

    @Override
    public void terminar() {
        MariaDB.cerrarConexion();
    }

    private Vehiculo getVehiculo(ResultSet registro) throws SQLException {
        return new Vehiculo(registro.getString(MARCA), registro.getString(MODELO), registro.getString(MATRICULA));
    }

    private void prepararSentencia(PreparedStatement sentencia, Vehiculo vehiculo) throws SQLException {
        sentencia.setString(1, vehiculo.marca());
        sentencia.setString(2, vehiculo.modelo());
        sentencia.setString(3, vehiculo.matricula());
    }

    @Override
    public List<Vehiculo> get() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        try (Statement consulta = conexion.createStatement()) {
            ResultSet registros = consulta.executeQuery("select * from vehiculos");
            while (registros.next()) {
                listaVehiculos.add(getVehiculo(registros));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return listaVehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        try (PreparedStatement sentencia = conexion.prepareStatement("insert into vehiculos values (?, ?, ?)")) {
            prepararSentencia(sentencia, vehiculo);
            sentencia.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("Ya existe un vehículo con esa matricula.");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        Vehiculo vehiculoBuscado = null;
        try (PreparedStatement consulta = conexion.prepareStatement("select * from vehiculos where matricula = ?")) {
            consulta.setString(1, vehiculo.matricula());
            ResultSet registro = consulta.executeQuery();
            if (registro.next()) {
                vehiculoBuscado = getVehiculo(registro);
                // vehiculoBuscado = registro.first() ? getVehiculo(registro) : null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return vehiculoBuscado;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehiculo nulo.");
        try (PreparedStatement sentencia = conexion.prepareStatement("delete from vehiculos where matricula = ?")) {
            sentencia.setString(1, vehiculo.matricula());
            int registrosAfectados = sentencia.executeUpdate();
            if (registrosAfectados == 0) {
                throw new OperationNotSupportedException("No existe ningún vehículo con ese DNI.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
