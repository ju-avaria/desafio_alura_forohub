package com.aluracursos.forumhub.controller;

import com.aluracursos.forumhub.domain.respuesta.Respuesta;
import com.aluracursos.forumhub.domain.respuesta.RespuestaRepository;
import com.aluracursos.forumhub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.aluracursos.forumhub.domain.respuesta.dto.CrearRespuestaDTO;
import com.aluracursos.forumhub.domain.respuesta.dto.DetalleRespuestaDTO;
import com.aluracursos.forumhub.domain.respuesta.validations.create.ValidarRespuestaCreada;
import com.aluracursos.forumhub.domain.respuesta.validations.update.ValidarRespuestaActualizada;
import com.aluracursos.forumhub.domain.topico.Topico;
import com.aluracursos.forumhub.domain.topico.TopicoRepository;
import com.aluracursos.forumhub.domain.topico.TopicoStatus;
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
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuesta" , description = "Solo una puede se la solución al problema.")
public class RespuestaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidarRespuestaCreada> crearValidadores;

    @Autowired
    List<ValidarRespuestaActualizada> actualizadarValidadores;

    @PostMapping
    @Transactional
    @Operation(summary = "Resgistra una nueva respuesta en la base de datos, vinculada a un usuario y un topico existente")
    public ResponseEntity<DetalleRespuestaDTO> crearRespuesta(@RequestBody @Valid CrearRespuestaDTO crearRespuestaDTO,
                                                              UriComponentsBuilder uriComponentsBuilder){
        crearValidadores.forEach(v -> v.validar(crearRespuestaDTO));

        Usuario usuario = usuarioRepository.getReferenceById(crearRespuestaDTO.usuarioId());
        Topico topico = topicoRepository.findById(crearRespuestaDTO.topicoId()).get();

        var respuesta = new Respuesta(crearRespuestaDTO, usuario, topico);
        respuestaRepository.save(respuesta);

        var uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleRespuestaDTO(respuesta));
    }

    @GetMapping("topico/{topicoId}")
    @Operation(summary = "Lee todas las respuestas del tema dado")
    public ResponseEntity<Page<DetalleRespuestaDTO>> leerRespuestaDelTopico(@PageableDefault(size = 5, sort = "{ultimaActualizacion}",
            direction = Sort.Direction.ASC)Pageable pageable, @PathVariable Long topicoId) {

        var pagina = respuestaRepository.findAllByTopicoId(topicoId, pageable).map(DetalleRespuestaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuario/{nombreUsuario}")
    @Operation(summary = "Lee todas las respuestas del nombre de usuario proporcionado")
    public ResponseEntity<Page<DetalleRespuestaDTO>> leerRespuestasDelUsuario(@PageableDefault(size = 5, sort = "{ultimaactualizacion}",
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long usuarioId){
        var pagina = respuestaRepository.findAllByUsuarioId(usuarioId, pageable).map(DetalleRespuestaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("{id}")
    @Operation(summary = "Lee una única respuesta por su Id")
    public ResponseEntity<DetalleRespuestaDTO> leerUnaRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

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

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Actualiza el mensaje de la respuesta, la solucion o el estado de la respuesta")
    public ResponseEntity<DetalleRespuestaDTO> actualizarRespuesta(@RequestBody @Valid ActualizarRespuestaDTO actualizarRespuestaDTO, @PathVariable Long id){
        actualizadarValidadores.forEach(v -> v.validar(actualizarRespuestaDTO, id));
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(actualizarRespuestaDTO);

        if(actualizarRespuestaDTO.esLaSolucion()){
            var temaResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            temaResuelto.setStatus(TopicoStatus.RESUELTO);
        }

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


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina una respuesta por su Id")
    public ResponseEntity<?> borrarRespuesta(@PathVariable Long id){
        Respuesta respuesta =respuestaRepository.getReferenceById(id);
        respuesta.eliminarRespuesta();
        return ResponseEntity.noContent().build();
    }

}
