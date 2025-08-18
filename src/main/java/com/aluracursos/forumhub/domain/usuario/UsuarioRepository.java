package com.aluracursos.forumhub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//    Usuario findByNombreUsuario(String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    Usuario findByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);


    UserDetails findByEmail (String email);

//    Page<Usuario> findAllByActivoTrue(Pageable pageable);


    Usuario getReferenceById(Long id);


    Page<Usuario> findAll(Pageable pageable);

    Usuario getReferenceByNombreUsuario(String nombreUsuario);

    Boolean existsByNombreUsuario(String nombreUsuario);

    @Query("select u from Usuario u where u.activo = true")
    Page<Usuario> listarActivos(Pageable pageable);


}
