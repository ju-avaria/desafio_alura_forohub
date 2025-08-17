package com.aluracursos.forumhub.domain.respuesta;

import com.aluracursos.forumhub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.aluracursos.forumhub.domain.respuesta.dto.CrearRespuestaDTO;
import com.aluracursos.forumhub.domain.topico.Topico;
import com.aluracursos.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    private Boolean esLaSolucion;
    private Boolean fueBorrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(CrearRespuestaDTO crearRespuestaDTO, Usuario usuario, Topico topico){
        this.mensaje = crearRespuestaDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.esLaSolucion = false;
        this.fueBorrado = false;
        this.usuario = usuario;
        this.topico = topico;
    }

    public void actualizarRespuesta(ActualizarRespuestaDTO actualizarRespuestaDTO){

        if(actualizarRespuestaDTO.mensaje() != null){
            this.mensaje = actualizarRespuestaDTO.mensaje();
        }

        if(actualizarRespuestaDTO.esLaSolucion() != null){
            this.esLaSolucion = actualizarRespuestaDTO.esLaSolucion();
        }

        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void eliminarRespuesta(){
        this.fueBorrado = true;
    }
}
