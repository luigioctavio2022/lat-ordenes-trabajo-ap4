# Sistema local de órdenes de trabajo LAT 132 kV — AP4
Ramirez Luis Ignacio · VINF04473 · Seminario de Práctica de Analista de Sistemas de Información

## Alcance
Prototipo operacional Java Swing + MySQL: alta, consulta por ID o listado, cambio de estado y guardado/recuperación en archivo. Un esquema real de una tabla coincide con el informe AP4. El diseño ampliado de siete tablas permanece como evolución futura; no se afirma haber implementado 28 tablas.

## Revisión ejecutada
Java 17.0.20, MySQL 8.0.46 y Connector/J 8.4.0. Veintitrés comprobaciones automatizadas aprobadas y nueve capturas de la interfaz ejecutada en pantalla virtual. Datos sintéticos, sin conexión con sistemas de ENERSA. Los registros completos están en evidencias. Las 23 pruebas se ejecutaron mediante línea de comandos en el entorno separado. Luego el alumno abrió y ejecutó el proyecto en IntelliJ IDEA 2026.3 con Temurin 17.0.21.9 y MySQL 8.4.13 en Windows; las capturas windows-01, windows-02 y windows-03 documentan el recorrido manual. No se afirma que se hayan repetido allí las 23 pruebas automatizadas.

## Verificación manual en la PC del alumno
El 9 de noviembre de 2026 se documentaron seis casos manuales complementarios: alta de correctiva ID 2, actualización a EN_PROCESO, consulta tras reabrir, rechazo de línea vacía sin inserción, guardado de dos órdenes y recuperación con nuevos ID 3 y 4. La consulta posterior muestra cuatro filas con datos y estados conservados. Los detalles están en docs/PRUEBAS_MANUALES_WINDOWS.csv, evidencias/VERIFICACION_MANUAL_WINDOWS.md y el anexo E del informe completo. Estas comprobaciones no se suman al conteo de 23 pruebas automatizadas ni representan una repetición de aquellas. No se indujo un error de importación durante el recorrido manual.

## Uso sin instalar en la PC del trabajo
El informe Word, las capturas y el video se pueden revisar como archivos. El PDF definitivo se exporta desde Word con Calibri disponible. Para ejecutar el programa hace falta un equipo autorizado con JDK y MySQL, que puede ser otro equipo. No ejecute instaladores en una PC que no lo permita.

## Requisitos del equipo de ejecución
JDK 17+, MySQL 8.0.16+ (por las restricciones CHECK), IDE Java con soporte Maven, por ejemplo Eclipse o IntelliJ IDEA. La apertura y ejecución desde IntelliJ IDEA se documentan mediante las capturas aportadas por el alumno. El proyecto incluye pom.xml, fuentes convencionales y scripts de compilación.

## Configuración
1. Ejecutar sql/esquema.sql con una cuenta administradora. Crea lat_ot_ap4, independiente de las bases previas.
2. Crear una cuenta de aplicación con contraseña propia y permisos SELECT, INSERT y UPDATE sobre lat_ot_ap4.*. No usar root para la aplicación. Configurar la URL y cuenta para localhost.
3. Definir LAT_DB_URL, LAT_DB_USER y LAT_DB_PASSWORD como variables de entorno de la ejecución. Ejemplo de URL: jdbc:mysql://localhost:3306/lat_ot_ap4?connectTimeout=4000&socketTimeout=8000. No publique contraseñas en GitHub.
4. Importar pom.xml como proyecto Maven en la IDE; seleccionar JDK 17 y ejecutar lat.Main. Maven resuelve mysql-connector-j 8.4.0.
5. Alternativa sin Maven: el JAR compilado está incluido. Windows: java -cp "lat-ap4.jar;lib/*" lat.Main. Linux: java -cp "lat-ap4.jar:lib/*" lat.Main. El conector incluido conserva su licencia en licencias.
6. Para recompilar: compilar.bat en Windows o sh compilar.sh en Linux.

## Pruebas reproducibles
Utilizar una base exclusiva de pruebas creada con el mismo esquema y una cuenta de prueba. El test inserta registros de prueba y modifica uno de ellos; no ejecutarlo sobre datos operativos. Configurar las variables LAT_DB_* para esa base, compilar src/test/java/lat/Pruebas.java con classpath out y ejecutar lat.Pruebas con out y lib/* en el classpath. La clase Capturas recorre Swing automáticamente y requiere pantalla gráfica de 1280×800, ejecutarse desde la carpeta que contiene AP4, y localización de botones OK compatible con el entorno validado. No es necesaria para usar el programa; se incluye como trazabilidad de las capturas producidas.

## Menú y archivos
Opciones: Nueva orden, Consultar, Cambiar estado, Guardar archivo, Recuperar archivo, Salir. El filtro vacío consulta todas; ID positivo consulta una orden. Los archivos .lat son binarios versionados LAT-OT-1, no legibles como CSV. Guardar consulta todas las órdenes, sin aplicar el filtro visible. Recuperar valida primero todo el archivo y luego pide confirmación; inserta las órdenes con nuevos ID en una transacción. Repetir la recuperación genera duplicados; no restaura los ID ni la fecha original. No es una copia completa de seguridad de MySQL.

## Evidencia y límites
La aplicación no gestiona permisos, licencias de trabajo, historial de estados, autenticación ni autorización de maniobras. No sustituye procedimientos eléctricos. Cambiar estado permite cualquiera de los tres estados del prototipo. No hay sincronización móvil ni servicios REST. MySQL puede negociar TLS en su conexión JDBC; esto no implica una capa de servicios HTTPS. No se evaluaron concurrencia multiusuario, rendimiento, carga, accesibilidad ni recuperación automática ante una caída del equipo. No se midieron mejoras operativas reales.

## Repositorio y presentación
Repositorio público del alumno: https://github.com/luigioctavio2022/lat-ordenes-trabajo-ap4
La publicación se documentó mediante la captura aportada por el alumno. Este paquete incorpora el informe integral de las actividades 1 a 4; para actualizar la copia ya publicada, reemplazar la documentación del repositorio con esta versión. El código Java de producción conserva la versión probada.

El video utiliza la voz aportada por Luis Ignacio Ramírez y subtítulos sincronizados. Dura 3 minutos y 21 segundos; se incluyen MP4 y SRT.

## Formato del informe
El informe integral se encuentra en docs/RAMIREZ-LUIS-IGNACIO-AP4.docx. Recupera el desarrollo del negocio de la actividad inicial, mantiene las correcciones dentro de sus secciones y organiza las cuatro actividades con portada e índice actualizable. Configuración: Calibri 11, A4 y espaciado simple, conservando el logotipo y el estilo del documento inicial.

El PDF abreviado anterior se excluye de este paquete para evitar su entrega por error. Abrir el Word completo en un equipo con Calibri, actualizar todos los campos y exportar como RAMIREZ-LUIS-IGNACIO-AP4.PDF. Revisar el resultado y cargar ese PDF junto con la documentación actualizada en GitHub y en el aula, según corresponda.

## Configuración de ejecución en IntelliJ
Usar una configuración de tipo Application, clase principal lat.Main, JDK 17 y classpath del módulo Maven. Definir LAT_DB_USER y LAT_DB_PASSWORD en el editor de variables de entorno. No editar workspace.xml manualmente. Mantener las configuraciones con claves fuera de GitHub; .idea y .run quedan excluidas por .gitignore. Ejecutar el JAR con el classpath indicado en este README, incluyendo lib/*; el comando java -jar aislado no incorpora el conector.

## Correspondencia con TP1 a TP4
La matriz docs/CONTROL_ENTREGABLES_TP1_TP4.csv identifica cada requisito y su evidencia. Los seis casos de uso se detallan en docs/CASOS_USO.md y su trazabilidad en docs/TRAZABILIDAD_CASOS_USO.csv. Los diagramas se incluyen como PNG, SVG y, cuando corresponde, PlantUML.

El esquema operativo es sql/esquema.sql, con una tabla. El modelo ampliado de docs/modelo-ampliado-propuesto.sql contiene siete tablas y siete claves foráneas: es una propuesta normalizada para la evolución, no la base usada por Java. Sus cardinalidades están explicadas en docs/CARDINALIDADES.md.

La práctica sql/practica_tp2.sql demuestra inserción, consulta, actualización y borrado en una base de demostración independiente. Requiere una cuenta autorizada para crear esa base y ejecutar las operaciones; no amplía permisos ni agrega una función de borrado a la aplicación. Su ejecución complementaria en MySQL 8.0.46 y la creación del modelo propuesto están registradas en evidencias/VERIFICACION_SQL_TP2.txt, separadas de las 23 comprobaciones anteriores y las seis pruebas manuales en Windows.

La participación en foros, encuentros y comunicaciones debe acreditarse mediante actividades reales del alumno.

## Revisión de citas y referencias
Informe revisado: referencias ordenadas, sangría francesa, títulos en cursiva, citas concordantes y figuras y tablas numeradas, tituladas y con notas de procedencia. Se conserva el formato solicitado por la materia: A4, Calibri 11 y espaciado simple. Exportar el PDF definitivo desde Word y revisar su presentación.
