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
import br.unitins.topicos1.dto.PixDTO;
import br.unitins.topicos1.dto.PixResponseDTO;
import br.unitins.topicos1.model.Pix;
import br.unitins.topicos1.repository.PixRepository;

@ApplicationScoped
public class PixServiceImpl implements PixService {

    @Inject
    PixRepository debitoRepository;

    @Inject
    Validator validator;

    @Override
    public List<PixResponseDTO> getAll() {
        List<Pix> list = debitoRepository.listAll();
        return list.stream().map(PixResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PixResponseDTO findById(Long id) {
        Pix debito = debitoRepository.findById(id);
        if (debito == null)
            return null;
        return new PixResponseDTO(debito);
    }

    @Override
    @Transactional
    public PixResponseDTO create(PixDTO debitoDTO) throws ConstraintViolationException {
        validar(debitoDTO);

        Pix entity = new Pix();

        entity.setChave(debitoDTO.chave());

        debitoRepository.persist(entity);

        return new PixResponseDTO(entity);
    }

    @Override
    @Transactional
    public PixResponseDTO update(Long id, PixDTO debitoDTO) throws ConstraintViolationException {
        Pix debitoUpdate = debitoRepository.findById(id);
        if (debitoUpdate == null)
            throw new NotFoundException("Pix não encontrado.");

        validar(debitoDTO);

        debitoUpdate.setChave(debitoDTO.chave());

        debitoRepository.persist(debitoUpdate);

        return new PixResponseDTO(debitoUpdate);
    }

    private void validar(PixDTO debitoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<PixDTO>> violations = validator.validate(debitoDTO);
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
    public List<PixResponseDTO> findByChave(String chave) {
        List<Pix> list = debitoRepository.findByChave(chave);
        return list.stream().map(PixResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return debitoRepository.count();
    }
}