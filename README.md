# Tarea: Taller mecánico
## Profesor: José Ramón Jiménez Reyes
## Alumno: Ángel Martínez Otero

Un nuevo cliente que posee un taller mecánico nos ha pedido que le hagamos una aplicación para gestionar su taller. El taller se dedica a realizar diferentes trabajos a los
vehículos, aunque el cliente en este **primer sptint** quiere que nos centremos en los trabajos de revisión de un vehículo.

El taller cuenta con una serie de **clientes** de los que desea almacenar su **nombre**, **DNI** y **teléfono**. Un cliente podrá cambiar su nombre y su teléfono.

Para los **vehículos** nos interesa almacenar su **marca**, **modelo** y **matrícula**, pero no almacenaremos su propietario ya que los trabajos se facturan al cliente que los trae al taller.

Cuando un vehículo llega al taller, el mecánico encargado le registrará una **nueva revisón** en la que se anota el **cliente** que lo ha traido, el **vehículo** sobre el que se hará la revisión y la **fecha de inicio**, esto podremos anotarlo con posterioridad en nuestra aplicación. Cada trabajo deberá almacenar la fecha de llegada al taller, el número de horas dedicadas al mismo (que al llegar será 0 y que el mecánico irá aumentando según el tiempo que le dedique cada día), el precio del material que se va necesitando, que también cambiará con el trascurso de la revisión. 

Cuando se termine la revisión, se anota la fecha de finalización y ya no se podrá cambiar el número de horas de dicho trabajo, ni el precio del material.

Las revisiones tendrán un precio, que se calcula como el resultante de multiplicar el número de **horas** empleadas por **30 €** más el número de **días** que el vehículo
haya permanecido en el taller multiplicado por **10 €**.

El programa debe poder llevar una gestión de los clientes (añadir clientes, borrar clientes, buscar clientes por DNI, modificar clientes por su DNI y listar los clientes). 

Para los vehículos se llevará la misma gestión, a diferencia que estos no se pueden modificar.

Para las revisiones será parecida aunque tendremos más opciones tales como añadirle horas, añadirle precio del material y cerrarlas, listar revisiones por cliente y listar revisiones por vehículo.

El programa deberá mostrar un menú que permita llevar a cabo toda la gestión del taller que por ahora nos ha pedido el cliente.

Tu tarea consiste en realizar una primera versión de esta aplicación.

En este repositorio de GitHub hay un esqueleto de proyecto **gradle** con las dependencias necesarias del proyecto y que ya lleva incluidos todos los test necesarios que el programa debe pasar.

Para ello te muestro un diagrama de clases para el mismo y poco a poco te iré explicando los diferentes pasos a realizar:

![Diagrama de clases de la tarea](src/main/resources/uml/tallerMecanico.png)


#### Primeros Pasos
1. Lo primero que debes hacer es un **fork** del repositorio donde he colocado el esqueleto de este proyecto.
2. Clona tu repositorio remoto recién copiado en GitHub a un repositorio local que será donde irás realizando lo que a continuación se te pide. Modifica el archivo `README.md` para que incluya tu nombre en el apartado "Alumno". Realiza tu **primer commit**.

#### Cliente
1. Crea la clase `Cliente` con los atributos y visibilidad adecuados. 
2. Crea los métodos de acceso y modificación de cada atributo, teniendo en cuenta que un **nombre** estará compuesto de palabras separadas por un espacio y cada palabra comenzará con una mayúscula y continuará con minúsculas. El **DNI** y el **teléfono** deben también tener un formato válido. Debes comprobar que la **letra** del **DNI** sea **correcta**. Debes crear las constantes para las expresiones regulares que luego utilizarás en los métodos de modificación. Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no sea adecuado. También debes tener en cuenta que tanto el nombre como el teléfono de un cliente pueden cambiar.
3. Crea el **constructor con parámetros** que hará uso de los métodos de modificación.
4. Crea el **constructor copia**.
5. Crea el **método de clase** que se indica en el diagrama, que dado un DNI correcto nos devuelva un cliente válido con ese DNI y que será utilizado en las futuras **búsquedas**.
6. Un cliente será igual a otro si su DNI es el mismo. Basándote en ello crea los métodos `equals` y `hashCode`.
7. Crea el método `toString` que devuelva la cadena que esperan los tests.
8. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Vehiculo
1. Crea el registro `Vehiculo` con los atributos indicados.
2. Crea los métodos de verificación de los atributos, teniendo en cuenta que la **marca** puede seguir alguno de los siguientes patrones: Seat, Land Rover, KIA, Rolls-Royce, SsangYong. El **modelo** simplemente no debe estar en blanco. La matrícula tendrá el formato de una matrícula española moderna (1111BBB). Debes crear las constantes para las expresiones regulares que luego utilizarás en los métodos de modificación. Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no sea adecuado.
3. Crea el **constructor canónico** que hará uso de los métodos de verificación.
4. Crea el **método de clase** que se indica en el diagrama, que dada una matrícula correcta nos devuelva un vehículo válido con dicha matrícula y que será utilizado en las futuras **búsquedas**.
5. Un vehívculo será igual a otro si su matrícula es la misma. Basándote en ello crea los métodos `equals` y `hashCode`.
6. Crea el método `toString` que devuelva la cadena que esperan los tests.
7. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Revision
1. Crea la clase `Revision` con los atributos y visibilidad adecuados.
2. Crea los métodos de acceso y modificación de cada atributo, teniendo en cuenta que es posible registrar una revisión pasada (nuestro cliente a veces apunta las revisiones y luego los pasa a la aplicación). La fecha de inicio de la revisón no puede ser posterior a hoy. La fecha de fin no puede ser igual o anterior a la fecha de inicio y tampoco puede ser posterior a hoy. Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no sea adecuado.
3. Crea el **constructor con parámetros** que hará uso de los métodos de modificación.
4. Crea el **constructor copia** que creará una copia y en el caso del cliente creará una nueva instancia llamando a su constructor copia.
5. Crea los métodos `anadirHoras` y `anadirPrecioMaterial` que comprueben si los parámetros son correctos y laa añada al atributo adecuado.
6. Crea el método `cerrar` que se encargará de asignar la fecha de fin si esta es correcta.
7. Crea el método `getPrecio` que devolverá el precio de la revisión conforme a la fórmula establecida por nuestro cliente y explicada anteriormente.
8. Una revisión será igual a otro si es el mismo cliente, el mismo turismo y la fecha de inicio. Basándote en ello crea los métodos `equals` y `hashCode`.
9. Crea el método `toString` que devuelva la cadena que esperan los tests.
10. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Clientes
1. Crea la clase `Clientes` que gestionará una lista de clientes (`Cliente`) sin permitir elementos repetidos.
2. Crea el **constructor por defecto** que simplemente creará la lista.
3. Crea el método `get` que devolverá una nueva lista con los mismos elementos (no debe crear nuevas instancias).
4. Crea el método `insertar` que añadirá un cliente a la lista si éste no es nulo y no existe aún en la lista.
5. Crea el método `modificar` que permitirá cambiar el nombre o el teléfono (si estos parámetros no son nulos ni blancos) de un cliente existente y si no lanzará la correspondiente excepción. Devolverá `true` o  `false` dependiendo de si lo ha modificado o no.
6. Crea el método `buscar` que devolverá el cliente si este se encuentra en la lista y `null` en caso contrario.
7. Crea el método `borrar` que borrará el cliente si este existe en la lista o lanzará una excepción en caso contrario.
8. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Vehiculos
1. Crea la clase `Vehiculos` que gestionará una lista de vehículos (`Vehiculo`) sin permitir elementos repetidos.
2. Crea el **constructor por defecto** que simplemente creará la lista.
3. Crea el método `get` que devolverá una nueva lista con los mismos elementos (no debe crear nuevas instancias).
4. Crea el método `insertar` que añadirá un vehículo a la lista si este no es nulo y no existe aún en la lista.
5. Crea el método `buscar` que devolverá el turismo si este se encuentra en la lista y `null` en caso contrario.
6. Crea el método `borrar` que borrará el turismo si este existe en la lista o lanzará una excepción en caso contrario.
7. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Alquileres
1. Crea la clase `Revisiones` que gestionará una lista de revisiones (`Revision`) sin permitir elementos repetidos.
2. Crea el **constructor por defecto** que simplemente creará la lista.
3. Crea el método `get` que devolverá una nueva lista con los mismos elementos (no debe crear nuevas instancias).
4. Crea el método `get` para un cliente dado, que devolverá una nueva lista con las revisiones para dicho cliente (no debe crear nuevas instancias).
5. Crea el método `get` para un vehículo dado, que devolverá una nueva lista con las revisiones para dicho vehículo (no debe crear nuevas instancias).
6. Crea el método `insertar` que añadirá una revisión a la lista si esta no es nula y pasa la comprobación anterior.
7. Crea el método `comprobarRevision` que comprobará que en la lista no existe ninguna revisión sin cerrar ni para el cliente ni para el vehículo y que tampoco hay una revisión cerrada, del cliente o del vehículo, con fecha de fin posterior a la fecha en la que se pretende comenzar la revisión.
8. Crea el método `getRevision` que comprobará que la revisión no es nula y devol verá la revisión encontrada en la lista o lanzará una excepción. Este método será utilizado en métodos posteriores.
9. Crea el método `anadirHoras` que le añadirá horas a la revisión encontrada utilizando el método anterior.
10. Crea el método `anadirPrecioMaterial` que añadirá precio del material a la revisión encontrada utilizando el método anterior. 
11. Crea el método `cerrar` que cerrará (asignará la fecha de fin) a la revisión encontrada utilizando el método anteiror.
12. Crea el método `buscar` que devolverá la revisión si esta se encuentra en la lista y null en caso contrario.
13. Crea el método `borrar` que borrará la revisión si esta existe en la lista o lanzará una excepción en caso contrario.
14. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Modelo
1. Crea la clase `Modelo` que gestionará todo el modelo de nuestra aplicación. Será la encargada de comunicarse con las tres clases anteriores.
2. Crea el método `comenzar` que creará la instancia de las clases de negocio anteriores.
3. Crea el método `terminar` que simplemente mostrará un mensaje informativo indicando que el modelo ha terminado.
4. Crea los diferentes métodos `insertar`, teniendo en cuenta que ahora ya si insertaremos nuevas instancias utilizando los constructores copia (exceptuando `Vehiculo` ya que es un registro y, por tanto, inmutable) y que en el caso de las revisiones, primero debe buscar el cliente y el vehículo y utilizar dichas instancias encontradas.
5. Crea los diferentes métodos `buscar`, que devolverá una nueva instancia del elemento encontrado si este existe.
6. Crea el método `modificar` que invocará a su homólogo en la clase de negocio.
7. Crea los métodos `anadirHoras` y `anadirPrecioMaterial` que invocará a sus homólogos en la clase de negocio.
8. Crea el método `cerrar` que realizará el cierre, si es posible, de la revisión pasada.
9. Crea los diferentes métodos `borrar`, teniendo en cuenta que los borrados se realizarán en cascada, es decir, si borramos un cliente también borraremos todos sus revisiones y lo mismo pasará con los vehículos.
10. Crea los diferentes métodos `get`, que deben devolver una nueva lista, pero que contenga nuevas instancias no una referencia de los elementos.
11. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Opcion
1. Crea el enumerado `Opcion` que contendrá las diferentes opciones de nuestro menú de opciones y que será utilizado posteriormente para mostrar las posibles opciones a realizar. Cada instancia debe estar parametrizada con una cadena con el texto adecuado a mostrarnos y el número de la opción asignado (no nos basaremos en su orden).
2. Crea el atributo con el texto y el número de la opción a mostrar que será asignado en el constructor con parámetros, que también debes crear.
3. Crea e inicializa el mapa compuesto por los números de opciones y su opción correspondiente.
4. Crea el método `esValida` que comprobará si el número de la opción pasado se encuentra en el mapa.
5. Crea el método `get` que devolverá la opción adecuada si el número de la opción pasado es correcto y lanzará una excepción en caso contrario.
6. Crea el método `toString` que devuelva una cadena con el número de la opción y el texto de la opción que luego utilizaremos para mostrar las diferentes opciones del menú.
7. Realiza un **commit**.

#### Consola
1. Crea la clase de utilidades `Consola` que contendrá métodos que serán utilizados desde la vista para mostrar información por consola o leer información de la misma.
2. Crea el constructor adecuado.
3. Crea el método `mostrarCabecera` que mostrará por pantalla el mensaje pasado por parámetro y luego mostrará un subrayado compuesto de guiones con su misma longitud.
4. Crea el método `mostrarMenu` que mostrará una cabecera informando del cometido de la aplicación y mostrará las diferentes opciones del menú.
5. Crea el método `leerReal` que mostrará el mensaje pasado por parámetro y devolverá el real que lea por consola.
6. Crea el método `leerEntero` que hará lo mismo pero con un entero.
7. Crea el método `leerCadena` que hará lo mismo pero con una cadena.
8. Crea el método `leerFecha` que hará lo mismo, pero con una fecha. Deberá repetir la lectura mientras la fecha no se haya podido crear correctamente.
9. Crea el método `elegirOpcion` que leerá un entero (utilizando el método anteriormente creado) asociado a la opción y devolverá la opción correspondiente. Si el entero introducido no se corresponde con ninguna opción deberá volver a leerlo hasta que éste sea válido.
10. Crea los demás métodos de la clase que harán uso de los métodos privados anteriormente creados y que son autodescriptivos. Si tienes alguna duda con alguno, no dudes en preguntarme.
11. Realiza un **commit**.

#### Vista
1. Crea la clase `Vista` que será la encargada de la interacción con el usuario y que se comunicará con el controlador para pedirle realizar las diferentes acciones. Crea el atributo correspondiente, aunque aún no existe su clase y te lo marcará como erróneo.
2. Crea el método `setControlador` que asignará el controlador pasado al atributo si éste no es nulo.
3. Crea el método `comenzar` que mostrará el menú, leerá una opción de consola y la ejecutará. Repetirá este proceso mientras la opción elegida no sea la correspondiente a salir. Utilizará los correspondientes métodos de la clase Consola y llamará al método ejecutar de esta clase que describiré a continuación.
4. Crea el método `terminar` que simplemente mostrará un mensaje de despedida por consola.
5. Crea el método `ejecutar` que dependiendo de la opción pasada por parámetro invocará a un método o a otro.
6. Crea los métodos asociados a cada una de las opciones. Estos métodos deberán mostrar una cabecera informando en que opción nos encontramos, pedirnos los datos adecuados y realizar la operación adecuada llamando al método correspondiente de nuestro controlador. También deben controlar todas las posibles excepciones.
7. Realiza un **commit**.

#### Controlador
1. Crea la clase `Controlador` que será la encargada de hacer de intermediario entre la vista y el modelo.
2. Crea los atributos adecuados.
3. Crea el constructor con parámetros que comprobará que no son nulos y los asignará a los atributos. Además debe llamar al método `setControlador` de la vista con una instancia suya.
4. Crea los métodos `comenzar` y `terminar`, que llamarán a los correspondientes métodos en el modelo y en la vista.
5. Crea los demás métodos que simplemente harán una llamada al correspondiente método del modelo.
6. Realiza un **commit**.

#### MainApp
1. Crea la clase `Main` con un único método `main` que será el método de entrada a nuestra aplicación. Este método simplemente creará una vista, un modelo y un controlador, pasándoles las instancias antes creadas. Luego simplemente invocará al método `comenzar` del controlador.
2. Realiza las pruebas que estimes oportunas y cuando consideres que todo es correcto, realiza el último **commit** y seguidamente realiza el **push** a tu repositorio remoto.

#### Se valorará:

- La indentación debe ser correcta en cada uno de los apartados.
- Los identificadores utilizados deben ser adecuados y descriptivos.
- Se debe utilizar la clase Entrada para realizar la entrada por teclado que se encuentra como dependencia de nuestro proyecto en la librería entrada.
- El programa debe pasar todas las pruebas que van en el esqueleto del proyecto y toda entrada del programa será validada, para evitar que el programa termine abruptamente debido a una excepción.
- La corrección ortográfica tanto en los comentarios como en los mensajes que se muestren al usuario.


