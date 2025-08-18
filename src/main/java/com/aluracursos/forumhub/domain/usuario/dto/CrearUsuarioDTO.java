package com.aluracursos.forumhub.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CrearUsuarioDTO(
        @NotBlank String nombre_usuario,
        @NotBlank String contrasena,
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @Email String email
) {
}

