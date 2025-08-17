package com.aluracursos.forumhub.domain.respuesta.validations.create;

import com.aluracursos.forumhub.domain.respuesta.dto.CrearRespuestaDTO;
import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaUsuarioValida implements ValidarRespuestaCreada{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CrearRespuestaDTO datos) {
        var usuarioExiste = usuarioRepository.existsById(datos.usuarioId());

        if(!usuarioExiste){
            throw new ValidationException("Este ususario no existe");
        }

        var usuarioHabilitado = usuarioRepository.findById(datos.usuarioId()).get().isEnabled();

        if(!usuarioHabilitado) {
            throw new ValidationException("Este usuario no está habilitado");
        }
    }

}
