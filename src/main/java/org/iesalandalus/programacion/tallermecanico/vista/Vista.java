package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Objects;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador, "El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        Consola.mostrarCabecera("¡Gracias por utilizar nuestra aplicación! Esperamos haber hecho tu experiencia más fácil y agradable. ¡Hasta pronto!");
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE -> insertarCliente();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BORRAR_CLIENTE -> borrarCliente();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case LISTAR_CLIENTES -> listarClientes();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case LISTAR_VEHICULOS -> listarVehiculos();
            case INSERTAR_REVISION -> insertarRevision();
            case BUSCAR_REVISION -> buscarRevision();
            case BORRAR_REVISION -> borrarRevision();
            case LISTAR_REVISIONES -> listarRevisiones();
            case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
            case ANADIR_HORAS_REVISION -> anadirHoras();
            case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
            case CERRAR_REVISION -> cerrarRevision();
            case SALIR -> salir();
        }
    }

    private void insertarCliente() {
        Consola.mostrarCabecera("INSERCIÓN DE CLIENTE ACTIVADA");
        String mensajeCliente = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Cliente clienteAInsertar = Consola.leerCliente();
                if (controlador.buscar(clienteAInsertar) != null) {
                    mensajeCliente = "El cliente ya ha sido registrado anteriormente. Por favor, verifica los detalles e inténtalo de nuevo.";
                } else {
                    controlador.insertar(clienteAInsertar);
                    mensajeCliente = String.format("¡Cliente insertado exitosamente en el sistema! A continuación se muestran los detalles: %s", controlador.buscar(clienteAInsertar));
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeCliente);
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("INSERCIÓN DE VEHÍCULO ACTIVADA");
        String mensajeVehiculo = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Vehiculo vehiculoAInsertar = Consola.leerVehiculo();
                if (controlador.buscar(vehiculoAInsertar) != null) {
                    mensajeVehiculo = "El vehículo ya ha sido registrado anteriormente. Por favor, verifica los detalles e inténtalo de nuevo.";
                } else {
                    controlador.insertar(vehiculoAInsertar);
                    mensajeVehiculo = String.format("¡Vehículo insertado exitosamente en el sistema! A continuación se muestran los detalles: %s", controlador.buscar(vehiculoAInsertar));
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeVehiculo);
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("INSERCIÓN DE REVISIÓN ACTIVADA");
        String mensajeRevision = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Revision revisionAInsertar = Consola.leerRevision();
                Cliente clienteRevision = controlador.buscar(revisionAInsertar.getCliente());
                Vehiculo vehiculoRevision = controlador.buscar(revisionAInsertar.getVehiculo());
                if (clienteRevision == null) {
                    mensajeRevision = "No se puede insertar la revisión porque el cliente especificado no existe en la base de datos. Por favor, registra al cliente primero y luego intenta nuevamente.";
                } else if (vehiculoRevision == null) {
                    mensajeRevision = "No se puede insertar la revisión porque el vehículo especificado no existe en la base de datos. Por favor, registra al vehículo primero y luego intenta nuevamente.";
                } else {
                    if (controlador.buscar(revisionAInsertar) != null) {
                        mensajeRevision = "la revisión ya ha sido registrada anteriormente. Por favor, verifica los detalles e inténtalo de nuevo.";
                    } else {
                        Revision revisionInsertada = new Revision(clienteRevision, vehiculoRevision, revisionAInsertar.getFechaInicio());
                        controlador.insertar(revisionInsertada);
                        mensajeRevision = String.format("¡Revisión insertado exitosamente en el sistema! A continuación se muestran los detalles: %s", controlador.buscar(revisionAInsertar));
                    }
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeRevision);
    }

    private void buscarCliente() {
        Consola.mostrarCabecera("BÚSQUEDA DE CLIENTE ACTIVADA");
        boolean saltaExcepcion = true;
        Cliente clienteBuscado = null;
        while (saltaExcepcion) {
            try {
                clienteBuscado = controlador.buscar(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        String mensajeCliente = (clienteBuscado == null) ? "No se ha encontrado ningún cliente con esa información. Por favor, inténtalo de nuevo." : String.format("¡Cliente encontrado! A continuación se muestran los detalles: %s", clienteBuscado);
        System.out.println(mensajeCliente);
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("BÚSQUEDA DE VEHÍCULO ACTIVADA");
        boolean saltaExcepcion = true;
        Vehiculo vehiculoBuscado = null;
        while (saltaExcepcion) {
            try {
                vehiculoBuscado = controlador.buscar(Consola.leerVehiculoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        String mensajeTurismo = (vehiculoBuscado == null) ? "No se ha encontrado ningún vehículo con esa información. Por favor, inténtalo de nuevo." : String.format("¡Vehículo encontrado! A continuación se muestran los detalles: %s", vehiculoBuscado);
        System.out.println(mensajeTurismo);
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("BÚSQUEDA DE REVISIÓN ACTIVADA");
        boolean saltaExcepcion = true;
        Revision revisionBuscada = null;
        while (saltaExcepcion) {
            try {
                revisionBuscada = controlador.buscar(Consola.leerRevision());
                saltaExcepcion = false;
            } catch (IllformedLocaleException e) {
                System.out.println(e.getMessage());
            }
        }
        String mensajeAlquiler = (revisionBuscada == null) ? "No se ha encontrado ningúna revisión con esa información. Por favor, inténtalo de nuevo." : String.format("¡Revisión encontrada! A continuación se muestran los detalles: %s", revisionBuscada);
        System.out.println(mensajeAlquiler);
    }

    private void modificarCliente() {
        Consola.mostrarCabecera("MODIFICACIÓN DE CLIENTE ACTIVADA");
        boolean saltaExcepcion = true;
        String mensajeCliente = null;
        while (saltaExcepcion) {
            try {
                Cliente clienteAModificar = controlador.buscar(Consola.leerClienteDni());
                if (clienteAModificar == null) {
                    mensajeCliente = "No se ha encontrado ningún cliente con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    boolean haSidoModificado = controlador.modificar(clienteAModificar, Consola.leerClienteNombre(), Consola.leerClienteTelefono());
                    mensajeCliente = (haSidoModificado) ? String.format("¡Cliente modificado exitosamente! A continuación se muestran los detalles: %s", controlador.buscar(clienteAModificar)) : "Lo siento, no se ha podido modificar al cliente. Por favor, verifica los detalles e inténtalo de nuevo.";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeCliente);
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("AÑADIR HORAS A REVISION ACTIVADA");
        boolean saltaExcepcion = true;
        String mensajeRevision = null;
        boolean yaTieneFechaCierre = true;
        Revision revisionAnadirHoras = null;
        while (saltaExcepcion) {
            try {
                List<Revision> revisionesVehiculo = controlador.getRevisiones(Consola.leerVehiculoMatricula());
                if (revisionesVehiculo.isEmpty()) {
                    mensajeRevision = "No se ha encontrado ningúna revisión con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    for (Revision revisionVehiculo : revisionesVehiculo) {
                        if (!revisionVehiculo.estaCerrada()) {
                            yaTieneFechaCierre = false;
                            revisionAnadirHoras = revisionVehiculo;
                        }
                    }
                    if (yaTieneFechaCierre) {
                        mensajeRevision = "El cierre de la revisión ya ha sido registrado anteriormente, por lo tanto, no es posible añadir más horas a esta revisión.";
                    }
                }
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (!yaTieneFechaCierre) {
                saltaExcepcion = true;
                while (saltaExcepcion) {
                    try {
                        controlador.anadirHoras(revisionAnadirHoras, Consola.leerHoras());
                        mensajeRevision = String.format("¡La cantidad de horas ha sido añadida exitosamente a la revisión! A continuación se muestran los detalles: %s", controlador.buscar(revisionAnadirHoras));
                        saltaExcepcion = false;
                    } catch (OperationNotSupportedException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        System.out.println(mensajeRevision);
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("AÑADIR PRECIO MATERIAL A REVISION ACTIVADA");
        boolean saltaExcepcion = true;
        String mensajeRevision = null;
        boolean yaTieneFechaCierre = true;
        Revision revisionAnadirPrecioMaterial = null;
        while (saltaExcepcion) {
            try {
                List<Revision> revisionesVehiculo = controlador.getRevisiones(Consola.leerVehiculoMatricula());
                if (revisionesVehiculo.isEmpty()) {
                    mensajeRevision = "No se ha encontrado ningúna revisión con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    for (Revision revisionVehiculo : revisionesVehiculo) {
                        if (!revisionVehiculo.estaCerrada()) {
                            yaTieneFechaCierre = false;
                            revisionAnadirPrecioMaterial = revisionVehiculo;
                        }
                    }
                    if (yaTieneFechaCierre) {
                        mensajeRevision = "El cierre de la revisión ya ha sido registrado anteriormente, por lo tanto, no es posible añadir el precio del material a esta revisión.";
                    }
                }
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (!yaTieneFechaCierre) {
                saltaExcepcion = true;
                while (saltaExcepcion) {
                    try {
                        controlador.anadirPrecioMaterial(revisionAnadirPrecioMaterial, Consola.leerPrecioMaterial());
                        mensajeRevision = String.format("¡El precio del material ha sido añadido exitosamente a la revisión! A continuación se muestran los detalles: %s", controlador.buscar(revisionAnadirPrecioMaterial));
                        saltaExcepcion = false;
                    } catch (OperationNotSupportedException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        System.out.println(mensajeRevision);
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("CIERRE DE REVISIÓN ACTIVADA");
        boolean saltaExcepcion = true;
        String mensajeRevision = null;
        boolean yaTieneFechaCierre = true;
        Revision revisionACerrar = null;
        while (saltaExcepcion) {
            try {
                List<Revision> revisionesVehiculo = controlador.getRevisiones(Consola.leerVehiculoMatricula());
                if (revisionesVehiculo.isEmpty()) {
                    mensajeRevision = "No se ha encontrado ningúna revisión con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    for (Revision revisionVehiculo : revisionesVehiculo) {
                        if (!revisionVehiculo.estaCerrada()) {
                            yaTieneFechaCierre = false;
                            revisionACerrar = revisionVehiculo;
                        }
                    }
                    if (yaTieneFechaCierre) {
                        mensajeRevision = "El cierre de la revisión ya ha sido registrada anteriormente.";
                    }
                }
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (!yaTieneFechaCierre) {
                saltaExcepcion = true;
                while (saltaExcepcion) {
                    try {
                        controlador.cerrar(revisionACerrar, Consola.leerFechaCierre());
                        mensajeRevision = String.format("¡Revisión cerrada con éxito! A continuación se muestran los detalles: %s", controlador.buscar(revisionACerrar));
                        saltaExcepcion = false;
                    } catch (OperationNotSupportedException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        System.out.println(mensajeRevision);
    }

    private void borrarCliente() {
        Consola.mostrarCabecera("ELIMINACIÓN DE CLIENTE ACTIVADA");
        String mensajeCliente = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Cliente clienteABorrar = controlador.buscar(Consola.leerClienteDni());
                if (clienteABorrar == null) {
                    mensajeCliente = "No se ha encontrado ningún cliente con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    controlador.borrar(clienteABorrar);
                    mensajeCliente = "¡Cliente eliminado exitosamente del sistema! Todos las revisiones asociadas a este cliente también han sido eliminadas.";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeCliente);
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("ELIMINACIÓN DE VEHÍCULO ACTIVADA");
        String mensajeVehiculo = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Vehiculo vehiculoABorrar = controlador.buscar(Consola.leerVehiculoMatricula());
                if (vehiculoABorrar == null) {
                    mensajeVehiculo = "No se ha encontrado ningún vehículo con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    controlador.borrar(vehiculoABorrar);
                    mensajeVehiculo = "¡Vehículo eliminado exitosamente del sistema! Todos los alquileres asociados a este cliente también han sido eliminados.";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeVehiculo);
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("ELIMINACIÓN DE REVISIÓN ACTIVADA");
        String mensajeRevision = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Revision revisionABorrar = controlador.buscar(Consola.leerRevision());
                if (revisionABorrar == null) {
                    mensajeRevision = "No se ha encontrado ningúna revisión con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    controlador.borrar(revisionABorrar);
                    mensajeRevision = "¡Revisión eliminada exitosamente del sistema!";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeRevision);
    }

    private void listarClientes() {
        Consola.mostrarCabecera("LISTADO DE CLIENTES ACTIVADA");
        List<Cliente> clientes = controlador.getClientes();
        StringBuilder mensajeCliente = new StringBuilder();
        if (clientes.isEmpty()) {
            mensajeCliente.append("No se encontraron clientes en la lista. ¡Registra un nuevo cliente para comenzar!  ");
        } else {
            int indiceLista = 1;
            mensajeCliente.append("Mostrando lista de clientes disponibles: \n");
            for (Cliente cliente : clientes) {
                mensajeCliente.append(String.format("%s. %s%n", indiceLista++, cliente));
            }
        }
        mensajeCliente.replace(mensajeCliente.length() - 2, mensajeCliente.length(), "");
        System.out.println(mensajeCliente);
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("LISTADO DE VEHÍCULOS ACTIVADA");
        List<Vehiculo> vehiculos = controlador.getVehiculos();
        StringBuilder mensajeVehiculo = new StringBuilder();
        if (vehiculos.isEmpty()) {
            mensajeVehiculo.append("No se encontraron vehículos en la lista. ¡Registra un nuevo vehículo para comenzar!  ");
        } else {
            int indiceLista = 1;
            mensajeVehiculo.append("Mostrando lista de vehículos disponibles: \n");
            for (Vehiculo vehiculo : vehiculos) {
                mensajeVehiculo.append(String.format("%s. %s%n", indiceLista++, vehiculo));
            }
        }
        mensajeVehiculo.replace(mensajeVehiculo.length() - 2, mensajeVehiculo.length(), "");
        System.out.println(mensajeVehiculo);
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("LISTADO DE REVISIONES ACTIVADA");
        List<Revision> revisiones = controlador.getRevisiones();
        StringBuilder mensajeRevisiones = new StringBuilder();
        if (revisiones.isEmpty()) {
            mensajeRevisiones.append("No se encontraron revisiones en la lista. ¡Registra un nueva revisión para comenzar!  ");
        } else {
            int indiceLista = 1;
            mensajeRevisiones.append("Mostrando lista de revisiones disponibles: \n");
            for (Revision revision : revisiones) {
                mensajeRevisiones.append(String.format("%s. %s%n", indiceLista++, revision));
            }
        }
        mensajeRevisiones.replace(mensajeRevisiones.length() - 2, mensajeRevisiones.length(), "");
        System.out.println(mensajeRevisiones);
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("LISTADO DE REVISIONES POR CLIENTE ACTIVADA");
        System.out.print("Introduce el DNI del cliente que deseas el listado de sus revisiones: ");
        boolean saltaExcepcion = true;
        List<Revision> revisionesCliente = null;
        while (saltaExcepcion) {
            try {
                revisionesCliente = controlador.getRevisiones(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        StringBuilder mensajeRevisionesCliente = new StringBuilder();
        if (revisionesCliente.isEmpty()) {
            mensajeRevisionesCliente.append("No se encontraron revisiones asociadas a este cliente en la lista. ¡Asegúrate de haber registrado al menos una revisión para este cliente!  ");
        } else {
            int indiceLista = 1;
            mensajeRevisionesCliente.append("Mostrando lista de revisiones asociadas a este cliente disponibles: \n");
            for (Revision revisionCliente : revisionesCliente) {
                mensajeRevisionesCliente.append(String.format("%s. %s%n", indiceLista++, revisionCliente));
            }
        }
        mensajeRevisionesCliente.replace(mensajeRevisionesCliente.length() - 2, mensajeRevisionesCliente.length(), "");
        System.out.println(mensajeRevisionesCliente);
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("LISTADO DE REVISIONES POR VEHÍCULO ACTIVADA");
        System.out.print("Introduce la matrícula del vehículo que deseas el listado de sus revisiones: ");
        boolean saltaExcepcion = true;
        List<Revision> revisionesVehiculo = null;
        while (saltaExcepcion) {
            try {
                revisionesVehiculo = controlador.getRevisiones(Consola.leerVehiculoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        StringBuilder mensajeRevisionesVehiculo = new StringBuilder();
        if (revisionesVehiculo.isEmpty()) {
            mensajeRevisionesVehiculo.append("No se encontraron revisiones asociadas a este vehículo en la lista. ¡Asegúrate de haber registrado al menos una revisión para este vehículo!  ");
        } else {
            int indiceLista = 1;
            mensajeRevisionesVehiculo.append("Mostrando lista de revisiones asociados a este vehículo disponibles: \n");
            for (Revision revisionVehiculo : revisionesVehiculo) {
                mensajeRevisionesVehiculo.append(String.format("%s. %s%n", indiceLista++, revisionVehiculo));
            }
        }
        mensajeRevisionesVehiculo.replace(mensajeRevisionesVehiculo.length() - 2, mensajeRevisionesVehiculo.length(), "");
        System.out.println(mensajeRevisionesVehiculo);
    }

    private void salir() {
        controlador.terminar();
    }
}
