package com.aluracursos.forumhub.domain.usuario;


import com.aluracursos.forumhub.domain.usuario.dto.ActualizarUsuarioDTO;
import com.aluracursos.forumhub.domain.usuario.dto.CrearUsuarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    private String nombre;
    private String apellido;
    private String email;
    private Boolean activo;

    public Usuario(@NotBlank CrearUsuarioDTO usuario, String contrasenaEnCriptada) {
        this.nombreUsuario = usuario.nombreUsuario();
        this.contrasena = contrasenaEnCriptada;
        this.perfil = Perfil.ESTUDIANTE;
        this.nombre = usuario.nombre();
        this.apellido = usuario.apellido();
        this.email = usuario.email();
        this.activo = true;

    }

    public void actualizarUsuarioConPassword(ActualizarUsuarioDTO actualizarUsuarioDTO, String contrasenaEncriptada){
        if(actualizarUsuarioDTO.contrasena() != null){
            this.contrasena = contrasenaEncriptada;
        }
        if(actualizarUsuarioDTO.perfil()  != null) {
            this.perfil = actualizarUsuarioDTO.perfil();
        }
        if(actualizarUsuarioDTO.nombre()  != null) {
            this.nombre = actualizarUsuarioDTO.nombre();
        }
        if (actualizarUsuarioDTO.apellido() != null) {
            this.apellido = actualizarUsuarioDTO.apellido();
        }
        if(actualizarUsuarioDTO.email() != null) {
            this.email = actualizarUsuarioDTO.email();
        }
        if(actualizarUsuarioDTO.activo() != null) {
            this.activo = actualizarUsuarioDTO.activo();
        }
    }

    public void actualizarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO){

        if(actualizarUsuarioDTO.perfil() != null){
            this.perfil = actualizarUsuarioDTO.perfil();
        }
        if(actualizarUsuarioDTO.nombre()  != null) {
            this.nombre = actualizarUsuarioDTO.nombre();
        }
        if (actualizarUsuarioDTO.apellido() != null) {
            this.apellido = actualizarUsuarioDTO.apellido();
        }
        if(actualizarUsuarioDTO.email() != null) {
            this.email = actualizarUsuarioDTO.email();
        }
        if(actualizarUsuarioDTO.activo() != null) {
            this.activo = actualizarUsuarioDTO.activo();
        }
    }

    public void eliminarUsuario() {
        this.activo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
