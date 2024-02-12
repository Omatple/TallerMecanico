package org.iesalandalus.programacion.tallermecanico.modelo.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consola {

    private static final String CADENA_FORMATO_FECHA = "yyyy-MM-dd";

    private Consola() {
    }

    public static void mostrarCabecera(String mensaje) {
        StringBuilder subrayadoMensaje = new StringBuilder();
        if (mensaje.isBlank() || mensaje == null) {
            System.out.println("El mensaje no puede estar vacio, ni ser nulo.");
        } else {
            System.out.println(mensaje);
            for (int i = 0; i < mensaje.length(); i++) {
                subrayadoMensaje.append("-");
            }
            System.out.println(subrayadoMensaje);
        }
    }

    public static void mostrarMenu() {
        mostrarCabecera("Aplicación del taller mecánico. Uso para la gestión de clientes, vehículos y revisiones");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.toString());
        }
    }

    private static float leerReal(String mensaje) {
        float entradaReal = 0;
        if (mensaje.isBlank() || mensaje == null) {
            System.out.println("El mensaje no puede estar vacio, ni ser nulo.");
        } else {
            System.out.println(mensaje);
            System.out.print("A continuación escriba un número real: ");
            entradaReal = Entrada.real();
            while (entradaReal <= 0) {
                System.out.println("ERROR: El número real debe ser mayor que 0. Intentelo de nuevo.");
                System.out.print("A continuación escriba un número real: ");
                entradaReal = Entrada.real();
            }
        }
        return entradaReal;
    }

    private static int leerEntero(String mensaje) {
        int entradaEntero = 0;
        if (mensaje.isBlank() || mensaje == null) {
            System.out.println("El mensaje no puede estar vacio, ni ser nulo.");
        } else {
            System.out.println(mensaje);
            System.out.print("A continuación escriba un número entero: ");
            entradaEntero = Entrada.entero();
            while (entradaEntero <= 0) {
                System.out.println("ERROR: El número entero debe ser mayor que 0. Intentelo de nuevo.");
                System.out.print("A continuación escriba un número entero: ");
                entradaEntero = Entrada.entero();
            }
        }
        return entradaEntero;
    }

    private static String leerCadena(String mensaje) {
        String entradaCadena = "ERROR: Cadena no esperada.";
        if (mensaje.isBlank() || mensaje == null) {
            System.out.println("El mensaje no puede estar vacio, ni ser nulo.");
        } else {
            System.out.println(mensaje);
            System.out.print("A continuación escriba una cadena: ");
            entradaCadena = Entrada.cadena();
            while (entradaCadena.isBlank() || entradaCadena == null) {
                System.out.println("ERROR: La cadena no puede estar vacia, ni ser nula. Intentalo de nuevo.");
                System.out.print("A continuación escriba una cadena: ");
                entradaCadena = Entrada.cadena();
            }
        }
        return entradaCadena;
    }

    private static String leerFecha(String mensaje) {
        String entradaFecha = "ERROR: Fecha no esperada.";
        Pattern patron = Pattern.compile(CADENA_FORMATO_FECHA);
        Matcher comparador = patron.matcher(entradaFecha);
        if (mensaje.isBlank() || mensaje == null) {
            System.out.println("El mensaje no puede estar vacio, ni ser nulo.");
        } else {
            System.out.println(mensaje);
            System.out.print("A continuación escriba una fecha: ");
            entradaFecha = Entrada.cadena();
            while (!comparador.matches() || entradaFecha == null) {
                System.out.println("ERROR: La fecha no tiene un formato valido(año(aaaa)-mes(mm)-dia(dd)) o es nula. Intentelo de nuevo.");
                System.out.print("A continuación escriba una fecha: ");
                entradaFecha = Entrada.cadena();
            }
        }
        return entradaFecha;
    }

    public static Opcion elegirOpcion() {
        int numeroOpcion;
        numeroOpcion = leerEntero("Eliga el número de la opción que desee ejecutar -> ");
        boolean numeroValido = Opcion.esValida(numeroOpcion);
        while (!numeroValido) {
            System.out.println("ERROR: Número de la opción invalido. Intentelo de nuevo.");
            numeroOpcion = leerEntero("Eliga el número de la opción que desee ejecutar -> ");
        }
        return Opcion.get(numeroOpcion);
    }

    public static Cliente leerCliente() {
        Cliente clienteIngresado;
        String nombreCliente;
        String telefonoCliente;
        System.out.println("Introduzca los datos del cliente -> ");
        clienteIngresado = leerClienteDni();
        nombreCliente = leerClienteNombre();
        telefonoCliente = leerClienteTelefono();
        if (!(nombreCliente.isBlank() || nombreCliente == null)) {
            clienteIngresado.setNombre(nombreCliente);
        }
        if (!(telefonoCliente.isBlank() || telefonoCliente == null)) {
            clienteIngresado.setTelefono(telefonoCliente);
        }
        return new Cliente(clienteIngresado);
    }

    public static Cliente leerClienteDni() {
        String dniCliente;
        dniCliente = leerCadena("Ingrese el DNI -> ");
        return new Cliente(Cliente.get(dniCliente));
    }

    public static String leerClienteNombre() {
        String nombreCliente;
        nombreCliente = leerCadena("Ingrese el nombre -> ");
        return nombreCliente;
    }

    public static String leerClienteTelefono() {
        String telefonoCliente;
        telefonoCliente = leerCadena("Ingrese el número de telefono -> ");
        return telefonoCliente;
    }

    public static Vehiculo leerVehiculo() {
        Vehiculo vehiculoIngresado;
        String marcaVehiculo;
        String modeloVehiculo;
        System.out.println("Introduzca los datos del vehículo -> ");
        marcaVehiculo = leerCadena("Ingrese la marca del vehículo -> ");
        modeloVehiculo = leerCadena("Ingrese el modelo del vehículo -> ");
        vehiculoIngresado = leerVehiculoMatricula();
        if (!(marcaVehiculo.isBlank() || marcaVehiculo == null)) {
            vehiculoIngresado = new Vehiculo(marcaVehiculo, vehiculoIngresado.modelo(), vehiculoIngresado.matricula());
        }
        if (!(modeloVehiculo.isBlank() || modeloVehiculo == null)) {
            vehiculoIngresado = new Vehiculo(vehiculoIngresado.marca(), modeloVehiculo, vehiculoIngresado.matricula());
        }
        return vehiculoIngresado;
    }

    public static Vehiculo leerVehiculoMatricula() {
        String matriculaVehiculo;
        matriculaVehiculo = leerCadena("Ingrese la matrícula del vehículo -> ");
        return Vehiculo.get(matriculaVehiculo);
    }

    public static Revision leerRevision() {
        Revision revisionIngresada;
        Cliente clienteRevision;
        Vehiculo vehiculoRevision;
        LocalDate fechaInicioRevision;
        System.out.println("Introduzca los datos de la revisión -> ");
        clienteRevision = leerCliente();
        vehiculoRevision = leerVehiculo();
        fechaInicioRevision = LocalDate.parse(leerFecha("Ingrese la fecha de inicio de la revisión -> "));
        revisionIngresada = new Revision(clienteRevision, vehiculoRevision, fechaInicioRevision);
        return new Revision(revisionIngresada);
    }

    public static int leerHoras() {
        int cantidadhorasaIngresada;
        cantidadhorasaIngresada = leerEntero("Introduzca las horas que desee añadir a la revisión -> ");
        return cantidadhorasaIngresada;
    }

    public static float leerPrecioMaterial() {
        float precioMaterialIngresado;
        precioMaterialIngresado = leerReal("Introduzca el precio que desee añadir al material de la revisión -> ");
        return precioMaterialIngresado;
    }

    public static LocalDate leerFechaCierre() {
        LocalDate fechaFinIngresada;
        fechaFinIngresada = LocalDate.parse(leerFecha("Introduzca la fecha de cierre de la revisión -> "));
        return fechaFinIngresada;
    }
}