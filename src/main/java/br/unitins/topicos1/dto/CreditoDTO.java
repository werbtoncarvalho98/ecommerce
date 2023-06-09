package br.unitins.topicos1.dto;

public record CreditoDTO(
                String titular,
                String numero,
                String validade,
                String cvv) {
}