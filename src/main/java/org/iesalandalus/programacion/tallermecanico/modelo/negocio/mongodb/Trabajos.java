package org.iesalandalus.programacion.tallermecanico.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.mongodb.Vehiculos;

import javax.naming.OperationNotSupportedException;
import javax.swing.text.Element;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

public class Trabajos implements ITrabajos {

    private static final String COLECCCION = "trabajos";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String TIPO = "tipo";
    private static final String REVISION = "Revisión";
    private static final String MECANICO = "Mecánico";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String DNI_CLIENTE = "cliente.dni";
    private static final String MATRICULA_VEHICULO = "vehiculo.matricula";


    private MongoCollection<Document> coleccionTrabajos;
    private static Trabajos instancia;

    private Trabajos() {}

    public static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        coleccionTrabajos = MongoDB.getBD().getCollection(COLECCCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }

    private LocalDate toLocalDate(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate(LocalDate fecha) {
        return Date.from(fecha.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Trabajo getTrabajo(Document documento) {
        Trabajo trabajo = null;
        if (documento != null) {
            Cliente cliente = Clientes.getInstancia().getCliente((Document) documento.get(CLIENTE));
            Vehiculo vehiculo = Vehiculos.getInstancia().getVehiculo((Element) documento.get(VEHICULO));
            // CAMIBAR CASTIING ELEMENT A DOCUMENT
            LocalDate fechaInicio = toLocalDate(documento.getDate(FECHA_INICIO));
            String tipo = documento.getString(TIPO);
            try {
                if (tipo.equals(REVISION)) {
                    trabajo = new Revision(cliente, vehiculo, fechaInicio);
                } else if (tipo.equals(MECANICO)){
                    trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
                    if (documento.containsKey(PRECIO_MATERIAL)) {
                        double precioMaterial = documento.getDouble(PRECIO_MATERIAL);
                        ((Mecanico) trabajo).anadirPrecioMaterial((float) precioMaterial);
                    }
                }
                if (documento.containsKey(HORAS)) {
                    int horas = documento.getInteger(HORAS);
                    trabajo.anadirHoras(horas);
                }
                if (documento.containsKey(FECHA_FIN)) {
                    LocalDate fechaFin = toLocalDate(documento.getDate(FECHA_FIN));
                    trabajo.cerrar(fechaFin);
                }
            } catch (OperationNotSupportedException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return trabajo;
    }

    private Document getDocumento(Trabajo trabajo) {
        Document documento = null;
        if (trabajo != null) {
            Document documentoCliente = Clientes.getInstancia().getDocumento(trabajo.getCliente());
            Document documentoVehiculo = (Document) Vehiculos.getInstancia().getDocumento(trabajo.getVehiculo());
            //QUITAR CASTING DOCUMENT <--
            Date fechaInicio = toDate(trabajo.getFechaInicio());
            TipoTrabajo tipoTrabajo = TipoTrabajo.get(trabajo);
            documento = new Document().append(CLIENTE, documentoCliente).append(VEHICULO, documentoVehiculo).append(FECHA_INICIO, fechaInicio).append(TIPO, tipoTrabajo.toString());
            if (trabajo.getHoras() != 0) {
                documento.append(HORAS, trabajo.getHoras());
            }
            if (trabajo instanceof Mecanico mecanico && mecanico.getPrecioMaterial() != 0) {
                documento.append(PRECIO_MATERIAL, mecanico.getPrecioMaterial());
            }
            if (trabajo.getFechaFin() != null) {
                documento.append(FECHA_FIN, toDate(trabajo.getFechaFin()));
            }
        }
        return documento;
    }

    private Bson getCriterioBusqueda(Trabajo trabajo) {
        Date fechaInicio = toDate(trabajo.getFechaInicio());
        return and(eq(DNI_CLIENTE, trabajo.getCliente().getDni()),
                eq(MATRICULA_VEHICULO, trabajo.getVehiculo().matricula()),
                eq(FECHA_INICIO, fechaInicio));
    }

    private Bson getCriterioBusqueda(Cliente cliente) {
        return eq(DNI_CLIENTE, cliente.getDni());
    }

    private Bson getCriterioBusqueda(Vehiculo vehiculo) {
        return eq(MATRICULA_VEHICULO, vehiculo.matricula());
    }

    @Override
    public List<Trabajo> get() {
        List<Trabajo> trabajos = new ArrayList<>();
        for (Document documento : coleccionTrabajos.find()) {
            trabajos.add(getTrabajo(documento));
        }
        return trabajos;
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajos = new ArrayList<>();
        for (Document documento : coleccionTrabajos.find(getCriterioBusqueda(cliente))) {
            trabajos.add(getTrabajo(documento));
        }
        return trabajos;    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajos = new ArrayList<>();
        for (Document documento : coleccionTrabajos.find(getCriterioBusqueda(vehiculo))) {
            trabajos.add(getTrabajo(documento));
        }
        return trabajos;    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        Objects.requireNonNull(mes, "El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> estadisticas = inicializarEstadisticas();
        LocalDate inicioMes = LocalDate.of(mes.getYear(), mes.getMonth(), 1);
        LocalDate finMes = LocalDate.of(mes.getYear(), mes.getMonth(), mes.lengthOfMonth());
        Bson filtro = and(gte(FECHA_INICIO, toDate(inicioMes)), lte(FECHA_INICIO, toDate(finMes)));
        for (Document documento : coleccionTrabajos.find(filtro)) {
            TipoTrabajo tipoTrabajo = TipoTrabajo.get(getTrabajo(documento));
            estadisticas.put(tipoTrabajo, estadisticas.get(tipoTrabajo) + 1);
        }
        return estadisticas;
    }

    private Map<TipoTrabajo, Integer> inicializarEstadisticas() {
        Map<TipoTrabajo, Integer> estadisticas = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()) {
            estadisticas.put(tipoTrabajo, 0);
        }
        return estadisticas;
    }

    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.insertOne(getDocumento(trabajo));
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) throws OperationNotSupportedException {
        Bson filtro = and(getCriterioBusqueda(cliente), exists(FECHA_FIN, false));
        if (coleccionTrabajos.find(filtro).first() != null) {
            throw new OperationNotSupportedException("El cliente tiene otro trabajo en curso.");
        }
        filtro = and(getCriterioBusqueda(vehiculo), exists(FECHA_FIN, false));
        if (coleccionTrabajos.find(filtro).first() != null) {
            throw new OperationNotSupportedException("El vehículo está actualmente en el taller.");
        }
        filtro = and(getCriterioBusqueda(cliente), exists(FECHA_FIN, true), gte(FECHA_FIN, toDate(fechaAlquiler)));
        if (coleccionTrabajos.find(filtro).first() != null) {
            throw new OperationNotSupportedException("El cliente tiene otro trabajo posterior.");
        }
        filtro = and(getCriterioBusqueda(vehiculo), exists(FECHA_FIN, true), gte(FECHA_FIN, toDate(fechaAlquiler)));
        if (coleccionTrabajos.find(filtro).first() != null) {
            throw new OperationNotSupportedException("El vehículo tiene otro trabajo posterior.");
        }
    }
    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Bson filtro = and(getCriterioBusqueda(trabajo), exists(FECHA_FIN, false));
        UpdateResult resultado = coleccionTrabajos.updateOne(filtro, set(HORAS, horas));
        trabajo.anadirHoras(horas);
        if (resultado.getMatchedCount() == 0) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto igual.");
        }
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Bson filtro = and(getCriterioBusqueda(trabajo), exists(FECHA_FIN, false), eq(TIPO, MECANICO));
        UpdateResult resultado = coleccionTrabajos.updateOne(filtro, set(PRECIO_MATERIAL, precioMaterial));
        ((Mecanico)trabajo).anadirPrecioMaterial(precioMaterial);
        if (resultado.getMatchedCount() == 0) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto igual.");
        }
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo cerrar a un trabajo nulo.");
        Bson filtro = and(getCriterioBusqueda(trabajo), exists(FECHA_FIN, false));
        UpdateResult resultado = coleccionTrabajos.updateOne(filtro, set(FECHA_FIN, toDate(fechaFin)));
        trabajo.cerrar(fechaFin);
        if (resultado.getMatchedCount() == 0) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto igual.");
        }
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        return getTrabajo(coleccionTrabajos.find(getCriterioBusqueda(trabajo)).first());
    }

    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        DeleteResult resultado = coleccionTrabajos.deleteOne(getCriterioBusqueda(trabajo));
        if (resultado.getDeletedCount() == 0) {
            throw new OperationNotSupportedException("No existe ningún trabajo igual.");
        }
    }

    void modificarCliente(Document cliente) {
        Bson filtro = eq(DNI_CLIENTE, cliente.get("dni"));
        Bson modificacion = set(CLIENTE, cliente);
        coleccionTrabajos.updateMany(filtro, modificacion);
    }
}
