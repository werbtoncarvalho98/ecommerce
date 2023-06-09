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
import br.unitins.topicos1.dto.BoletoDTO;
import br.unitins.topicos1.dto.BoletoResponseDTO;
import br.unitins.topicos1.model.Boleto;
import br.unitins.topicos1.repository.BoletoRepository;

@ApplicationScoped
public class BoletoServiceImpl implements BoletoService {

    @Inject
    BoletoRepository boletoRepository;

    @Inject
    Validator validator;

    @Override
    public List<BoletoResponseDTO> getAll() {
        List<Boleto> list = boletoRepository.listAll();
        return list.stream().map(BoletoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public BoletoResponseDTO findById(Long id) {
        Boleto boleto = boletoRepository.findById(id);
        if (boleto == null)
            return null;
        return new BoletoResponseDTO(boleto);
    }

    @Override
    @Transactional
    public BoletoResponseDTO create(BoletoDTO boletoDTO) throws ConstraintViolationException {
        validar(boletoDTO);

        Boleto entity = new Boleto();

        entity.setCodigo(boletoDTO.codigo());

        boletoRepository.persist(entity);

        return new BoletoResponseDTO(entity);
    }

    @Override
    @Transactional
    public BoletoResponseDTO update(Long id, BoletoDTO boletoDTO) throws ConstraintViolationException {
        Boleto boletoUpdate = boletoRepository.findById(id);
        if (boletoUpdate == null)
            throw new NotFoundException("Boleto não encontrado.");

        validar(boletoDTO);

        boletoUpdate.setCodigo(boletoDTO.codigo());

        boletoRepository.persist(boletoUpdate);

        return new BoletoResponseDTO(boletoUpdate);
    }

    private void validar(BoletoDTO boletoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<BoletoDTO>> violations = validator.validate(boletoDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boletoRepository.deleteById(id);
    }

    @Override
    public List<BoletoResponseDTO> findByCodigo(String cpf) {
        List<Boleto> list = boletoRepository.findByCodigo(cpf);
        return list.stream().map(BoletoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return boletoRepository.count();
    }
}