package br.unitins.topicos1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import br.unitins.topicos1.dto.UsuarioDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Produto;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;
import br.unitins.topicos1.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    Validator validator;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> list = usuarioRepository.listAll();
        return list.stream().map(u -> UsuarioResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario pessoafisica = usuarioRepository.findById(id);
        if (pessoafisica == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(pessoafisica);
    }

    /*
     * @Override
     * 
     * @Transactional
     * public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws
     * ConstraintViolationException {
     * validar(usuarioDTO);
     * 
     * Usuario entity = new Usuario();
     * entity.setLogin(usuarioDTO.login());
     * entity.setSenha(usuarioDTO.senha());
     * 
     * entity.setEnderecos(new ArrayList<Endereco>());
     * for (Long idEndereco : usuarioDTO.idEnderecos()) {
     * Endereco endereco = new Endereco();
     * endereco.setId(idEndereco);
     * entity.getEnderecos().add(endereco);
     * }
     * 
     * entity.setTelefones(new ArrayList<Telefone>());
     * for (Long idTelefones : usuarioDTO.idTelefones()) {
     * Telefone telefone = new Telefone();
     * telefone.setId(idTelefones);
     * entity.getTelefones().add(telefone);
     * }
     * 
     * entity.setListaDesejo(new ArrayList<Produto>());
     * for (Long idListaDesejo : usuarioDTO.idListaDesejo()) {
     * Produto produto = new Produto();
     * produto.setId(idListaDesejo);
     * entity.getListaDesejo().add(produto);
     * }
     * 
     * usuarioRepository.persist(entity);
     * 
     * return new UsuarioResponseDTO(entity);
     * }
     * 
     * @Override
     * 
     * @Transactional
     * public UsuarioResponseDTO update(Long id, UsuarioDTO UsuarioDTO) throws
     * ConstraintViolationException {
     * Usuario UsuarioUpdate = usuarioRepository.findById(id);
     * if (UsuarioUpdate == null)
     * throw new NotFoundException("Usuario não encontrado.");
     * validar(UsuarioDTO);
     * UsuarioUpdate.setNome(UsuarioDTO.nome());
     * UsuarioUpdate.setEmail(UsuarioDTO.email());
     * UsuarioUpdate.setCpf(UsuarioDTO.cpf());
     * 
     * UsuarioUpdate.setEnderecos(new ArrayList<Endereco>());
     * for (Long idEndereco : UsuarioDTO.idEnderecos()) {
     * Endereco endereco = new Endereco();
     * endereco.setId(idEndereco);
     * UsuarioUpdate.getEnderecos().add(endereco);
     * }
     * 
     * UsuarioUpdate.setTelefones(new ArrayList<Telefone>());
     * for (Long idTelefones : UsuarioDTO.idTelefones()) {
     * Telefone telefone = new Telefone();
     * telefone.setId(idTelefones);
     * UsuarioUpdate.getTelefones().add(telefone);
     * }
     * 
     * UsuarioUpdate.setListaDesejo(new ArrayList<Produto>());
     * for (Long idListaDesejo : UsuarioDTO.idListaDesejo()) {
     * Produto produto = new Produto();
     * produto.setId(idListaDesejo);
     * UsuarioUpdate.getListaDesejo().add(produto);
     * }
     * 
     * usuarioRepository.persist(UsuarioUpdate);
     * 
     * return new UsuarioResponseDTO(UsuarioUpdate);
     * }
     */

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
        List<Usuario> list = usuarioRepository.findByNome(nome);
        return list.stream().map(u -> UsuarioResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return usuarioRepository.count();
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, String imagem) {

        Usuario entity = usuarioRepository.findById(id);
        entity.setImagem(imagem);

        return UsuarioResponseDTO.valueOf(entity);
    }
}