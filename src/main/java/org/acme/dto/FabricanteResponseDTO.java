package org.acme.dto;

import org.acme.model.Fabricante;

public record FabricanteResponseDTO(
        Long id,
        String nome,
        String website) {

    public FabricanteResponseDTO(Fabricante fabricante) {
        this(fabricante.getId(), fabricante.getNome(), fabricante.getWebsite());
    }
}
