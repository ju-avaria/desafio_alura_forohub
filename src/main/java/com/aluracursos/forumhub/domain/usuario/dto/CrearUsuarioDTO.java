package com.aluracursos.forumhub.domain.usuario.dto;

import com.aluracursos.forumhub.domain.usuario.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearUsuarioDTO(
        @NotBlank String nombreUsuario,
        @NotBlank String contrasena,
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @Email String email
) {
}

