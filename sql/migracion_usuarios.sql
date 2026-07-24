-- =====================================================
-- Migración: actualizar tabla usuarios SIN borrar datos
-- Ejecutar si tu BD tiene la columna "usuario" (esquema antiguo)
-- =====================================================

USE gestor_documentos;

-- Renombrar columna de acceso (solo si aún se llama "usuario")
SET @tiene_usuario := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = 'gestor_documentos'
      AND TABLE_NAME = 'usuarios'
      AND COLUMN_NAME = 'usuario'
);

SET @sql := IF(
    @tiene_usuario > 0,
    'ALTER TABLE usuarios CHANGE COLUMN usuario username VARCHAR(50) NOT NULL',
    'SELECT ''Columna username ya existe'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar columnas del esquema completo si no existen
SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = 'gestor_documentos'
       AND TABLE_NAME = 'usuarios'
       AND COLUMN_NAME = 'nombre') = 0,
    'ALTER TABLE usuarios ADD COLUMN nombre VARCHAR(100) NOT NULL DEFAULT ''Usuario'' AFTER id',
    'SELECT ''Columna nombre ya existe'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = 'gestor_documentos'
       AND TABLE_NAME = 'usuarios'
       AND COLUMN_NAME = 'apellido') = 0,
    'ALTER TABLE usuarios ADD COLUMN apellido VARCHAR(100) NOT NULL DEFAULT '''' AFTER nombre',
    'SELECT ''Columna apellido ya existe'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = 'gestor_documentos'
       AND TABLE_NAME = 'usuarios'
       AND COLUMN_NAME = 'correo') = 0,
    'ALTER TABLE usuarios ADD COLUMN correo VARCHAR(100) NULL AFTER password',
    'SELECT ''Columna correo ya existe'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = 'gestor_documentos'
       AND TABLE_NAME = 'usuarios'
       AND COLUMN_NAME = 'activo') = 0,
    'ALTER TABLE usuarios ADD COLUMN activo TINYINT(1) NOT NULL DEFAULT 1 AFTER rol',
    'SELECT ''Columna activo ya existe'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Completar datos del admin
UPDATE usuarios
SET
    nombre = 'Admin',
    apellido = 'Sistema',
    correo = 'admin@gestor.com',
    activo = 1
WHERE username = 'admin';

-- Actualizar roles permitidos
ALTER TABLE usuarios
    MODIFY rol ENUM('ADMINISTRADOR', 'CLIENTE', 'USUARIO') NOT NULL;

SELECT 'Migración completada' AS resultado;
