package com.aluracursos.forumhub.domain.topico.dto;

import com.aluracursos.forumhub.domain.curso.Categoria;
import com.aluracursos.forumhub.domain.topico.Topico;
import com.aluracursos.forumhub.domain.topico.TopicoStatus;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record DetalleTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        LocalDateTime ultimaActualizacion,
        TopicoStatus status,
        String usuario,
        String curso,
        Categoria categoriaCurso

) {

    public DetalleTopicoDTO(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getStatus(),
                topico.getUsuario().getNombre(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}
