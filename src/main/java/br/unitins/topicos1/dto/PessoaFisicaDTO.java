package br.unitins.topicos1.dto;

public record PessoaFisicaDTO(
        Long id,
        String cpf,
        String email,
        Integer sexo) {
}