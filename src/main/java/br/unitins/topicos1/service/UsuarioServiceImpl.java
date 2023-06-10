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
    UsuarioRepository clienteRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    Validator validator;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        return clienteRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario cliente = clienteRepository.findById(id);
        if (cliente == null)
            throw new NotFoundException("Não encontrado");
        return new UsuarioResponseDTO(cliente);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioDTO clienteDTO) throws ConstraintViolationException {
        validar(clienteDTO);

        var entity = new Usuario();
        entity.setNome(clienteDTO.nome());
        entity.setCpf(clienteDTO.cpf());

        entity.setSexo(Sexo.valueOf(clienteDTO.idSexo()));

        entity.setTelefone(new Telefone());
        entity.getTelefone().setId(clienteDTO.telefone());

        entity.setEndereco(new Endereco());
        entity.getEndereco().setId(clienteDTO.endereco());

        clienteRepository.persist(entity);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO clienteDTO) throws ConstraintViolationException {
        validar(clienteDTO);

        var entity = clienteRepository.findById(id);
        entity.setNome(clienteDTO.nome());
        entity.setCpf(clienteDTO.cpf());

        entity.setSexo(Sexo.valueOf(clienteDTO.idSexo()));

        if (!clienteDTO.telefone().equals(entity.getTelefone().getId())) {
            entity.getTelefone().setId(clienteDTO.telefone());
        }
        if (!clienteDTO.endereco().equals(entity.getEndereco().getId())) {
            entity.getEndereco().setId(clienteDTO.endereco());
        }

        return new UsuarioResponseDTO(entity);
    }

    private void validar(UsuarioDTO clienteDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(clienteDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Usuario cliente = clienteRepository.findById(id);

        if (clienteRepository.isPersistent(cliente)) {
            clienteRepository.delete(cliente);
        } else
            throw new NotFoundException("Nenhum usuario encontrado");
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) throws NullPointerException {
        List<Usuario> list = clienteRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum usuario encontrado");

        return list.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return clienteRepository.count();
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return clienteRepository.findByLoginAndSenha(login, senha);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario cliente = clienteRepository.findByLogin(login);
        if (cliente == null)
            throw new NotFoundException("Usuario não encontrado");
        return new UsuarioResponseDTO(cliente);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, String nomeImagem) {
        Usuario cliente = clienteRepository.findById(id);

        cliente.setImagem(nomeImagem);

        return new UsuarioResponseDTO(cliente);
    }
}
