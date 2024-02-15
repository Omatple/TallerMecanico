package org.iesalandalus.programacion.tallermecanico.modelo.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador, "El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() throws OperationNotSupportedException {
        Consola.mostrarMenu();
        Opcion opcion = Consola.elegirOpcion();
        while (opcion != Opcion.SALIR) {
            ejecutar(opcion);
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
        }
        ejecutar(opcion);
    }

    public void terminar() {
        Consola.mostrarCabecera("¡Hasta luego!¡Gracias por utilizar nuestros servicios!");
    }

    private void ejecutar(Opcion opcion) throws OperationNotSupportedException {
        switch (opcion) {
            case INSERTAR_CLIENTE:
                insertarCliente();
                break;
            case BUSCAR_CLIENTE:
                buscarCliente();
                break;
            case BORRAR_CLIENTE:
                borrarCliente();
                break;
            case MODIFICAR_CLIENTE:
                modificarCliente();
                break;
            case LISTAR_CLIENTES:
                listarClientes();
                break;
            case INSERTAR_VEHICULO:
                insertarVehiculo();
                break;
            case BUSCAR_VEHICULO:
                buscarVehiculo();
                break;
            case BORRAR_VEHICULO:
                borrarVehiculo();
                break;
            case LISTAR_VEHICULOS:
                listarVehiculos();
                break;
            case INSERTAR_REVISION:
                insertarRevision();
                break;
            case BUSCAR_REVISION:
                buscarRevision();
                break;
            case BORRAR_REVISION:
                borrarRevision();
                break;
            case LISTAR_REVISIONES:
                listarRevisiones();
                break;
            case LISTAR_REVISIONES_CLIENTE:
                listarRevisionesCliente();
                break;
            case LISTAR_REVISIONES_VEHICULO:
                listarRevisionesVehiculo();
                break;
            case ANADIR_HORAS_REVISION:
                anadirHoras();
                break;
            case ANADIR_PRECIO_MATERIAL_REVISION:
                anadirPrecioMaterial();
                break;
            case CERRAR_REVISION:
                cerrarRevision();
                break;
            case SALIR:
                salir();
                break;
            default:
                Consola.mostrarMenu();
                break;
        }
    }

    private void insertarCliente() {
        Consola.mostrarCabecera("INSERTAR CLIENTE");
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                controlador.insertar(Consola.leerCliente());
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("INSERTAR VEHICULO");
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                controlador.insertar(Consola.leerVehiculo());
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("INSERTAR REVISION");
        boolean saltaExcepcion = true;
        Cliente clienteAInsertar = null;
        while (saltaExcepcion) {
            try {
                clienteAInsertar = controlador.buscar(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (clienteAInsertar == null) {
            System.out.println("No puedes crear una revisión, si no existe este cliente en la base de datos.");
        } else {
            saltaExcepcion = true;
            Vehiculo vehiculoAInsertar = null;
            while (saltaExcepcion) {
                try {
                    vehiculoAInsertar = controlador.buscar(Consola.leerVehiculoMatricula());
                    saltaExcepcion = false;
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (vehiculoAInsertar == null) {
                System.out.println("No puedes crear una revisión, si no existe este vehículo en la base de datos.");
            } else {
                saltaExcepcion = true;
                while (saltaExcepcion) {
                    try {
                        Revision revisionAInsertar = new Revision(clienteAInsertar, vehiculoAInsertar, LocalDate.parse(Consola.leerFecha("Introduzca la fecha de inicio de la revisón -> ")));
                        controlador.insertar(revisionAInsertar);
                        saltaExcepcion = false;
                    } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private void buscarCliente() {
        Consola.mostrarCabecera("BUSCAR CLIENTE");
        boolean saltaExcepcion = true;
        Cliente clienteBuscado = null;
        while (saltaExcepcion) {
            try {
                clienteBuscado = controlador.buscar(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (clienteBuscado == null) {
            System.out.println("El cliente no ha sido encontrado.");
        } else {
            System.out.printf("El cliente ha sido encontrado: %s\n", clienteBuscado);
        }
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("BUSCAR VEHICULO");
        boolean saltaExcepcion = true;
        Vehiculo vehiculoBuscado = null;
        while (saltaExcepcion) {
            try {
                vehiculoBuscado = controlador.buscar(Consola.leerVehiculoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (vehiculoBuscado == null) {
            System.out.println("El vehículo no ha sido encontrado.");
        } else {
            System.out.printf("El vehículo ha sido encontrado: %s\n", vehiculoBuscado);
        }
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("BUSCAR REVISION");
        boolean saltaExcepcion = true;
        Revision revisionBuscada = null;
        while (saltaExcepcion) {
            try {
                revisionBuscada = controlador.buscar(Consola.leerRevision());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (revisionBuscada == null) {
            System.out.println("La revisión no ha sido encontrado.");
        } else {
            System.out.printf("La revisión ha sido encontrado: %s\n", revisionBuscada);
        }
    }

    private void modificarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("MODIFICAR CLIENTE");
        boolean saltaExcepcion = true;
        Cliente clienteAModificar = null;
        while (saltaExcepcion) {
            try {
                clienteAModificar = controlador.buscar(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (clienteAModificar == null) {
            System.out.println("El cliente no existe, por lo que no puede ser modificado.");
        } else {
            boolean clienteModificado = false;
            saltaExcepcion = true;
            System.out.println("Si desea no modificar uno de los atributos dejelo en blanco pulsando la tecla 'Enter' lo que quiera modificar del cliente: ");
            while (saltaExcepcion) {
                try {
                    clienteModificado = controlador.modificar(clienteAModificar, Consola.leerClienteNombre(), Consola.leerClienteTelefono());
                    saltaExcepcion = false;
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (clienteModificado) {
                System.out.println("El cliente a sido modificado.");
            } else {
                System.out.println("El cliente no ha sido modificado.");
            }
        }
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("AÑADIR HORAS REVISION");
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                controlador.anadirHoras(Consola.leerRevision(), Consola.leerHoras());
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("AÑADIR PRECIO MATERIAL REVISION");
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                controlador.anadirPrecioMaterial(Consola.leerRevision(), Consola.leerPrecioMaterial());
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("CERRAR REVISION");
        boolean saltaExcepcion = true;
        Revision revisionACerrar = null;
        while (saltaExcepcion) {
            try {
                revisionACerrar = controlador.buscar(Consola.leerRevision());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (revisionACerrar == null) {
            System.out.println("No existe ninguna revisión igual.");
        } else {
            saltaExcepcion = true;
            while (saltaExcepcion) {
                try {
                    controlador.cerrar(revisionACerrar, Consola.leerFechaCierre());
                    saltaExcepcion = false;
                } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void borrarCliente() {
        Consola.mostrarCabecera("BORRAR CLIENTE");
        boolean saltaExcepcion = true;
        Cliente clienteABorrar = null;
        while (saltaExcepcion) {
            try {
                clienteABorrar = controlador.buscar(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (clienteABorrar == null) {
            System.out.println("El cliente no existe, por lo que no puede ser eliminado.");
        } else {
            saltaExcepcion = true;
            while (saltaExcepcion) {
                try {
                    controlador.borrar(clienteABorrar);
                    saltaExcepcion = false;
                } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("BORRAR VEHICULO");
        boolean saltaExcepcion = true;
        Vehiculo vehiculoABorrar = null;
        while (saltaExcepcion) {
            try {
                vehiculoABorrar = controlador.buscar(Consola.leerVehiculoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (vehiculoABorrar == null) {
            System.out.println("El vehículo no existe, por lo que no puede ser eliminado.");
        } else {
            saltaExcepcion = true;
            while (saltaExcepcion) {
                try {
                    controlador.borrar(vehiculoABorrar);
                    saltaExcepcion = false;
                } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("BORRAR REVISION");
        boolean saltaExcepcion = true;
        Revision revisionABorrar = null;
        while (saltaExcepcion) {
            try {
                revisionABorrar = controlador.buscar(Consola.leerRevision());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (revisionABorrar == null) {
            System.out.println("La revisión no existe, por lo que no puede ser eliminada.");
        } else {
            saltaExcepcion = true;
            while (saltaExcepcion) {
                try {
                    controlador.borrar(revisionABorrar);
                    saltaExcepcion = false;
                } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void listarClientes() {
        Consola.mostrarCabecera("LISTAR CLIENTES");
        List<Cliente> listaClientes = controlador.getClientes();
        if (!listaClientes.isEmpty()) {
            int contador = 1;
            System.out.println("Lista: ");
            for (Cliente cliente : listaClientes) {
                System.out.printf("%s. %s\n", contador++, cliente);
            }
        } else {
            System.out.println("No existe ningun cliente para crear la lista.");
        }
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("LISTAR VEHICULOS");
        List<Vehiculo> listaVehiculos = controlador.getVehiculos();
        if (!listaVehiculos.isEmpty()) {
            int contador = 1;
            System.out.println("Lista: ");
            for (Vehiculo vehiculo : listaVehiculos) {
                System.out.printf("%s. %s\n", contador++, vehiculo);
            }
        } else {
            System.out.println("No existe ningun vehículo para crear la lista.");
        }
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("LISTAR REVISIONES");
        List<Revision> listaRevisiones = controlador.getRevisiones();
        if (!listaRevisiones.isEmpty()) {
            int contador = 1;
            System.out.println("Lista: ");
            for (Revision revision : listaRevisiones) {
                System.out.printf("%s. %s\n", contador++, revision);
            }
        } else {
            System.out.println("No existe ninguna revisión para crear la lista.");
        }
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("LISTAR REVISONES CLIENTE");
        boolean saltaExcepcion = true;
        List<Revision> listaRevisionesCliente = null;
        while (saltaExcepcion) {
            try {
                listaRevisionesCliente = controlador.getRevisiones(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (!listaRevisionesCliente.isEmpty()) {
            int contador = 1;
            System.out.println("Lista: ");
            for (Revision revisionCliente : listaRevisionesCliente) {
                System.out.printf("%s. %s\n", contador++, revisionCliente);
            }
        } else {
            System.out.println("No existe ninguna revisión para ese cliente.");
        }
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("LISTAR REVISONES VEHICULOS");
        boolean saltaExcepcion = true;
        List<Revision> listaRevisionesVehiculo = null;
        while (saltaExcepcion) {
            try {
                listaRevisionesVehiculo = controlador.getRevisiones(Consola.leerVehiculoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        if (!listaRevisionesVehiculo.isEmpty()) {
            int contador = 1;
            System.out.println("Lista: ");
            for (Revision revisionVehiculo : listaRevisionesVehiculo) {
                System.out.printf("%s. %s\n", contador++, revisionVehiculo);
            }
        } else {
            System.out.println("No existe ninguna revisión para ese vehículo.");
        }
    }

    private void salir() {
        terminar();
    }
}
