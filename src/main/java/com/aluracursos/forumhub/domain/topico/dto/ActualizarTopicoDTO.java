package com.aluracursos.forumhub.domain.topico.dto;

import com.aluracursos.forumhub.domain.topico.TopicoStatus;

public record ActualizarTopicoDTO(
        String titulo,
        String mensaje,
        TopicoStatus status,
        Long cursoId
) {
}
