package org.acme.dto;

public record UsuarioDTO(
                String login,
                String senha,
                String imagem,
                String nome,
                String email,
                String cpf,            
                Integer idSexo,
                Long idTelefone,
                Long idEndereco
                /* Long idPerfis */) {
}