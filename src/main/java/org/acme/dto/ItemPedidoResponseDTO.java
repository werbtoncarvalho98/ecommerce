package org.acme.dto;

import org.acme.model.ItemPedido;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        float preco) {
    public ItemPedidoResponseDTO(ItemPedido itemPedido) {
        this(itemPedido.getId(), itemPedido.getQuantidade(), itemPedido.getPreco());
    }
}
