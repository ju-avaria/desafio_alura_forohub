package com.aluracursos.forumhub.domain.usuario.validations.update;

import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import com.aluracursos.forumhub.domain.usuario.dto.ActualizarUsuarioDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaActualizacionUsuario implements ValidarActualizarUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(ActualizarUsuarioDTO datos){
        if(datos.email() != null){
            var emailDuplicado = usuarioRepository.findByEmail(datos.email());
            if(emailDuplicado != null){
                throw new ValidationException("Este email ya existe");
            }
        }
    }
}
