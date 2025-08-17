package com.aluracursos.forumhub.controller;


import com.aluracursos.forumhub.domain.curso.Curso;
import com.aluracursos.forumhub.domain.curso.CursoRepository;
import com.aluracursos.forumhub.domain.respuesta.Respuesta;
import com.aluracursos.forumhub.domain.respuesta.RespuestaRepository;
import com.aluracursos.forumhub.domain.respuesta.dto.DetalleRespuestaDTO;
import com.aluracursos.forumhub.domain.topico.Topico;
import com.aluracursos.forumhub.domain.topico.TopicoStatus;
import com.aluracursos.forumhub.domain.topico.dto.ActualizarTopicoDTO;
import com.aluracursos.forumhub.domain.topico.dto.CrearTopicoDTO;
import com.aluracursos.forumhub.domain.topico.TopicoRepository;
import com.aluracursos.forumhub.domain.topico.dto.DetalleTopicoDTO;
import com.aluracursos.forumhub.domain.topico.validations.create.ValidarTopicoCreado;
import com.aluracursos.forumhub.domain.topico.validations.update.ValidarTopicoActualizado;
import com.aluracursos.forumhub.domain.usuario.Usuario;
import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topico", description = "Esta vinculado a un curso y un usuario")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidarTopicoCreado> crearValidadores;

    @Autowired
    List<ValidarTopicoActualizado> avtualizarValidadores;


    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo topico en la base de datos")
    public ResponseEntity<DetalleTopicoDTO> crearTopico(@RequestBody @Valid CrearTopicoDTO crearTopicoDTO,
                                                        UriComponentsBuilder uriComponentsBuilder){
        crearValidadores.forEach(v -> v.validar(crearTopicoDTO));

        Usuario usuario = usuarioRepository.findById(crearTopicoDTO.usuarioId()).get();
        Curso curso = cursoRepository.findById(crearTopicoDTO.cursoId()).get();
        Topico topico = new Topico(crearTopicoDTO, usuario, curso);

        topicoRepository.save(topico);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleTopicoDTO(topico));
    }

    @GetMapping("/all")
    @Operation(summary = "Lee todos los temas independientemente de su status")
    public ResponseEntity<Page<DetalleTopicoDTO>> leerTopicosLosTopicos(@PageableDefault(size = 5,sort = "/{ultimaActualizacion}",
            direction = Sort.Direction.ASC) Pageable pageable){
        var pagina = topicoRepository.findAll(pageable).map(DetalleTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }


    @GetMapping
    @Operation(summary = "Lista de temas abierto y cerrados")
    public ResponseEntity<Page<DetalleTopicoDTO>> leerTopicosNoEliminados(@PageableDefault(size = 5,sort = "/{ultimaActualizacion}",
    direction = Sort.Direction.ASC) Pageable pageable){
        var pagina = topicoRepository.findAllByStatusIsNot(TopicoStatus.ELIMINADO, pageable).map(DetalleTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee un único tema por su Id")
    public  ResponseEntity<DetalleTopicoDTO> leerUnTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DetalleTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getStatus(),
                topico.getUsuario().getNombreUsuario(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(datosTopico);
    }




    @GetMapping("/{id}/solucion")
    @Operation(summary = "Lee la respuesta del topico marcada como solucion")
    public ResponseEntity<DetalleRespuestaDTO> leerSolucionTopico(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceByTopicoId(id);

        var datosRespuesta = new DetalleRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getEsLaSolucion(),
                respuesta.getFueBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getNombreUsuario(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el titulo, el mensaje, el estado o el Id del curso de un tema")
    public ResponseEntity<DetalleTopicoDTO> actualizarTopico(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO, Long id) {
        avtualizarValidadores.forEach(v -> v.validar(actualizarTopicoDTO));

        Topico topico = topicoRepository.getReferenceById(id);

        if (actualizarTopicoDTO.cursoId() != null) {
            Curso curso = cursoRepository.getReferenceById(actualizarTopicoDTO.cursoId());
        } else {
            topico.actualizarTopico(actualizarTopicoDTO);
        }

        var datosTopico = new DetalleTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getStatus(),
                topico.getUsuario().getNombreUsuario(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topico")
    public  ResponseEntity<?> eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }
}
