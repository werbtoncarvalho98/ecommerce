package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Pagamento;
import br.unitins.topicos1.model.Pedido;

public record PagamentoResponseDTO(
        Long id,
        Double valor,
        Pedido pedido

) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(pagamento.getId(), pagamento.getValor(), pagamento.getPedido());
    }
}
