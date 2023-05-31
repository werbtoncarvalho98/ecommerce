package br.unitins.topicos1.dto;

import java.util.List;

import br.unitins.topicos1.model.Cliente;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Produto;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String senha,
        List<Endereco> enderecos,
        List<Telefone> telefones,
        List<Produto> listaDesejo,
        Cliente cliente) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario.getCliente() == null)
            return new UsuarioResponseDTO(usuario.getId(),
                    usuario.getLogin(),
                    usuario.getSenha(),
                    usuario.getEnderecos(), usuario.getTelefones(),
                    usuario.getListaDesejo(),
                    usuario.getCliente());

        return new UsuarioResponseDTO(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getEnderecos(),
                usuario.getTelefones(), usuario.getListaDesejo(), usuario.getCliente());
    }
}