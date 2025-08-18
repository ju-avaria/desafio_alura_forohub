package com.aluracursos.forumhub.controller;

import com.aluracursos.forumhub.domain.usuario.Usuario;
import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import com.aluracursos.forumhub.domain.usuario.dto.ActualizarUsuarioDTO;
import com.aluracursos.forumhub.domain.usuario.dto.CrearUsuarioDTO;
import com.aluracursos.forumhub.domain.usuario.dto.DetalleUsuarioDTO;
import com.aluracursos.forumhub.domain.usuario.validations.create.ValidarCrearUsuario;
import com.aluracursos.forumhub.domain.usuario.validations.update.ValidarActualizarUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Crear topicos y publicar respuestas")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidarCrearUsuario> crearValidadores;

    @Autowired
    List<ValidarActualizarUsuario> actualizarValidadores;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo usuario en la base de datos")
    public ResponseEntity<DetalleUsuarioDTO> crearUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO,
                                                          UriComponentsBuilder uriComponentsBuilder){
        crearValidadores.forEach(v -> v.validar(crearUsuarioDTO));

        String contrasenaEncriptada = passwordEncoder.encode(crearUsuarioDTO.contrasena());
        Usuario usuario = new Usuario(crearUsuarioDTO, contrasenaEncriptada);

        usuarioRepository.save(usuario);
        var uri = uriComponentsBuilder.path("/usuarios/{nombre_usuario}").buildAndExpand(usuario.getNombreUsuario()).toUri();
        return ResponseEntity.created(uri).body(new DetalleUsuarioDTO(usuario));
    }

    @GetMapping("/all")
    @Operation(summary = "Enumera todos los usuarios")
    public ResponseEntity<Page<DetalleUsuarioDTO>> leerTodosLosUsuarios(@PageableDefault(size = 5 /*, sort = "/{ultimaActualizacion}"*/)
                                                                            Pageable pageable){
        var pagina = usuarioRepository.findAll(pageable).map(DetalleUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista solo los usuario habilitados")
    public ResponseEntity<Page<DetalleUsuarioDTO>> leerUsuariosActivos(@PageableDefault(size = 5) Pageable pageable){
//        var pagina = usuarioRepository.findAllByActivoTrue(pageable).map(DetalleUsuarioDTO::new);
        var pagina = usuarioRepository.listarActivos(pageable).map(DetalleUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuarios/{nombreUsuario}")
    @Operation(summary = "Lee un único usuario por su nombre de usuario")
    public ResponseEntity<DetalleUsuarioDTO> leerUnUsuario(@PathVariable String nombreUsuario){

        Usuario usuario = (Usuario) usuarioRepository.findByNombreUsuario(nombreUsuario);

        var datosUsuario = new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getPerfil(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getActivo()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee un único usuario por su Id")
    public ResponseEntity<DetalleUsuarioDTO> leerUnUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosUsuario = new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getPerfil(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getActivo()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @PutMapping("/{nombreUsuario}")
    @Transactional
    @Operation(summary = "Actualiza la contraseña de un usuario, perfil, nombre , apellido, email o estado")
    public ResponseEntity<DetalleUsuarioDTO> actualizarUsuario(@RequestBody @Valid ActualizarUsuarioDTO actualizarUsuarioDTO,
                                                               String nombreUsuario){
        actualizarValidadores.forEach(v -> v.validar(actualizarUsuarioDTO));

        Usuario usuario = (Usuario) usuarioRepository.findByNombreUsuario(nombreUsuario);

        if(actualizarUsuarioDTO.contrasena() != null){
            String contrasenaEncriptada = passwordEncoder.encode(actualizarUsuarioDTO.contrasena());
            usuario.actualizarUsuarioConPassword(actualizarUsuarioDTO, contrasenaEncriptada);
        } else {
            usuario.actualizarUsuario(actualizarUsuarioDTO);
        }

        var datosUsuario = new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getPerfil(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getActivo()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @DeleteMapping("/{nombre_usuario}")
    @Transactional
    @Operation(summary = "Deshabilita a un usuario")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String nombreUsuario){
        Usuario usuario = (Usuario) usuarioRepository.findByNombreUsuario(nombreUsuario);
        usuario.eliminarUsuario();
        return ResponseEntity.noContent().build();
    }
}
