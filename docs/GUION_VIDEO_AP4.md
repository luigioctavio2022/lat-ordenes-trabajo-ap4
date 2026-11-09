# Narración del video AP4

Grabación de Luis Ignacio Ramírez. Duración: 3 minutos y 21 segundos. Texto con puntuación y nombres técnicos normalizados para subtítulos.

Hola, soy Luis Ignacio Ramírez. En esta presentación voy a explicar mi proyecto integrador, un sistema local de gestión de órdenes de trabajo para mantenimiento de líneas de alta tensión de 132 kilovoltios.
El proyecto toma como referencia el sector de mantenimiento de ENERSA, Zona Oeste. La problemática abordada es el registro de tareas en papel y su posterior transcripción, que dificulta localizar y consultar la información. La propuesta busca concentrar esos registros en una aplicación de escritorio.
Para organizar el desarrollo se aplicaron las etapas del Proceso Unificado: inicio, elaboración, construcción y transición. Se definieron el problema, el alcance, la arquitectura y las pruebas. La implementación es un prototipo operacional que permite crear órdenes, consultarlas y actualizar su estado.
El programa utiliza Java, una interfaz gráfica Swing y una base de datos MySQL. Se seleccionó el patrón DAO para separar el acceso a los datos de las validaciones y de la presentación. Así, las sentencias SQL quedan concentradas en las clases de persistencia.
También se incorporó una clase abstracta llamada Orden, de la que heredan las órdenes preventivas y correctivas. Ambas comparten atributos y validaciones, pero identifican su tipo mediante un método propio.
Desde el menú principal podemos crear una orden ingresando línea, piquete, descripción y tipo de mantenimiento. Antes de guardarla, el sistema valida los campos. Cuando la inserción termina correctamente, muestra una confirmación.
La opción Consultar permite listar las órdenes o buscar una por su identificador. Al seleccionar un registro, podemos cambiar su estado a pendiente, en proceso o finalizada. Los datos se recuperan y actualizan directamente en MySQL mediante consultas parametrizadas.
Los arreglos se utilizan para los encabezados, los tipos y los estados. En cambio, ArrayList almacena una cantidad variable de órdenes recuperadas de la base.
Como función complementaria, se permite guardar y recuperar órdenes mediante archivos. La recuperación utiliza una transacción. Si alguna inserción falla, se revierte el lote completo.
El tratamiento de excepciones permite informar errores de validación, archivos o conexión, sin presentar la operación como exitosa. Además, los recursos de conexión se cierran automáticamente.
Para verificar el prototipo se realizaron 23 comprobaciones, todas aprobadas, incluyendo operaciones reales con MySQL.
Esta versión utiliza una tabla y mantiene un alcance acotado. La administración de usuarios, las licencias eléctricas y el historial completo quedan previstos para futuras ampliaciones.
