package br.unitins.topicos1.dto;

public record DebitoDTO(
                String titular,
                String numero,
                String validade,
                String cvv) {
}