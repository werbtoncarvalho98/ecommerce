package br.unitins.topicos1.dto;

import java.util.List;
import java.util.Set;

import br.unitins.topicos1.model.Perfil;
import br.unitins.topicos1.model.PessoaFisica;

public record UsuarioDTO(
                String login,
                String senha,
                String imagem,
                Set<Perfil> perfis,
                List<Long> idEnderecos,
                List<Long> idTelefones,
                List<Long> idListaDesejo,
                PessoaFisica pessoaFisica) {
}