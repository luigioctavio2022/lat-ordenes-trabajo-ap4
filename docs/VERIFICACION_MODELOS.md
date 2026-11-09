# Verificación de los modelos

Se contrastaron CU01–CU06 con el código Java y sus pruebas de apoyo. El diseño ampliado normalizado tiene siete CREATE TABLE y siete FK NOT NULL. Se eliminó id_zona de ordenes_trabajo y se exigieron estados NOT NULL. Se comprobó la creación del DDL en MySQL 8.0.46 en una base de validación independiente.

El esquema integrado con Java conserva una tabla y siete campos, sin FK. El código Java y sql/esquema.sql no se modificaron. Evidencia SQL complementaria: evidencias/VERIFICACION_SQL_TP2.txt.
