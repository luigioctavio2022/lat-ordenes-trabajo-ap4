# Sistema local de órdenes de trabajo LAT 132 kV — AP4
Ramirez Luis Ignacio · VINF04473 · Seminario de Práctica de Analista de Sistemas de Información

## Alcance
Prototipo operacional Java Swing + MySQL: alta, consulta por ID o listado, cambio de estado y guardado/recuperación en archivo. Un esquema real de una tabla coincide con el informe AP4. El diseño ampliado de siete tablas permanece como evolución futura; no se afirma haber implementado 28 tablas.

## Revisión ejecutada
Java 17.0.20, MySQL 8.0.46 y Connector/J 8.4.0. Veintitrés comprobaciones automatizadas aprobadas y nueve capturas de la interfaz ejecutada en pantalla virtual. Datos sintéticos, sin conexión con sistemas de ENERSA. Los registros completos están en evidencias. Las 23 pruebas se ejecutaron mediante línea de comandos en el entorno separado. Luego el alumno abrió y ejecutó el proyecto en IntelliJ IDEA 2026.3 con Temurin 17.0.21.9 y MySQL 8.4.13 en Windows; las capturas windows-01, windows-02 y windows-03 documentan el recorrido manual. No se afirma que se hayan repetido allí las 23 pruebas automatizadas.

## Uso sin instalar en la PC del trabajo
El PDF, las capturas y el video se pueden revisar como archivos. Para ejecutar el programa hace falta un equipo autorizado con JDK y MySQL, que puede ser otro equipo. No ejecute instaladores en una PC que no lo permita.

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
La aplicación no gestiona permisos, licencias de trabajo, historial de estados, autenticación ni autorización de maniobras. No sustituye procedimientos eléctricos. Cambiar estado permite cualquiera de los tres estados del prototipo. No hay sincronización móvil ni servicios REST. MySQL puede negociar TLS en su conexión JDBC; esto no implica una capa de servicios HTTPS. No se evaluaron concurrencia multiusuario, rendimiento, carga, accesibilidad ni instalación en la PC del alumno. No se midieron mejoras operativas reales.

## Publicación pendiente
No existe todavía un enlace verificado al repositorio del alumno. El PDF lo declara; debe agregarse antes de enviar la actividad. No reemplazarlo por https://github.com/ genérico. Publicar fuentes, pom.xml, scripts, SQL, docs y evidencias; omitir out, bases de datos, credenciales y .env. El video de presentación utiliza la voz aportada por Luis Ignacio Ramírez y subtítulos sincronizados. Dura 3 minutos y 21 segundos. Se incluyen el video MP4 y los subtítulos SRT.

## Formato del informe
Se incluye una versión editable configurada en Calibri 11, A4 y espaciado simple. Al generar el PDF en este entorno se usa Carlito 11 como sustitución de Calibri, que no está disponible. Para cumplir literalmente la tipografía, exportar el DOCX desde Word en un equipo donde Calibri ya esté disponible, sin instalar nada en la PC del trabajo. Conservar el nombre RAMIREZ-LUIS-IGNACIO-AP4.PDF.

## Configuración de ejecución en IntelliJ
Usar una configuración de tipo Application, clase principal lat.Main, JDK 17 y classpath del módulo Maven. Definir LAT_DB_USER y LAT_DB_PASSWORD en el editor de variables de entorno. No editar workspace.xml manualmente. Mantener las configuraciones con claves fuera de GitHub; .idea y .run quedan excluidas por .gitignore. Ejecutar el JAR con el classpath indicado en este README, incluyendo lib/*; el comando java -jar aislado no incorpora el conector.
