package com.aluracursos.forumhub.domain.curso.dto;

import com.aluracursos.forumhub.domain.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCursoDTO(
        @NotBlank String nombre,
        @NotNull Categoria categoria
) {
}



