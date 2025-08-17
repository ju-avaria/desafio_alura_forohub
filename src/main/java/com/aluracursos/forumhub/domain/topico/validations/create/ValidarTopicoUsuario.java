package com.aluracursos.forumhub.domain.topico.validations.create;

import com.aluracursos.forumhub.domain.topico.dto.CrearTopicoDTO;
import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCreado{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CrearTopicoDTO datos) {
        var existeUsuario = usuarioRepository.existsById(datos.usuarioId());

        if(!existeUsuario){
            throw new ValidationException("Este usuario no existe");
        }

        var usuarioHabilitado = usuarioRepository.findById(datos.usuarioId()).get().getActivo();
        if(!usuarioHabilitado){
            throw new ValidationException("Este usuario fue desactivado");
        }




    }
}
