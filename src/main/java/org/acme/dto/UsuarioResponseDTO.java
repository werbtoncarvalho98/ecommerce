package org.acme.dto;

import org.acme.model.Endereco;
import org.acme.model.Sexo;
import org.acme.model.Telefone;
import org.acme.model.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String senha,
        String imagem,
        String nome,
        String cpf,
        @JsonInclude(JsonInclude.Include.NON_NULL) Sexo sexo,
        Telefone telefone,
        Endereco endereco
        /* Set<Perfil> perfis */) {
    public UsuarioResponseDTO(Usuario cliente) {
        this(cliente.getId(),
                cliente.getLogin(),
                cliente.getSenha(),
                cliente.getImagem(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getSexo(),
                cliente.getTelefone(),
                cliente.getEndereco()
                /* cliente.getPerfis() */);
    }
}