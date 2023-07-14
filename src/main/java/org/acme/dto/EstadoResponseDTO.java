package org.acme.dto;

import org.acme.model.Estado;

public record EstadoResponseDTO(
        Long id,
        String nome,
        String sigla) {

    public EstadoResponseDTO(Estado estado) {
        this(estado.getId(), estado.getNome(), estado.getSigla());
    }
}