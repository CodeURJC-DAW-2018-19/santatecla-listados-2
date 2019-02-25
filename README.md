# santatecla-listados-2
## Phase 0
### Equipo Técnico
El equipo técnico es el siguiente:

| Nombre  | Apellidos          | Correo                            | GitHub       |
|---------|--------------------|-----------------------------------|--------------|
| Alberto | Cañal Liberal      | a.canal.2016@alumnos.urjc.es      | Albertocalib |
| Samuel  | Serrano González   | s.serrano.2016@alumnos.urjc.es    | Saal4        |
| Jaime   | Fernández Ribelles | j.fernandezr.2016@alumnos.urjc.es | Jaime9070    |
| Haritz  | Gamarra Urkia      | h.gamarra.2016@alumnos.urjc.es    | HaritzGamarra|

### Trello
Para la autoorganización del equipo se utilizará la herramienta Trello, cuyo tablero del equipo lo podéis encontrar en el siguiente link:

[Tablero del equipo en Trello](https://trello.com/b/iyJ33zUz/daw)

### Documento de requisitos
En el siguiente enlace podéis encontrar el enlace con el documento donde se explica las funcionalidades de la página web:

[Ir al documento](https://drive.google.com/open?id=17in-rek1uW9gD4oh3yN4b59HTd_Unjdcabd1Ibo5woY)

## Phase 1
### Capturas de pantalla
#### LogIn
![LogIn](/Screenshots/Phase1/login.png "LogIn")

En esta página encontramos el logIn de la página web en el que tenemos tres posibles modos de acceder al sistema, siendo profesor, alumno, o invitado. 
Por defecto, al considerar que el usuario más habitual será el de alumno, es el que está por defecto.

#### Pantalla Inicio Alumno
![InicioAlumno](/Screenshots/Phase1/StudentMainPage.png "Pantalla Inicio Alumno")

En esta imagen podemos ver como sería la página principal del alumno, en la que podemos ver una barra de búsqueda para buscar en el temario y todo el temario en un menu desplegable, 
con los conceptos de cada tema que al pulsar sobre cada uno de ellos, iríamos a pantalla del concepto y un botón a un diagram en el que mostrará los aciertos y fallos globales sobre el temario.
Aqui podemos ver el diagrama arriba mencionado.

![DiagramaResultados](/Screenshots/Phase1/DiagramaInicio.png "Diagrama global del alumno")

#### Pantalla conceptos Alumno
![ConceptosAlumno](/Screenshots/Phase1/conceptos.png "Pantalla conceptos alumno")

En esta imagen podemos ver como sería la página de cada concepto del alumno, en la que podemos ver las respuestas que ya están corregidas y las que estánn pendientes todavía,
 un botón para responder una nueva pregunta y el siguiente diagrama con los resultados de ese concepto:

![DiagramaResultados](/Screenshots/Phase1/DiagramaConcepto.png "Diagrama resultado del concepto")

#### Pantalla Inicio Invitados

![InicioInvitado](/Screenshots/Phase1/guestMainPage.png "Pantalla Inicio Invitado")

En esta imagen podemos ver como sería la página principal del invitado que es exactamente igual que la de alumno pero sin la posibilidad de ir a la página de los conceptos 
y sin la posibilidad de ver el diagrama.

#### Diálogos Preguntas
A continuación podréis ver los tipos de pregunta existentes en nuestro sistema:
##### Pregunta Abierta (tipo 0)
![PreguntaAbiertaTipo0](/Screenshots/Phase1/Tipo0.png "Diálogo Pregunta Abierta(Tipo 0)")
##### Pregunta Cerrada (tipo 1)
![PreguntaCerradaTipo1](/Screenshots/Phase1/Tipo1.png "Diálogo Pregunta Cerrada(Tipo 1)")
##### Pregunta Abierta (tipo 2)
![PreguntaAbiertaTipo2](/Screenshots/Phase1/Tipo2.png "Diálogo Pregunta Abierta(Tipo 2)")
##### Pregunta Abierta (tipo 3)
![PreguntaAbiertaTipo3](/Screenshots/Phase1/Tipo3.png "Diálogo Pregunta Abierta(Tipo 3)")
##### Pregunta Abierta (tipo 4)
![PreguntaAbiertaTipo4](/Screenshots/Phase1/DialogoTipo4.png "Diálogo Pregunta Abierta(Tipo 4)")

### Diagrama de navegación
La siguiente imagen muestra el diagrama de navegación de nuestra página web:

![DiagramaNavegación](/Screenshots/Phase1/Navigation_Diagram_Gestor_de_Listas.png "Diagrama de navegación de la página web")


## Phase 2
### Capturas de pantalla

La navegación por nuestra página web ha variado desde la fase anterior, a continuación os mostraremos  el aspecto de las nuevas capturas de pantalla.

#### LogIn
![LogIn](/Screenshots/Phase2/LogIn.png "LogIn")

Este es nuestro LogIn actual, es prácticamente igual que el de la fase anterior, sin embargo tenemos un nuevo enlace para poder registrarnos. El LogIn diferencia auntomáticamente el rol del usuario que accede buscando en la base de datos.

#### Nueva Cuenta
![Nueva Cuenta](/Screenshots/Phase2/newAccount.png "Nueva Cuenta")

Aqui podemos ver una nueva pantalla en la que el usuario podrá registrarse (crear una nueva cuenta). Esta cuenta se guarda automáticamente en la base de datos. La página cuenta con sistemas para detectar posibles errores (mismo nombre de ususario, falta de información, etc). 

#### Pantalla Inicio Estudiante
![Pantalla Estudiante](/Screenshots/Phase2/StudentScreen.png "Pantalla Estudiante")

Esta es la página principal del estudiante, como podemos ver es muy similar a la de la fase anterior. Los cambios mas significativos son la implementación de una barra de búsqueda funcional, un botón para cargar más conceptos funcional (Load More) y un botón que redirige a la página de las imagenes (Images). Se mantiene el botón para mostrar el cuadro de diálogo Diagrama como podemos ver acontinuación.

![StudentDiagram](/Screenshots/Phase2/StudentDiagram.png "StudentDiagram")

#### Imagenes para Estudiante
![ImagesStudent](/Screenshots/Phase2/ImagesStudent.png "ImagesStudent")

Esta es una nueva pantalla en la cual el alumno puede ver las imagenes (que solo el profesor puede subir como ya veremos acontinuación). Cada imagen tiene un enlace al concepto con el que esta relacionado y al pulsar sobre el te redirecciona alli.

#### Pantalla de conceptos para el Estudiante
![StudentConcept](/Screenshots/Phase2/StudentConcept.png "StudentConcept")

La pantalla de conceptos para el alumno es prácticamente igual a la de la fase anterior. Cuenta con su dialogo para el diagrama y para los diferentes tipos de pregunta.
#### Diálogos
##### Diagrama de Concepto
![StudentConceptDiagram](/Screenshots/Phase2/StudentConceptDiagram.png "StudentConceptDiagram")
##### Pregunta Abierta (tipo 0)
![PreguntaAbiertaTipo0](/Screenshots/Phase1/Tipo0.png "Diálogo Pregunta Abierta(Tipo 0)")
##### Pregunta Cerrada (tipo 1)
![PreguntaCerradaTipo1](/Screenshots/Phase1/Tipo1.png "Diálogo Pregunta Cerrada(Tipo 1)")
##### Pregunta Abierta (tipo 2)
![PreguntaAbiertaTipo2](/Screenshots/Phase1/Tipo2.png "Diálogo Pregunta Abierta(Tipo 2)")
##### Pregunta Abierta (tipo 3)
![PreguntaAbiertaTipo3](/Screenshots/Phase1/Tipo3.png "Diálogo Pregunta Abierta(Tipo 3)")
##### Pregunta Abierta (tipo 4)
![PreguntaAbiertaTipo4](/Screenshots/Phase1/DialogoTipo4.png "Diálogo Pregunta Abierta(Tipo 4)")

#### Pantalla Inicio Invitado
![GuestScreen](/Screenshots/Phase2/GuestScreen.png "GuestScreen")

Esta pantalla también es muy similar a la de la fase anterior. los cambios mas significativos son una barra de búsqueda funcional, un botón para cargar más conceptos funcional (Load More) y un botón que redirige a la página de las imagenes (Images). Esta última pagina de imagenes solo permite al invitado ver las imagenes y el concepto con el que estan relacionadas pero no permite acceder al concepto para ello hay que registrarse.
#### Imagenes para Invitado
![ImagesGuest](/Screenshots/Phase2/ImagesGuest.png "ImagesGuest")

#### Pantalla para Profesor
![TeacherScreen](/Screenshots/Phase2/TeacherScreen.png "TeacherScreen")

Esta es la página principal del profesor, como podemos ver es muy similar a la de la fase anterior. Los cambios mas significativos son la implementación de una barra de búsqueda funcional, un botón para cargar más conceptos funcional (Load More), un botón que redirige a la página de las imagenes (Images) y que se ha añadido funcionalidad a los botones de añadir tema, añadir concpeto, eliminar tema y eliminar concepto. Estos últimos botones abren una serie de dialogoss como podemos ver a continuación.
#### Diálogos
##### Añadir Tema
![addTopic](/Screenshots/Phase2/addTopic.png "addTopic")
##### Añadir Concepto
![addConcept](/Screenshots/Phase2/addConcept.png "addConcept")
##### Eliminar Tema
![deleteTopic](/Screenshots/Phase2/deleteTopic.png "deleteTopic")
##### Eliminar Concepto
![deleteConcept](/Screenshots/Phase2/deleteConcept.png "deleteConcept")

Como en el caso de las pantallas de estudiante, el bóton con el diálogo del diagrama se ha mantenido.

![TeacherDiagram](/Screenshots/Phase2/TeacherDiagram.png "TeacherDiagram")

#### Pantalla de conceptos para el profesor
![TeacherConcept](/Screenshots/Phase2/TeacherConcept.png "TeacherConcept")

Esta es la pantalla de concepto del profesor, muy similar a la que podemos encontrar en la fase 1. Aqui cabe destacar el header de la pagina el cual en función del concepto accedido abre una pestaña que podemos cerrar en cualquier momento en la x, redirigiendonos asi a la pagina principal del profesor (Este header no solo esta aqui, sino que se repite en toda la aplicación).


#### Imagenes para Profesor
![ImagesTeacher](/Screenshots/Phase2/ImagesTeacher.png "ImagesTeacher")

Esta es la pantalla de las imágenes para el profesor. Es una pantalla nueva muy similar a la de las imagenes de estudiante e invitado. Como pasa con estudiante, las imagenes tienen un enlace al concepto con el que estan relacionadas. Esta pantalla también tiene un botón para añadir imagenes ya que el profesor es el único usuario al que se le permite dicha acción. Una vez pulsado el botón de añadir imagen se nos redirecciona a una pagina donde tenemos que rellenar un formulario (Pantalla de Nueva Imágen) y una vez enviada la imágen, la web nos indica que se ha realizado con exito (Pantalla de imagen subida) o si ha habido algun error. 
##### Nueva Imágen
![NewImage](/Screenshots/Phase2/NewImage.png "NewImage")
##### Imagen Subida
![ImageUploaded](/Screenshots/Phase2/ImageUploaded.png "ImageUploaded")

#### Error Personalizado
![CustomError](/Screenshots/Phase2/CustomError.png "CustomError")

En esta segunda fase hemos personalizado un error propio, para sustituir al error por defecto. Este error personalizado muestra el codigo del error http y su mensaje asociado.

### Diagrama de navegación
La siguiente imagen muestra el nuevo diagrama de navegación de nuestra página web resultante tras concluir esta segunda fase:

![DiagramaNavegacionFase2](/Screenshots/Phase2/DiagramaNavegacionFase2.png "DiagramaNavegacionFase2")


### Entorno de desarrollo

Para esta fase se nos requiere realizar una explicación para poder compilar y ejecutar nuestra aplicación, asi como un Diagrama de Entidades de la base de datos y un Diagrama de Clases Java de la aplicación.

Para poder compilar y ejecutar la aplicación será necesario seguir los siguientes pasos:
- Comenzaremos descargando y configurando lo referente a la base de datos. Usamos MySQL . Será necesario descargar MySQL Installer para poder instalar el Server de MySQL.

- Una vez teniendo el server, descargamos MySQL Workbench. Una vez en Workbench iremos a la parte izquierda de la pantalla y pulsaremos sobre “+” en MySQL Connections. Configuramos el nombre de conexión y nuestra contraseña de root.

- Con una conexión creada, clicamos sobre ella  y para poder acceder  se necesita un nombre de login y una contraseña.

    Login name: Grupo10

    Contraseña: DAWGrupo10

- A continuación necesitamos un entorno de desarrollo de Java que soporte aplicaciones Spring. En la actualidad los IDEs de Java más utilizados tienen dicho soporte. Nosotros hemos estado desarrollando utilizando Intellj, pero se pueden utilizar otros como Eclipse o NetBeans.

- IMPORTANTE: descargar el SDK 11.0.2.
Para poder establecer (set up) este SDK seguimos los siguientes pasos: File > Project Structure > Platform Setting > SDKs > y seleccionamos el que nos hemos descargado, el SDK 11.0.2. Finalmente pulsamos OK.

- Dado que es un proyecto Maven, debemos importar todos los cambios que se requieran para que la aplicación funcione correctamente.

- Cuando hayamos finalizado todo esto vamos a la carpeta src del proyecto > main > java > com.example.demo > y pinchamos sobre Application. Vamos a la parte superior, a Run… y seleccionamos aquel que tenga el símbolo de Spring (una hojita verde). Así el proyecto comienza a compilarse.

- Una vez se ha completado la compilación sin errores (aparece Started Application…), vamos a un navegador y ponemos la dirección https://localhost:8443/logIn. De esta forma ya podrá interactuar con la aplicación.


### Diagrama de Entidades
Este es el Digrama de Entidades de nuestra base de datos. Las clases que aparecen en este diagrama son @Entity y mostramos las relaciones entre ellas y sus cardinalidades. Subrayado en negro esta el atributo que sirve para identificar al objeto (claves primarias); y subrayado en azul estan las claves que sirven para referenciar a objetos relacionados (claves ajenas).

![DiagramaEntidades](/Screenshots/Phase2/DiagramaEntidades.png "DiagramaEntidades")

### Diagrama de Clases
A continuación se expone el Diagrama de Clases de nuestra aplicación. Por motivos de claridad visual hemos partido este diagrama en dos, haciendo una primera parte en la que se muestran las relaciones entre los controladores ImageController, loginController, logoutController y CustomErrorController con el resto de las clases; y una segunda parte en la que se muestra el controlador MainController y sus relaciones con el resto de clases.

En estediagrma hemos seguido un codigo de colores para diferenciar entre sus componentes. En verde tenemos los @Controller, en rojo tenemos los @Service, en azul tenemos los Repository y en morado los templates (.html).  

#### Diagrama de Clases (Parte 1)
![DiagramaClasesParte1](/Screenshots/Phase2/DiagramaClasesParte1.png "DiagramaClasesParte1")

#### Diagrama de Clases (Parte 2)
![DiagramaClasesParte2](/Screenshots/Phase2/DiagramaClasesParte2.png "DiagramaClasesParte2")