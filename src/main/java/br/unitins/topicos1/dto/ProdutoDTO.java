package br.unitins.topicos1.dto;

public record ProdutoDTO(
                String nome,
                String descricao,
                Float preco,
                Integer estoque) {
}
