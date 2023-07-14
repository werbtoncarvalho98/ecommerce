package org.acme.dto;

import org.acme.model.Telefone;

public record TelefoneResponseDTO(

        Long id,
        String ddd,
        String numero) {

    public TelefoneResponseDTO(Telefone telefone) {
        this(telefone.getId(), telefone.getDdd(), telefone.getNumero());
    }
}