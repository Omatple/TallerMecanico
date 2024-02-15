package org.iesalandalus.programacion.tallermecanico.modelo.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class Consola {

    private static final String CADENA_FORMATO_FECHA = "^(?:(?:1[6-9]|[2-9]\\d)?\\d{2})-(?:(?:(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1\\d|2[0-8]))|(?:(?:0?[13-9]|1[0-2])-(?:29|30))|(?:0?[13578]|1[02])-31)$|^(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))-02-29$";

    private Consola() {
    }

    public static void mostrarCabecera(String mensaje) {
        StringBuilder subrayadoMensaje = new StringBuilder();
        System.out.println(mensaje);
        for (int i = 0; i < mensaje.length(); i++) {
            subrayadoMensaje.append("-");
        }
        System.out.println(subrayadoMensaje);
    }

    public static void mostrarMenu() {
        System.out.println("---------------------------------------------------------------------------------------");
        mostrarCabecera("Aplicación del taller mecánico. Uso para la gestión de clientes, vehículos y revisiones");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.toString());
        }
    }

    private static float leerReal(String mensaje) {
        System.out.println(mensaje);
        System.out.print("A continuación escriba un número real: ");
        return Entrada.real();
    }

    private static int leerEntero(String mensaje) {
        System.out.println(mensaje);
        System.out.print("A continuación escriba un número entero: ");
        return Entrada.entero();
    }

    private static String leerCadena(String mensaje) {
        System.out.println(mensaje);
        System.out.print("A continuación escriba una cadena: ");
        return Entrada.cadena();
    }

    public static String leerFecha(String mensaje) {
        Pattern patron = Pattern.compile(CADENA_FORMATO_FECHA);
        System.out.println(mensaje);
        System.out.print("A continuación escriba una fecha: ");
        String entradaFecha = Entrada.cadena();
        Matcher comparador = patron.matcher(entradaFecha);
        while (!comparador.matches()) {
            System.out.println("ERROR: La fecha no tiene un formato valido(aaaa-mm-dd). Intentelo de nuevo.");
            System.out.print("A continuación escriba una fecha: ");
            entradaFecha = Entrada.cadena();
            comparador = patron.matcher(entradaFecha);
        }
        return entradaFecha;
    }


    public static Opcion elegirOpcion() {
        return Opcion.get(leerEntero("Eliga el número de la opción que desee ejecutar -> "));
    }

    public static Cliente leerCliente() {
        System.out.println("Introduzca los datos del cliente -> ");
        Cliente clienteIngresado = leerClienteDni();
        clienteIngresado.setNombre(leerClienteNombre());
        clienteIngresado.setTelefono(leerClienteTelefono());
        return new Cliente(clienteIngresado);
    }

    public static Cliente leerClienteDni() {
        return new Cliente(Cliente.get(leerCadena("Ingrese el DNI -> ")));
    }

    public static String leerClienteNombre() {
        return leerCadena("Ingrese el nombre -> ");
    }

    public static String leerClienteTelefono() {
        return leerCadena("Ingrese el número de telefono -> ");
    }

    public static Vehiculo leerVehiculo() {
        System.out.println("Introduzca los datos del vehículo -> ");
        return new Vehiculo(leerCadena("Ingrese la marca del vehículo -> "), leerCadena("Ingrese el modelo del vehículo -> "), leerVehiculoMatricula().matricula());
    }

    public static Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(leerCadena("Ingrese la matrícula del vehículo -> "));
    }

    public static Revision leerRevision() {
        System.out.println("Introduzca los datos de la revisión -> ");
        return new Revision(leerClienteDni(), leerVehiculoMatricula(), LocalDate.parse(leerFecha("Ingrese la fecha de inicio de la revisión -> ")));
    }

    public static int leerHoras() {
        return leerEntero("Introduzca las horas que desee añadir a la revisión -> ");
    }

    public static float leerPrecioMaterial() {
        return leerReal("Introduzca el precio que desee añadir al material de la revisión -> ");
    }

    public static LocalDate leerFechaCierre() {
        return LocalDate.parse(leerFecha("Introduzca la fecha de cierre de la revisón -> "));

    }
}