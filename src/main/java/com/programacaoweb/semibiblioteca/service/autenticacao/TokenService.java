package com.programacaoweb.semibiblioteca.service.autenticacao;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.programacaoweb.semibiblioteca.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private final long expiration = System.currentTimeMillis() + 3600 * 2000;
    private final String expiresIn = Instant.ofEpochMilli(expiration)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("duckqueries")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token ", exception);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("duckqueries")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            // error 403
            return "";
        }
    }

    public long getExpiration() {
        return expiration;
    }

    public String getExpiresIn() {
        return expiresIn;
    }
}
