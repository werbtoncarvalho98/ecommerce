package br.unitins.topicos1.dto;

import java.util.List;
import java.util.Set;

import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Perfil;
import br.unitins.topicos1.model.PessoaFisica;
import br.unitins.topicos1.model.Produto;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String senha,
        String imagem,
        Set<Perfil> perfis,
        List<Endereco> enderecos,
        List<Telefone> telefones,
        List<Produto> listaDesejo,
        PessoaFisica pessoaFisica) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario.getPessoaFisica() == null)
            return new UsuarioResponseDTO(usuario.getId(),
                    usuario.getLogin(),
                    usuario.getSenha(),
                    usuario.getImagem(),
                    usuario.getPerfis(),
                    usuario.getEnderecos(), usuario.getTelefones(),
                    usuario.getListaDesejo(),
                    usuario.getPessoaFisica());

        return new UsuarioResponseDTO(usuario.getId(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getImagem(),
                usuario.getPerfis(),
                usuario.getEnderecos(),
                usuario.getTelefones(),
                usuario.getListaDesejo(),
                usuario.getPessoaFisica());
    }
}