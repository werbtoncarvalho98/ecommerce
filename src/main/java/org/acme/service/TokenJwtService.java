package org.acme.service;

import org.acme.model.Usuario;

public interface TokenJwtService {
    public String generateJwt(Usuario usuario);
}