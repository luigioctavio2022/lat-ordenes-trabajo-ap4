-- DISEÑO FUTURO NORMALIZADO: no utilizado por el prototipo AP4.
-- Se elimina ordenes_trabajo.id_zona; la zona se obtiene desde lineas.
-- Se exige NOT NULL en los estados, conservando sus valores DEFAULT.
-- El esquema ejecutable actual está en sql/esquema.sql.
CREATE DATABASE IF NOT EXISTS sgm_lineas_132kv
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
USE sgm_lineas_132kv;
-- 1. Tabla de Zonas
CREATE TABLE zonas (
    id_zona INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
) ENGINE=InnoDB;
-- 2. Tabla de Líneas
CREATE TABLE lineas (
    id_linea INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT NOT NULL,
    nombre_linea VARCHAR(100) NOT NULL,
    codigo_linea VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (id_zona) REFERENCES zonas(id_zona) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
-- 3. Tabla de Piquetes (Estructuras)
CREATE TABLE piquetes (
    id_piquete INT AUTO_INCREMENT PRIMARY KEY,
    id_linea INT NOT NULL,
    numero_piquete VARCHAR(20) NOT NULL,
    tipo_estructura VARCHAR(50), -- Hormigón, Metálica, etc.
    latitud DECIMAL(10, 8),
    longitud DECIMAL(11, 8),
    FOREIGN KEY (id_linea) REFERENCES lineas(id_linea) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
-- 4. Tabla de Activos de Piquetes
CREATE TABLE activos (
    id_activo INT AUTO_INCREMENT PRIMARY KEY,
    id_piquete INT NOT NULL,
    tipo_activo ENUM('VINCULO', 'AISLADOR', 'CONDUCTOR', 'HERRAJE', 'PAT', 'DESCARGADOR', 'HILO_GUARDA') NOT NULL,
    fase VARCHAR(10), -- Fase 1, Fase 2, Fase 3
    estado ENUM('EXCELENTE', 'BUENO', 'REQUIERE_MANTENIMIENTO', 'CRITICO') NOT NULL DEFAULT 'BUENO',
    FOREIGN KEY (id_piquete) REFERENCES piquetes(id_piquete) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;
-- 5. Tabla de Usuarios / Operarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    legajo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    rol ENUM('OPERARIO', 'JEFE_ZONA', 'ADMINISTRADOR') NOT NULL
) ENGINE=InnoDB;
-- 6. Tabla de Órdenes de Trabajo (OT)
CREATE TABLE ordenes_trabajo (
    id_ot INT AUTO_INCREMENT PRIMARY KEY,
    numero_guia VARCHAR(30) UNIQUE NOT NULL,
    fecha_emision DATE NOT NULL,
    id_linea INT NOT NULL,
    id_operario_responsable INT NOT NULL,
    tipo_trabajo ENUM('PROGRAMADO', 'INCIDENCIA', 'COLABORACION') NOT NULL,
    condicion_servicio ENUM('CON_TENSION', 'FUERA_DE_SERVICIO') NOT NULL,
    numero_licencia VARCHAR(50),
    hora_inicio_licencia DATETIME,
    hora_fin_licencia DATETIME,
    otorgado_por VARCHAR(100),
    retirado_por VARCHAR(100),
    trabajo_realizado TEXT,
    trabajo_a_realizar TEXT,
    estado_ot ENUM('PENDIENTE', 'EN_PROCESO', 'COMPLETADA', 'CANCELADA') NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_linea) REFERENCES lineas(id_linea),
    FOREIGN KEY (id_operario_responsable) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB;
-- 7. Tabla Detalle: Activos intervenidos en cada OT
CREATE TABLE detalle_ot_activos (
    id_ot INT NOT NULL,
    id_activo INT NOT NULL,
    observacion VARCHAR(255),
    PRIMARY KEY (id_ot, id_activo),
    FOREIGN KEY (id_ot) REFERENCES ordenes_trabajo(id_ot) ON DELETE CASCADE,
    FOREIGN KEY (id_activo) REFERENCES activos(id_activo) ON DELETE RESTRICT
) ENGINE=InnoDB;
