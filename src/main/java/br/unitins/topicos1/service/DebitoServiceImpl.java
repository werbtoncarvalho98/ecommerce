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
import br.unitins.topicos1.dto.DebitoDTO;
import br.unitins.topicos1.dto.DebitoResponseDTO;
import br.unitins.topicos1.model.Debito;
import br.unitins.topicos1.repository.DebitoRepository;

@ApplicationScoped
public class DebitoServiceImpl implements DebitoService {

    @Inject
    DebitoRepository debitoRepository;

    @Inject
    Validator validator;

    @Override
    public List<DebitoResponseDTO> getAll() {
        List<Debito> list = debitoRepository.listAll();
        return list.stream().map(DebitoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public DebitoResponseDTO findById(Long id) {
        Debito debito = debitoRepository.findById(id);
        if (debito == null)
            return null;
        return new DebitoResponseDTO(debito);
    }

    @Override
    @Transactional
    public DebitoResponseDTO create(DebitoDTO debitoDTO) throws ConstraintViolationException {
        validar(debitoDTO);

        Debito entity = new Debito();

        entity.setTitular(debitoDTO.titular());

        debitoRepository.persist(entity);

        return new DebitoResponseDTO(entity);
    }

    @Override
    @Transactional
    public DebitoResponseDTO update(Long id, DebitoDTO debitoDTO) throws ConstraintViolationException {
        Debito debitoUpdate = debitoRepository.findById(id);
        if (debitoUpdate == null)
            throw new NotFoundException("Debito não encontrado.");

        validar(debitoDTO);

        debitoUpdate.setTitular(debitoDTO.titular());

        debitoRepository.persist(debitoUpdate);

        return new DebitoResponseDTO(debitoUpdate);
    }

    private void validar(DebitoDTO debitoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<DebitoDTO>> violations = validator.validate(debitoDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        debitoRepository.deleteById(id);
    }

    @Override
    public List<DebitoResponseDTO> findByTitular(String titular) {
        List<Debito> list = debitoRepository.findByTitular(titular);
        return list.stream().map(DebitoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return debitoRepository.count();
    }
}