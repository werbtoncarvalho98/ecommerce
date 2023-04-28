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
import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.FabricanteResponseDTO;
import br.unitins.topicos1.model.Fabricante;
import br.unitins.topicos1.repository.FabricanteRepository;

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
            throw new NotFoundException("Equipamento não encontrado.");
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
        Fabricante fabricanteUpdate = fabricanteRepository.findById(id);
        if (fabricanteUpdate == null)
            throw new NotFoundException("Fabricante não encontrado.");
        validar(fabricanteDTO);
        fabricanteUpdate.setNome(fabricanteDTO.nome());
        fabricanteUpdate.setWebsite(fabricanteDTO.website());

        return new FabricanteResponseDTO(fabricanteUpdate);
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