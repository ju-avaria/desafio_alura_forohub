package com.aluracursos.forumhub.domain.topico.validations.update;

import com.aluracursos.forumhub.domain.curso.CursoRepository;
import com.aluracursos.forumhub.domain.topico.dto.ActualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoActualizado implements ValidarTopicoActualizado {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(ActualizarTopicoDTO datos) {
        if(datos.cursoId() != null){
            var existeCurso = cursoRepository.existsById(datos.cursoId());
            if(!existeCurso) {
                throw new ValidationException("Este curso no existe");
            }

            var cursoHabilitado = cursoRepository.findById(datos.cursoId()).get().getActivo();
            if(!cursoHabilitado){
                throw new ValidationException("Este curso no esta habilitado");
            }

        }
    }

}
