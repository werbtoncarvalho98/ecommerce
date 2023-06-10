package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.ItemPedido;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        float preco) {
    public ItemPedidoResponseDTO(ItemPedido itemPedido) {
        this(itemPedido.getId(), itemPedido.getQuantidade(), itemPedido.getPreco());
    }
}
