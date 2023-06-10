package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Pedido;

public record PagamentoDTO(
                Double valor,
                Pedido pedido) {
}