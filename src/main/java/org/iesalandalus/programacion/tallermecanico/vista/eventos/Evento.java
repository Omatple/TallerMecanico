package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE(11, "Insertar cliente."),
    BUSCAR_CLIENTE(12, "Buscar cliente."),
    BORRAR_CLIENTE(13, "Borrar cliente."),
    LISTAR_CLIENTES(14, "Listar clientes."),
    MODIFICAR_CLIENTE(15, "Modificar cliente."),
    INSERTAR_VEHICULO(21, "Insertar veh�culo."),
    BUSCAR_VEHICULO(22, "Buscar veh�culo."),
    BORRAR_VEHICULO(23, "Borrar veh�culo."),
    LISTAR_VEHICULOS(24, "Listar veh�culos."),
    INSERTAR_REVISION(31, "Insertar trabajo de revisi�n."),
    INSERTAR_MECANICO(32, "Insertar trabajo mec�nico."),
    BUSCAR_TRABAJO(33, "Buscar trabajo."),
    BORRAR_TRABAJO(34, "Borrar trabajo."),
    LISTAR_TRABAJOS(35, "Listar trabajos."),
    LISTAR_TRABAJOS_CLIENTE(36, "Listar trabajos de un cliente."),
    LISTAR_TRABAJOS_VEHICULO(37, "Listar trabajos de un veh�culo."),
    ANADIR_HORAS_TRABAJO(38, "A�adir horas a un trabajo."),
    ANADIR_PRECIO_MATERIAL_TRABAJO(39, "A�adir precio del material a un trabajo."),
    CERRAR_TRABAJO(40, "Cerrar trabajo."),
    MOSTRAR_ESTADISTICAS_MENSUALES(41, "Mostrar estad�sticas mensuales."),
    SALIR(0, "Salir.");

    private final int codigo;
    private final String texto;
    private static final Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    private Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public int getCodigo() {
        return codigo;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("El c�digo no es correcto.");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return texto;
    }
}
