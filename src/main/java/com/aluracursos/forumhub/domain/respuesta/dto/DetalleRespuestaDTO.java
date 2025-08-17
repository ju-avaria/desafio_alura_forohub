package com.aluracursos.forumhub.domain.respuesta.dto;

import com.aluracursos.forumhub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DetalleRespuestaDTO(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        LocalDateTime ultimaActualizacion,
        Boolean esLaSolucion,
        Boolean fueBorrado,
        Long usuarioId,
        String usuarioNombre,
        Long topicoId,
        String topico
) {

    public DetalleRespuestaDTO(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getEsLaSolucion(),
                respuesta.getFueBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getNombre(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
    }
}
