package com.aluracursos.forumhub.domain.curso.dto;

import com.aluracursos.forumhub.domain.curso.Categoria;
import com.aluracursos.forumhub.domain.curso.Curso;

public record DetalleCursoDTO(
        Long id,
        String nombre,
        Categoria categoria,
        Boolean activo
) {
    public DetalleCursoDTO(Curso curso){
        this(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria(),
                curso.getActivo()
        );
    }
}
