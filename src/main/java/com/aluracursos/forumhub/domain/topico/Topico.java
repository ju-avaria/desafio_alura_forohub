package com.aluracursos.forumhub.domain.topico;


import com.aluracursos.forumhub.domain.respuesta.Respuesta;
import com.aluracursos.forumhub.domain.curso.Curso;
import com.aluracursos.forumhub.domain.topico.dto.ActualizarTopicoDTO;
import com.aluracursos.forumhub.domain.topico.dto.CrearTopicoDTO;
import com.aluracursos.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @Column(name = "fecha_actualizacion")
    private  LocalDateTime ultimaActualizacion;

    @Enumerated(EnumType.STRING)
    private TopicoStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

//    @OneToMany(mappedBy = "topico")
//    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(CrearTopicoDTO datos, Usuario usuario, Curso curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.status = datos.status();
        this.usuario = usuario;
        this.curso = curso;
//        this.respuestas = datos.respuestas();
    }



    public void actualizarTopicoConCurso(ActualizarTopicoDTO actualizarTopicoDTO, Curso curso) {
        if(actualizarTopicoDTO.titulo() != null){
            this.titulo =actualizarTopicoDTO.titulo();
        }
        if(actualizarTopicoDTO.mensaje() != null){
            this.mensaje = actualizarTopicoDTO.mensaje();
        }

        if(actualizarTopicoDTO.status() != null){
            this.status = actualizarTopicoDTO.status();
        }

        if(actualizarTopicoDTO.cursoId() != null) {
            this.curso = curso;
        }
    }

    public void actualizarTopico(ActualizarTopicoDTO actualizarTopicoDTO){
        if (actualizarTopicoDTO.titulo() != null){
            this.titulo = actualizarTopicoDTO.titulo();
        }

        if(actualizarTopicoDTO.mensaje() != null) {
            this.mensaje = actualizarTopicoDTO.mensaje();
        }

        if(actualizarTopicoDTO.status() != null) {
            this.status = actualizarTopicoDTO.status();
        }

        this.ultimaActualizacion = LocalDateTime.now();
    }



    public void eliminarTopico() {
        this.status = TopicoStatus.ELIMINADO;
    }

    public void setStatus(TopicoStatus status) {
        this.status = status;
    }
}
