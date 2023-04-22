package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Estado;
import br.unitins.topicos1.model.Municipio;

public record MunicipioResponseDTO(
        Long id,
        String nome,
        Estado estado) {

    public MunicipioResponseDTO(Municipio municipio) {
        this(municipio.getId(), municipio.getNome(), municipio.getEstado());
    }
}