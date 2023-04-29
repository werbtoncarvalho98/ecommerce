package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Telefone;

public record TelefoneResponseDTO(

        Long id,
        String ddd,
        String numero) {

    public TelefoneResponseDTO(Telefone telefone) {
        this(telefone.getId(), telefone.getDdd(), telefone.getNumero());
    }
}