-- Base propia del prototipo AP4. No modifica lat_ot de entregas anteriores.
CREATE DATABASE IF NOT EXISTS lat_ot_ap4 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lat_ot_ap4;
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
-- Con una cuenta administradora cree lat_app en localhost con contraseña propia.
-- Otorgue solamente SELECT, INSERT, UPDATE en lat_ot_ap4.* a lat_app.
