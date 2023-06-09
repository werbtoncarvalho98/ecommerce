package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.UsuarioDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Usuario;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    // UsuarioResponseDTO create(UsuarioDTO usuarioDTO);

    // UsuarioResponseDTO update(Long id, String nomeImagem);

    UsuarioResponseDTO update(Long id, String nomeImagem);

    Usuario findByLoginAndSenha(String login, String senha);

    UsuarioResponseDTO findByLogin(String login);

    void delete(Long id);

    List<UsuarioResponseDTO> findByNome(String nome);

    Long count();
}