package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Debito;

public record DebitoResponseDTO(
        Long id,
        String titular,
        String numero,
        String validade,
        String cvv) {
    public DebitoResponseDTO(Debito debito) {
        this(debito.getId(), debito.getTitular(), debito.getNumero(), debito.getValidade(), debito.getCvv());
    }
}