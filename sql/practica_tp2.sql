-- Práctica TP2 en una base independiente. Requiere una cuenta autorizada.
-- No utiliza ni borra registros de lat_ot_ap4; no amplía permisos de lat_app.
-- El DDL se confirma implícitamente: la base y tabla de demostración permanecen.
CREATE DATABASE IF NOT EXISTS lat_ap4_tp2_demostracion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lat_ap4_tp2_demostracion;
CREATE TABLE IF NOT EXISTS orden_trabajo (
 id INT AUTO_INCREMENT PRIMARY KEY,
 linea VARCHAR(100) NOT NULL,
 piquete VARCHAR(30) NOT NULL,
 descripcion VARCHAR(500) NOT NULL,
 tipo ENUM('PREVENTIVA','CORRECTIVA') NOT NULL,
 estado ENUM('PENDIENTE','EN_PROCESO','FINALIZADA') NOT NULL DEFAULT 'PENDIENTE',
 creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 INDEX idx_linea_piquete(linea,piquete),
 CONSTRAINT chk_linea CHECK (CHAR_LENGTH(TRIM(linea))>0),
 CONSTRAINT chk_piquete CHECK (CHAR_LENGTH(TRIM(piquete))>0),
 CONSTRAINT chk_descripcion CHECK (CHAR_LENGTH(TRIM(descripcion))>0)
) ENGINE=InnoDB;

START TRANSACTION;
INSERT INTO orden_trabajo(linea,piquete,descripcion,tipo,estado)
VALUES('LAT-DEMO-TP2','P-DEMO','Registro académico temporal','PREVENTIVA','PENDIENTE');
SET @id_demo = LAST_INSERT_ID();
SELECT id,linea,piquete,descripcion,tipo,estado
FROM orden_trabajo WHERE id=@id_demo;
UPDATE orden_trabajo SET estado='EN_PROCESO' WHERE id=@id_demo;
SELECT estado AS estado_actualizado FROM orden_trabajo WHERE id=@id_demo;
DELETE FROM orden_trabajo WHERE id=@id_demo AND linea='LAT-DEMO-TP2';
SELECT ROW_COUNT() AS filas_borradas;
SELECT COUNT(*) AS filas_del_id_tras_borrado FROM orden_trabajo WHERE id=@id_demo;
ROLLBACK;
SELECT COUNT(*) AS filas_del_id_al_final FROM orden_trabajo WHERE id=@id_demo;
