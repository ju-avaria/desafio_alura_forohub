package com.aluracursos.forumhub.domain.respuesta.dto;

public record ActualizarRespuestaDTO(
        String mensaje,
        Boolean esLaSolucion,
        Boolean fueBorrado
) {
}
