package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Boleto;

public record BoletoResponseDTO(
        Long id,
        String codigo) {
    public BoletoResponseDTO(Boleto boleto) {
        this(boleto.getId(), boleto.getCodigo());
    }
}