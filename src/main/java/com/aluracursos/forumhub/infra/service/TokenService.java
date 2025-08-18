package com.aluracursos.forumhub.infra.service;

import com.aluracursos.forumhub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try{
            var algoritmo= Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("forumhub")
                    .withSubject(usuario.getNombreUsuario())
                    .withClaim("id" , usuario.getId())
                    .withExpiresAt(generarFechaVencimiento())
                    .sign(algoritmo);
        } catch (JWTCreationException e){
            throw new RuntimeException("Failed to generate token");
        }
    }

    public String getSubject(String token){
        if (token == null){
            throw new RuntimeException("Tokes is null");
        }

        DecodedJWT verificador = null;
        // verificar firma
        try{
            Algorithm algoritmo = Algorithm.HMAC256(apiSecret);
            verificador = JWT.require(algoritmo)
                    .withIssuer("forumhub")
                    .build()
                    .verify(token);
            verificador.getSubject();
        }catch (JWTVerificationException e){
            System.out.println(e.toString());
        }
        if(verificador.getSubject() != null){
            throw new RuntimeException("Invalid verifier");
        }
        return verificador.getSubject();
    }

    private Instant generarFechaVencimiento() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
