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
import br.unitins.topicos1.dto.CreditoDTO;
import br.unitins.topicos1.dto.CreditoResponseDTO;
import br.unitins.topicos1.model.Credito;
import br.unitins.topicos1.repository.CreditoRepository;

@ApplicationScoped
public class CreditoServiceImpl implements CreditoService {

    @Inject
    CreditoRepository creditoRepository;

    @Inject
    Validator validator;

    @Override
    public List<CreditoResponseDTO> getAll() {
        List<Credito> list = creditoRepository.listAll();
        return list.stream().map(CreditoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public CreditoResponseDTO findById(Long id) {
        Credito credito = creditoRepository.findById(id);
        if (credito == null)
            return null;
        return new CreditoResponseDTO(credito);
    }

    @Override
    @Transactional
    public CreditoResponseDTO create(CreditoDTO creditoDTO) throws ConstraintViolationException {
        validar(creditoDTO);

        Credito entity = new Credito();

        entity.setTitular(creditoDTO.titular());

        creditoRepository.persist(entity);

        return new CreditoResponseDTO(entity);
    }

    @Override
    @Transactional
    public CreditoResponseDTO update(Long id, CreditoDTO creditoDTO) throws ConstraintViolationException {
        Credito creditoUpdate = creditoRepository.findById(id);
        if (creditoUpdate == null)
            throw new NotFoundException("Credito não encontrado.");

        validar(creditoDTO);

        creditoUpdate.setTitular(creditoDTO.titular());

        creditoRepository.persist(creditoUpdate);

        return new CreditoResponseDTO(creditoUpdate);
    }

    private void validar(CreditoDTO creditoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<CreditoDTO>> violations = validator.validate(creditoDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        creditoRepository.deleteById(id);
    }

    @Override
    public List<CreditoResponseDTO> findByTitular(String titular) {
        List<Credito> list = creditoRepository.findByTitular(titular);
        return list.stream().map(CreditoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return creditoRepository.count();
    }
}