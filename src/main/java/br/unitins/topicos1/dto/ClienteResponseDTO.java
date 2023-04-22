package br.unitins.topicos1.dto;

import java.util.List;
import br.unitins.topicos1.model.Cliente;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Produto;
import br.unitins.topicos1.model.Telefone;

public record ClienteResponseDTO(

        Long id,
        String nome,
        String email,
        String cpf,
        List<Endereco> enderecos,
        List<Telefone> telefones,
        List<Produto> listaDesejo) {

    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getEnderecos(),
                cliente.getTelefones(), cliente.getListaDesejo());
    }
}
