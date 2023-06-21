package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.UsuarioDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Sexo;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;
import br.unitins.topicos1.repository.EnderecoRepository;
import br.unitins.topicos1.repository.MunicipioRepository;
import br.unitins.topicos1.repository.PedidoRepository;
import br.unitins.topicos1.repository.TelefoneRepository;
import br.unitins.topicos1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    Validator validator;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null)
            throw new NotFoundException("Não encontrado");
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        var entity = new Usuario();
        entity.setLogin(usuarioDTO.login());
        HashServiceImpl hash = new HashServiceImpl();
        entity.setSenha(hash.getHashSenha(usuarioDTO.senha()));
        entity.setNome(usuarioDTO.nome());
        entity.setEmail(usuarioDTO.email());
        entity.setCpf(usuarioDTO.cpf());

        Integer idSexo = usuarioDTO.idSexo();
        Sexo sexo = idSexo != null ? Sexo.valueOf(idSexo) : null;
        entity.setSexo(sexo);

        Telefone telefone = telefoneRepository.findById(usuarioDTO.idTelefone());
        entity.setTelefone(telefone);

        Endereco endereco = enderecoRepository.findById(usuarioDTO.idEndereco());
        entity.setEndereco(endereco);

        usuarioRepository.persist(entity);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        var entity = new Usuario();
        entity.setLogin(usuarioDTO.login());
        HashServiceImpl hash = new HashServiceImpl();
        entity.setSenha(hash.getHashSenha(usuarioDTO.senha()));
        entity.setNome(usuarioDTO.nome());
        entity.setEmail(usuarioDTO.email());
        entity.setCpf(usuarioDTO.cpf());

        Integer idSexo = usuarioDTO.idSexo();
        Sexo sexo = idSexo != null ? Sexo.valueOf(idSexo) : null;
        entity.setSexo(sexo);

        if (!usuarioDTO.idTelefone().equals(entity.getTelefone().getId()))
            entity.getTelefone().setId(usuarioDTO.idTelefone());

        if (!usuarioDTO.idEndereco().equals(entity.getEndereco().getId()))
            entity.getEndereco().setId(usuarioDTO.idEndereco());

        return new UsuarioResponseDTO(entity);
    }

    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Usuario usuario = usuarioRepository.findById(id);

        if (usuarioRepository.isPersistent(usuario)) {
            usuarioRepository.delete(usuario);
        } else
            throw new NotFoundException("Nenhum usuario encontrado");
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) throws NullPointerException {
        List<Usuario> list = usuarioRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum usuario encontrado");

        return list.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
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
            throw new NotFoundException("Usuario não encontrado");
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, String nomeImagem) {
        Usuario usuario = usuarioRepository.findById(id);

        usuario.setImagem(nomeImagem);

        return new UsuarioResponseDTO(usuario);
    }
}