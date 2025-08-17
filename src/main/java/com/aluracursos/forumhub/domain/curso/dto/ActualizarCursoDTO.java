package com.aluracursos.forumhub.domain.curso.dto;

import com.aluracursos.forumhub.domain.curso.Categoria;

public record ActualizarCursoDTO(
        String nombre,
        Categoria categoria,
        Boolean activo
) {
}
