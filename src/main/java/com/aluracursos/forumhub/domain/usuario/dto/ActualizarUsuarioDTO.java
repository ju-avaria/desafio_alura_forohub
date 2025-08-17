package com.aluracursos.forumhub.domain.usuario.dto;

import com.aluracursos.forumhub.domain.usuario.Perfil;

public record ActualizarUsuarioDTO(
        String contrasena,
        Perfil perfil,
        String nombre,
        String apellido,
        String email,
        Boolean activo
) {
}
