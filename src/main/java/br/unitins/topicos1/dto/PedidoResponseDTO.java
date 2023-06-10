package br.unitins.topicos1.dto;

import java.time.LocalDate;

import br.unitins.topicos1.model.Pedido;

public record PedidoResponseDTO(
        Long id,
        LocalDate data,
        String status) {
    public PedidoResponseDTO(Pedido compra) {
        this(compra.getId(), compra.getData(), compra.getStatus());
    }
}
