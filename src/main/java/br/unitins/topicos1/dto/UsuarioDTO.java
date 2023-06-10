package br.unitins.topicos1.dto;

public record UsuarioDTO(
                String nome,
                String cpf,
                Integer idSexo,
                Long telefone,
                Long endereco) {
}