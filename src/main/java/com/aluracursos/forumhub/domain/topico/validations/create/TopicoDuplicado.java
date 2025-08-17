package com.aluracursos.forumhub.domain.topico.validations.create;


import com.aluracursos.forumhub.domain.topico.TopicoRepository;
import com.aluracursos.forumhub.domain.topico.dto.CrearTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCreado{

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(CrearTopicoDTO datos) {
        var topicoDuplicado = topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if(topicoDuplicado){
            throw new ValidationException("Este topico ya existe . Revisa /topicos/" +
                    topicoRepository.findByTitulo(datos.titulo()).getId());
        }
    }
}
