-- Agregar rol USUARIO si tu tabla ya existe con el esquema anterior
USE gestor_documentos;

ALTER TABLE usuarios
    MODIFY rol ENUM('ADMINISTRADOR', 'CLIENTE', 'USUARIO', 'ASESOR') NOT NULL;

-- Opcional: quitar ASESOR si ya no lo usas
-- ALTER TABLE usuarios MODIFY rol ENUM('ADMINISTRADOR', 'CLIENTE', 'USUARIO') NOT NULL;
