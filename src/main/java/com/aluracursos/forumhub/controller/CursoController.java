package com.aluracursos.forumhub.controller;

import com.aluracursos.forumhub.domain.curso.Curso;
import com.aluracursos.forumhub.domain.curso.CursoRepository;
import com.aluracursos.forumhub.domain.curso.dto.ActualizarCursoDTO;
import com.aluracursos.forumhub.domain.curso.dto.CrearCursoDTO;
import com.aluracursos.forumhub.domain.curso.dto.DetalleCursoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso", description = "Puede pertencer a una" )
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    // @Operation(sumary = "Registrar un nuevo Curso en al BD";
    public ResponseEntity<DetalleCursoDTO> crearTopico(@RequestBody CrearCursoDTO crearCursoDTO,
                                                       UriComponentsBuilder uriComponentsBuilder){

        Curso curso = new Curso(crearCursoDTO);
        cursoRepository.save(curso);
        var uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalleCursoDTO(curso));
    }

    @GetMapping("/all")
    @Operation(summary = "Lee todos los cursos independiente d ela categoria")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(Pageable pageable){
        var pagina = cursoRepository.findAll(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista de cursos Activos")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursosActivos(Pageable pageable){
        var pagina = cursoRepository.findAllByActivoTrue(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee un solo curso por ID")
    public ResponseEntity<DetalleCursoDTO> listarUnCurso(@PathVariable Long id){
        Curso curso = cursoRepository.getReferenceById(id);
        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria(),
                curso.getActivo()
                );
        return ResponseEntity.ok(datosDelCurso);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el nombre , la categoria y el status")
    public ResponseEntity<DetalleCursoDTO> actualizarCurso(@RequestBody @Valid ActualizarCursoDTO actualizarCursoDTO, @PathVariable Long id){

        Curso curso = cursoRepository.getReferenceById(id);

        curso.actualizarCurso(actualizarCursoDTO);

        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria(),
                curso.getActivo()
        );
        return ResponseEntity.ok(datosDelCurso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        Curso curso = cursoRepository.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }
}

