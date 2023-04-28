package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Municipio;

public record EnderecoResponseDTO(
        Long id,
        boolean principal,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cep,
        Municipio municipio) {

    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getId(), endereco.isPrincipal(), endereco.getLogradouro(), endereco.getNumero(),
                endereco.getComplemento(), endereco.getBairro(), endereco.getCep(),
                endereco.getMunicipio());
    }
}