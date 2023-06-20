package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Pagamento;

public record PagamentoResponseDTO(
        Long id,
        Double valor) {

    public PagamentoResponseDTO(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getValor());
    }
}