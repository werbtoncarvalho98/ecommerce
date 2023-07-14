package org.acme.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.model.Usuario;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenJwtServiceImpl implements TokenJwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(8766);

    @Override
    public String generateJwt(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        Set<String> roles = usuario.getPerfis()
                .stream().map(p -> p.getLabel())
                .collect(Collectors.toSet());

        return Jwt.issuer("unitins-jwt")
                .subject(usuario.getLogin())
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();
    }
}