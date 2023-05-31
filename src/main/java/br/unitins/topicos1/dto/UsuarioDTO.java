package br.unitins.topicos1.dto;

import java.util.List;

public record UsuarioDTO(Long id,
        String login,
        String senha,
        List<Long> idEnderecos,
        List<Long> idTelefones,
        List<Long> idListaDesejo,
        Long idCliente) {
}