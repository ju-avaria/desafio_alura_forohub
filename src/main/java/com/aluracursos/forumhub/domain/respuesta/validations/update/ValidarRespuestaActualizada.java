package com.aluracursos.forumhub.domain.respuesta.validations.update;

import com.aluracursos.forumhub.domain.respuesta.dto.ActualizarRespuestaDTO;

public interface ValidarRespuestaActualizada {

    void validar(ActualizarRespuestaDTO datos, Long respuestId);
}
