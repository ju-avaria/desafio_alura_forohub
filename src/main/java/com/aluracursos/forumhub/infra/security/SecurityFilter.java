package com.aluracursos.forumhub.infra.security;

import com.aluracursos.forumhub.domain.usuario.Usuario;
import com.aluracursos.forumhub.domain.usuario.UsuarioRepository;
import com.aluracursos.forumhub.infra.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if(authHeader != null){
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);

            if(nombreUsuario != null){
                var usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario ,token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
