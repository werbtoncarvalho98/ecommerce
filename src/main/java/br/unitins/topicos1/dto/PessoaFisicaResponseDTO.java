package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.PessoaFisica;
import br.unitins.topicos1.model.Sexo;

public record PessoaFisicaResponseDTO(
        Long id,
        String cpf,
        String email,
        Sexo sexo) {
    public PessoaFisicaResponseDTO(PessoaFisica pessoaFisica) {
        this(pessoaFisica.getId(), pessoaFisica.getCpf(), pessoaFisica.getEmail(), pessoaFisica.getSexo());
    }
}