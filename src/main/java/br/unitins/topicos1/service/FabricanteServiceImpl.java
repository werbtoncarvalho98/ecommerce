package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.FabricanteResponseDTO;
import br.unitins.topicos1.model.Fabricante;
import br.unitins.topicos1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    Validator validator;

    @Override
    public List<FabricanteResponseDTO> getAll() {
        List<Fabricante> list = fabricanteRepository.listAll();
        return list.stream().map(FabricanteResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public FabricanteResponseDTO findById(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            return null;
        return new FabricanteResponseDTO(fabricante);
    }

    @Override
    @Transactional
    public FabricanteResponseDTO create(FabricanteDTO fabricanteDTO) throws ConstraintViolationException {
        validar(fabricanteDTO);

        Fabricante entity = new Fabricante();
        entity.setNome(fabricanteDTO.nome());
        entity.setWebsite(fabricanteDTO.website());

        fabricanteRepository.persist(entity);

        return new FabricanteResponseDTO(entity);
    }

    @Override
    @Transactional
    public FabricanteResponseDTO update(Long id, FabricanteDTO fabricanteDTO) throws ConstraintViolationException {
        Fabricante entityUpdate = fabricanteRepository.findById(id);
        if (entityUpdate == null)
            throw new NotFoundException("Fabricante não encontrado.");
        validar(fabricanteDTO);
        entityUpdate.setNome(fabricanteDTO.nome());
        entityUpdate.setWebsite(fabricanteDTO.website());

        return new FabricanteResponseDTO(entityUpdate);
    }

    private void validar(FabricanteDTO fabricanteDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<FabricanteDTO>> violations = validator.validate(fabricanteDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fabricanteRepository.deleteById(id);
    }

    @Override
    public List<FabricanteResponseDTO> findByNome(String nome) {
        List<Fabricante> list = fabricanteRepository.findByNome(nome);
        return list.stream().map(FabricanteResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return fabricanteRepository.count();
    }
}