package com.aluracursos.forumhub.domain.topico.dto;

import com.aluracursos.forumhub.domain.topico.TopicoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull TopicoStatus status,
        @NotBlank Long usuarioId,
        @NotBlank Long cursoId
) {
}




