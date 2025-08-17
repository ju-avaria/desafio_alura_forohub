package com.aluracursos.forumhub.domain.topico.validations.create;

import com.aluracursos.forumhub.domain.curso.CursoRepository;
import com.aluracursos.forumhub.domain.topico.dto.CrearTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCreado implements ValidarTopicoCreado{

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(CrearTopicoDTO datos) {
        var existeCurso = cursoRepository.existsById(datos.cursoId());
        if(!existeCurso){
            throw new ValidationException("Este curso no Existe");
        }

        var cursoHabilitado = cursoRepository.findById(datos.cursoId()).get().getActivo();
        if(!cursoHabilitado){
            throw new ValidationException("Este curso no esta habilitado");
        }
    }

}
