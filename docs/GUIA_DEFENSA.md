# Guía para preparar la defensa

La entrega es individual. Leé el informe y recorré el código para poder explicar las decisiones con tus palabras. El video contiene la narración grabada por el alumno y subtítulos sincronizados. Revisá que puedas ampliar oralmente cada concepto.

## Recorrido de lectura de 15 minutos
1. Cinco minutos: problema, alcance y diagrama de arquitectura del informe.
2. Cinco minutos: Main, OrdenServicio y MySqlOrdenDao. Seguí el alta desde el botón hasta el INSERT.
3. Cinco minutos: matriz de requisitos, resultados de pruebas y límites. Practicá el guion sin leerlo literalmente.

## Preguntas posibles
- ¿Qué problema resolvés? Centralizar un registro mínimo de órdenes para consultar intervenciones desde una computadora local. El beneficio esperado no fue medido en operación real.
- ¿Por qué DAO? Para que el servicio dependa de un contrato de acceso y no de sentencias SQL. MySqlOrdenDao implementa ese contrato.
- ¿DAO es toda la arquitectura? No. Es el patrón elegido para persistencia dentro de una aplicación organizada en vista, servicio, modelo y acceso a datos.
- ¿Dónde usás abstracción? Orden no se instancia directamente. Preventiva y Correctiva reutilizan invariantes y definen getTipo. OrdenDao agrega una interfaz independiente del proveedor de persistencia.
- ¿En qué se diferencian arreglo y ArrayList? Los encabezados, tipos, estados y columnas de una fila tienen posiciones fijas. La cantidad de órdenes recuperadas varía, por eso se usa ArrayList<Orden>.
- ¿Qué pasa si MySQL falla? El DAO propaga SQLException, libera recursos y la vista muestra un aviso. El registro técnico conserva el diagnóstico. No se informa éxito si el INSERT falló.
- ¿Por qué una transacción al recuperar archivos? Varias inserciones forman una operación: commit confirma todas y rollback revierte el lote si alguna falla.
- ¿El archivo conserva las identidades? No. Se recuperan campos operacionales con ID y fecha nuevos; repetirlo puede duplicar órdenes. No es un backup completo.
- ¿Por qué una sola tabla? Es un recorte operacional permitido por la consigna. El diseño ampliado de siete tablas queda como evolución, y no se lo presenta como implementado.
- ¿Se hicieron pruebas reales? Sí, 23 comprobaciones en un entorno separado, con Java 17, MySQL 8.0.46 y Connector/J 8.4.0. Revisá los resultados y las capturas. No se probó rendimiento ni despliegue en tu PC.
- ¿Qué queda para cerrar la entrega? Enlace GitHub, exportación en Calibri 11 y revisión de la presentación. La ejecución desde IntelliJ en la PC Windows ya se documentó. No afirmes actividades académicas que no realizaste.

## Antes de enviar
Abrí el PDF, comprobá el nombre requerido y revisá el enlace de GitHub desde una sesión donde el docente pueda acceder. El proyecto y el video deben corresponder a esta versión AP4.
