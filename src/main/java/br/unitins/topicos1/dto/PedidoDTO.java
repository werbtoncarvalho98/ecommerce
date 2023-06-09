package br.unitins.topicos1.dto;

import java.time.LocalDate;

import br.unitins.topicos1.model.Pagamento;

public record PedidoDTO(
        LocalDate data,
        String status,
        Pagamento pagamento) {
}