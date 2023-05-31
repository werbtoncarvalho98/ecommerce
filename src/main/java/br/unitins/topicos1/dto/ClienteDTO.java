package br.unitins.topicos1.dto;

import java.util.List;

public record ClienteDTO(
        String nome,
        String email,
        String cpf
        /* List<Long> idEnderecos,
        List<Long> idTelefones,
        List<Long> idListaDesejo */) {
}