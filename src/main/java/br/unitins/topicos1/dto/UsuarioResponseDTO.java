package br.unitins.topicos1.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Pedido;
import br.unitins.topicos1.model.Perfil;
import br.unitins.topicos1.model.Sexo;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String senha,
//      String imagem,
        String nome,
        String cpf,
        @JsonInclude(JsonInclude.Include.NON_NULL) Sexo sexo,
        Telefone telefone,
        Endereco endereco,
        Set<Perfil> perfis) {
    public UsuarioResponseDTO(Usuario cliente) {
        this(cliente.getId(),
                cliente.getLogin(),
                cliente.getSenha(),
//              cliente.getImagem(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getSexo(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getPerfis());
    }
}