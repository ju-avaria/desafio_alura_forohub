package com.aluracursos.forumhub.domain.usuario.validations.create;

import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import com.aluracursos.forumhub.domain.usuario.dto.CrearUsuarioDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDuplicado implements ValidarCrearUsuario{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CrearUsuarioDTO datos) {
        var usuarioDuplicado = usuarioRepository.findByNombreUsuario(datos.nombreUsuario());
        if(usuarioDuplicado != null){
            throw new ValidationException("Este ususario ya existe");
        }

        var emailDuplicado = usuarioRepository.findByEmail(datos.email());
        if(emailDuplicado != null){
            throw new ValidationException("Este email ya existe");
        }


    }
}
