package com.aluracursos.forumhub.domain.curso;


import com.aluracursos.forumhub.domain.curso.dto.ActualizarCursoDTO;
import com.aluracursos.forumhub.domain.curso.dto.CrearCursoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Curso")
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private Boolean activo;

    public Curso(CrearCursoDTO crearCurso) {
        this.nombre = crearCurso.nombre();
        this.categoria = crearCurso.categoria();
        this.activo = true;
    }

    public void actualizarCurso(ActualizarCursoDTO actualizarCurso) {

        if (actualizarCurso.nombre() != null){
            this.nombre = actualizarCurso.nombre();
        }
        if (actualizarCurso.categoria() != null){
            this.categoria = actualizarCurso.categoria();
        }
        if (actualizarCurso.activo() != null){
            this.activo = actualizarCurso.activo();
        }
    }

    public void eliminarCurso() {
        this.activo = false;
    }

}
