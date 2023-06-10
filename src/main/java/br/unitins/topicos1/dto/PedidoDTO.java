package br.unitins.topicos1.dto;

import java.time.LocalDate;

public record PedidoDTO(
                LocalDate data,
                String status) {
}