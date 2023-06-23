package br.unitins.topicos1.dto;

import java.time.LocalDate;

public record HardwareDTO(
        String nome,
        String descricao,
        Float preco,
        Integer estoque,
        String modelo,
        LocalDate lancamento,
        Integer idNivel,
        Integer idIntegridade,
        Long fabricante,
        String imagem) {
}