package br.unitins.topicos1.dto;

public record EnderecoDTO(
                boolean principal,
                String logradouro,
                String numero,
                String complemento,
                String bairro,
                String cep,
                Long idMunicipio) {
}