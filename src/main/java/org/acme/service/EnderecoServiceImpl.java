package org.acme.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.dto.EnderecoDTO;
import org.acme.dto.EnderecoResponseDTO;
import org.acme.model.Endereco;
import org.acme.model.Municipio;
import org.acme.repository.EnderecoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    Validator validator;

    @Override
    public List<EnderecoResponseDTO> getAll() {
        List<Endereco> list = enderecoRepository.listAll();
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null)
            return null;
        return new EnderecoResponseDTO(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        validar(enderecoDTO);

        Endereco entity = new Endereco();
        entity.setPrincipal(enderecoDTO.principal());
        entity.setLogradouro(enderecoDTO.logradouro());
        entity.setNumero(enderecoDTO.numero());
        entity.setComplemento(enderecoDTO.complemento());
        entity.setBairro(enderecoDTO.bairro());
        entity.setCep(enderecoDTO.cep());
        entity.setMunicipio(new Municipio());
        entity.getMunicipio().setId(enderecoDTO.idMunicipio());

        enderecoRepository.persist(entity);

        return new EnderecoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        Endereco entityUpdate = enderecoRepository.findById(id);
        if (entityUpdate == null)
            throw new NotFoundException("Endereco não encontrado.");
        validar(enderecoDTO);
        entityUpdate.setPrincipal(enderecoDTO.principal());
        entityUpdate.setLogradouro(enderecoDTO.logradouro());
        entityUpdate.setNumero(enderecoDTO.numero());
        entityUpdate.setComplemento(enderecoDTO.complemento());
        entityUpdate.setBairro(enderecoDTO.bairro());
        entityUpdate.setCep(enderecoDTO.cep());
        entityUpdate.setMunicipio(new Municipio());
        entityUpdate.getMunicipio().setId(enderecoDTO.idMunicipio());
        enderecoRepository.persist(entityUpdate);

        return new EnderecoResponseDTO(entityUpdate);
    }

    private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    public List<EnderecoResponseDTO> findByCep(String numero) {
        List<Endereco> list = enderecoRepository.findByCep(numero);
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return enderecoRepository.count();
    }
}