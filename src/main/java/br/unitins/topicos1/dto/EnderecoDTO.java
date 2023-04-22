package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Estado;
import br.unitins.topicos1.model.Municipio;

public record EnderecoDTO(
        boolean principal,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cep,
        Long idEstado,
        Long idMunicipio) {
}