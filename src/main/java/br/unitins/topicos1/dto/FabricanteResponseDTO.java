package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Fabricante;

public record FabricanteResponseDTO(
        Long id,
        String nome,
        String website) {

    public FabricanteResponseDTO(Fabricante fabricante) {
        this(fabricante.getId(), fabricante.getNome(), fabricante.getWebsite());
    }
}
