package com.aluracursos.forumhub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorizaHttpRequests -> authorizaHttpRequests
//                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
//                        .requestMatchers("/swagger-ui.html", "/v3/api-docs", "/swagger-ui/**").permitAll()
//                        .anyRequest().authenticated()
//                ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                            .requestMatchers(HttpMethod.GET, "/usuarios/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()
                            .requestMatchers(HttpMethod.GET, "/usuarios/all").permitAll()
                            .requestMatchers(HttpMethod.GET, "/usuarios/by-username/{nombreUsuario}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/usuarios/{nombreUsuario}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/usuarios/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.POST, "/cursos").permitAll()
                            .requestMatchers(HttpMethod.GET, "/cursos").permitAll()
                            .requestMatchers(HttpMethod.GET, "/cursos/all").permitAll()
                            .requestMatchers(HttpMethod.GET, "/cursos/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/cursos/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/cursos/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.POST, "/topicos").permitAll()
                            .requestMatchers(HttpMethod.GET, "/topicos").permitAll()
                            .requestMatchers(HttpMethod.GET, "/topicos/all").permitAll()
                            .requestMatchers(HttpMethod.GET, "/topicos/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/topicos/id/{id}/solucion").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/topicos/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/topicos/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.POST, "/respuestas").permitAll()
                            .requestMatchers(HttpMethod.GET, "/respuestas/topico/{topicoId}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/respuestas/usuario/{usuarioId}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/respuestas/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/respuestas/id/{id}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/respuestas/id/{id}").permitAll()
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui.html/**", "/swagger-ui/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
