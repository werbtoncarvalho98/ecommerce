package br.unitins.topicos1.dto;

public record ProdutoDTO(
        Long id,
        String nome,
        String descricao,
        Float preco,
        Integer estoque) {
}
