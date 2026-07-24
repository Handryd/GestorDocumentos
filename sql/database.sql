-- =====================================================
-- BASE DE DATOS
-- GESTOR DE DOCUMENTOS
-- =====================================================

DROP DATABASE IF EXISTS gestor_documentos;

CREATE DATABASE gestor_documentos
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE gestor_documentos;

-- =====================================================
-- TABLA USUARIOS
-- =====================================================

CREATE TABLE usuarios (

    id INT AUTO_INCREMENT PRIMARY KEY,

    nombre VARCHAR(100) NOT NULL,

    apellido VARCHAR(100) NOT NULL DEFAULT '',

    username VARCHAR(50) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    correo VARCHAR(100),

    rol ENUM(
        'ADMINISTRADOR',
        'CLIENTE',
        'USUARIO'
    ) NOT NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE

);

-- =====================================================
-- TABLA CLIENTES
-- =====================================================

CREATE TABLE clientes (

    id INT AUTO_INCREMENT PRIMARY KEY,

    codigo_expediente VARCHAR(20) NOT NULL UNIQUE,

    expediente_padre VARCHAR(20),

    nombre VARCHAR(100) NOT NULL,

    correo VARCHAR(100),

    telefono VARCHAR(20),

    fecha_registro TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP

);

-- =====================================================
-- TABLA EXPEDIENTES
-- =====================================================

CREATE TABLE expedientes (

    id INT AUTO_INCREMENT PRIMARY KEY,

    cliente_id INT NOT NULL,

    nombre_expediente VARCHAR(150) NOT NULL,

    descripcion TEXT,

    porcentaje_avance DECIMAL(5,2)
    DEFAULT 0.00,

    estado ENUM(
        'PENDIENTE',
        'EN_PROCESO',
        'COMPLETO'
    ) DEFAULT 'PENDIENTE',

    fecha_creacion TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_expediente_cliente
    FOREIGN KEY (cliente_id)
    REFERENCES clientes(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE

);

-- =====================================================
-- TABLA DOCUMENTOS
-- =====================================================

CREATE TABLE documentos (

    id INT AUTO_INCREMENT PRIMARY KEY,

    expediente_id INT NOT NULL,

    nombre_documento VARCHAR(150) NOT NULL,

    entregado BOOLEAN DEFAULT FALSE,

    fecha_entrega DATE,

    observaciones TEXT,

    CONSTRAINT fk_documento_expediente
    FOREIGN KEY (expediente_id)
    REFERENCES expedientes(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE

);

-- =====================================================
-- USUARIO ADMINISTRADOR INICIAL
-- =====================================================

INSERT INTO usuarios
(
    nombre,
    apellido,
    username,
    password,
    correo,
    rol,
    activo
)
VALUES
(
    'Admin',
    'Sistema',
    'admin',
    'admin123',
    'admin@gestor.com',
    'ADMINISTRADOR',
    TRUE
);

-- =====================================================
-- CLIENTES DE EJEMPLO
-- =====================================================

INSERT INTO clientes
(
    codigo_expediente,
    expediente_padre,
    nombre,
    correo,
    telefono
)
VALUES

(
    'EXP-001',
    NULL,
    'Arcelia',
    'arcelia@correo.com',
    '9621111111'
),

(
    'EXP-001-02',
    'EXP-001',
    'Brenda',
    'brenda@correo.com',
    '9622222222'
),

(
    'EXP-001-03',
    'EXP-001',
    'Juan',
    'juan@correo.com',
    '9623333333'
),

(
    'EXP-002',
    NULL,
    'Carlos',
    'carlos@correo.com',
    '9624444444'
);

-- =====================================================
-- EXPEDIENTES DE EJEMPLO
-- =====================================================

INSERT INTO expedientes
(
    cliente_id,
    nombre_expediente,
    descripcion,
    porcentaje_avance,
    estado
)
VALUES

(
    1,
    'Expediente Laboral',
    'Documentación inicial',
    40,
    'EN_PROCESO'
),

(
    2,
    'Actualización de Documentos',
    'Expediente derivado',
    70,
    'EN_PROCESO'
),

(
    3,
    'Documentación Complementaria',
    'Documentos adicionales',
    100,
    'COMPLETO'
);

-- =====================================================
-- DOCUMENTOS DE EJEMPLO
-- =====================================================

INSERT INTO documentos
(
    expediente_id,
    nombre_documento,
    entregado,
    fecha_entrega
)
VALUES

(
    1,
    'INE',
    TRUE,
    CURDATE()
),

(
    1,
    'Comprobante de domicilio',
    FALSE,
    NULL
),

(
    2,
    'CURP',
    TRUE,
    CURDATE()
);