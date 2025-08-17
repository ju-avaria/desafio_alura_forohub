package com.aluracursos.forumhub.domain.respuesta.validations.create;

import com.aluracursos.forumhub.domain.respuesta.dto.CrearRespuestaDTO;
import com.aluracursos.forumhub.domain.topico.TopicoRepository;
import com.aluracursos.forumhub.domain.topico.TopicoStatus;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaTopicoValida implements ValidarRespuestaCreada{

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(CrearRespuestaDTO datos) {
        var topicoExiste = topicoRepository.existsById(datos.topicoId());

        if(!topicoExiste){
            throw new ValidationException("Este topico no existe");
        }

        var topicoAbierto = topicoRepository.findById(datos.topicoId()).get().getStatus();

        if(topicoAbierto != TopicoStatus.NO_RESPONDIDO){
            throw new ValidationException("Este topico no esta abierto");
        }
    }
}
