package com.aluracursos.forumhub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearRespuestaDTO(
        @NotBlank String mensaje,
        @NotNull Long usuarioId,
        @NotNull Long topicoId
) {
}

