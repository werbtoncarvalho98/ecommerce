package org.acme.dto;

import org.acme.model.Pagamento;

public record PagamentoResponseDTO(
        Long id,
        Double valor) {

    public PagamentoResponseDTO(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getValor());
    }
}