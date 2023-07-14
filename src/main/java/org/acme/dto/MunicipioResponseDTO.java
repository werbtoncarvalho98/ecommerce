package org.acme.dto;

import org.acme.model.Estado;
import org.acme.model.Municipio;

public record MunicipioResponseDTO(
        Long id,
        String nome,
        Estado estado) {

    public MunicipioResponseDTO(Municipio municipio) {
        this(municipio.getId(), municipio.getNome(), municipio.getEstado());
    }
}