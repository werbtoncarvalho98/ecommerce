package br.unitins.topicos1.dto;

import java.time.LocalDate;

import br.unitins.topicos1.model.Pagamento;
import br.unitins.topicos1.model.Pedido;

public record PedidoResponseDTO(
        Long id,
        LocalDate data,
        String status,
        Pagamento pagamento) {

    public PedidoResponseDTO(Pedido compra) {
        this(compra.getId(), compra.getData(), compra.getStatus(), compra.getPagamento());
    }
}