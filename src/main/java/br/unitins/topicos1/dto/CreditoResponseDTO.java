package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Credito;

public record CreditoResponseDTO(
        Long id,
        String titular,
        String numero,
        String validade,
        String cvv) {
    public CreditoResponseDTO(Credito credito) {
        this(credito.getId(), credito.getTitular(), credito.getNumero(), credito.getValidade(), credito.getCvv());
    }
}