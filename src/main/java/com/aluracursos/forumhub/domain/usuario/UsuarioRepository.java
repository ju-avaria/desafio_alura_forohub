package com.aluracursos.forumhub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByNombreUsuario(String nombreUsuario);

    UserDetails findByEmail (String email);

    Page<Usuario> findAllByActivoTrue(Pageable pageable);

    Usuario getReferenceById(Long id);

    Page<Usuario> findAll(Pageable pageable);

    Usuario getReferenceByNombreUsuario(String nombreUsuario);

    Boolean existsByNombreUsuario(String nombreUsuario);
}
