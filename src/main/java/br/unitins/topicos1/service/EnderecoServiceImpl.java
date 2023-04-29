package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;
import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.dto.EnderecoResponseDTO;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Municipio;
import br.unitins.topicos1.repository.EnderecoRepository;

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
        Endereco enderecoUpdate = enderecoRepository.findById(id);
        if (enderecoUpdate == null)
            throw new NotFoundException("Endereco não encontrado.");
        validar(enderecoDTO);
        enderecoUpdate.setPrincipal(enderecoDTO.principal());
        enderecoUpdate.setLogradouro(enderecoDTO.logradouro());
        enderecoUpdate.setNumero(enderecoDTO.numero());
        enderecoUpdate.setComplemento(enderecoDTO.complemento());
        enderecoUpdate.setBairro(enderecoDTO.bairro());
        enderecoUpdate.setCep(enderecoDTO.cep());
        enderecoUpdate.setMunicipio(new Municipio());
        enderecoUpdate.getMunicipio().setId(enderecoDTO.idMunicipio());
        enderecoRepository.persist(enderecoUpdate);
        
        return new EnderecoResponseDTO(enderecoUpdate);
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