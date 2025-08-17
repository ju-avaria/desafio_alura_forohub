CREATE TABLE respuestas(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME NOT NULL,
    esLaSolucion TINYINT(1) NOT NULL,
    fueBorrado TINYINT(1) NOT NULL,
    usuario_id BIGINT NOT NULL,
    topico_id BIGINT NOT NULL,
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);