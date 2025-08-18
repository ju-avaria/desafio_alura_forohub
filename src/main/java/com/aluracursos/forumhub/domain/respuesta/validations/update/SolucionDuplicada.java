package com.aluracursos.forumhub.domain.respuesta.validations.update;

import com.aluracursos.forumhub.domain.respuesta.Respuesta;
import com.aluracursos.forumhub.domain.respuesta.RespuestaRepository;
import com.aluracursos.forumhub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.aluracursos.forumhub.domain.topico.TopicoRepository;
import com.aluracursos.forumhub.domain.topico.TopicoStatus;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class SolucionDuplicada implements ValidarRespuestaActualizada{

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(ActualizarRespuestaDTO datos, @PathVariable Long respuestaId ){
        if(datos.esLaSolucion()){
            Respuesta respuesta = respuestaRepository.getReferenceById(respuestaId);
            var topicoResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            if(topicoResuelto.getStatus() == TopicoStatus.RESUELTO){
                throw new ValidationException("Este topico ya esta solucionado");
            }
        }
    }
}
