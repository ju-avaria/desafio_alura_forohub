package com.aluracursos.forumhub.domain.usuario.dto;

import com.aluracursos.forumhub.domain.usuario.Perfil;
import com.aluracursos.forumhub.domain.usuario.Usuario;

public record DetalleUsuarioDTO(
        Long id,
        String nombre_usario,
        Perfil perfil,
        String nombre,
        String apellido,
        String email,
        Boolean activo
) {
    public DetalleUsuarioDTO (Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getPerfil(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getActivo()
        );
    }
}
