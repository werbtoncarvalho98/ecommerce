package br.unitins.topicos1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Sexo;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String cpf,

        @JsonInclude(JsonInclude.Include.NON_NULL) Sexo sexo,
        Telefone telefone,
        Endereco endereco,

        String login,

        String nomeImagem) {
    public UsuarioResponseDTO(Usuario cliente) {
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getSexo(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getLogin(),
                cliente.getImagem());
    }
}