package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;
import br.unitins.topicos1.model.Cliente;
import br.unitins.topicos1.repository.ClienteRepository;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    Validator validator;

    @Override
    public List<ClienteResponseDTO> getAll() {
        List<Cliente> list = clienteRepository.listAll();
        return list.stream().map(ClienteResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            return null;
        return new ClienteResponseDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO clienteDTO) throws ConstraintViolationException {
        validar(clienteDTO);

        Cliente entity = new Cliente();
        entity.setNome(clienteDTO.nome());
        entity.setEmail(clienteDTO.email());
        entity.setCpf(clienteDTO.cpf());

        clienteRepository.persist(entity);

        return new ClienteResponseDTO(entity);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteDTO clienteDTO) throws ConstraintViolationException {
        Cliente clienteUpdate = clienteRepository.findById(id);
        if (clienteUpdate == null)
            throw new NotFoundException("Cliente não encontrado.");
        validar(clienteDTO);
        clienteUpdate.setNome(clienteDTO.nome());
        clienteUpdate.setEmail(clienteDTO.email());
        clienteUpdate.setCpf(clienteDTO.cpf());

        clienteRepository.persist(clienteUpdate);

        return new ClienteResponseDTO(clienteUpdate);
    }

    private void validar(ClienteDTO clienteDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<ClienteResponseDTO> findByCpf(String cpf) {
        List<Cliente> list = clienteRepository.findByCpf(cpf);
        return list.stream().map(ClienteResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return clienteRepository.count();
    }
}