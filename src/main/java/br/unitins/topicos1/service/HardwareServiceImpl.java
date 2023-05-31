package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.HardwareDTO;
import br.unitins.topicos1.dto.HardwareResponseDTO;
import br.unitins.topicos1.model.Fabricante;
import br.unitins.topicos1.model.Hardware;
import br.unitins.topicos1.model.Integridade;
import br.unitins.topicos1.model.Nivel;
import br.unitins.topicos1.repository.HardwareRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class HardwareServiceImpl implements HardwareService {

    @Inject
    HardwareRepository hardwareRepository;

    @Inject
    Validator validator;

    @Override
    public List<HardwareResponseDTO> getAll() {
        List<Hardware> list = hardwareRepository.listAll();
        return list.stream().map(HardwareResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public HardwareResponseDTO findById(Long id) {
        Hardware hardware = hardwareRepository.findById(id);
        if (hardware == null)
            return null;
        return new HardwareResponseDTO(hardware);
    }

    @Override
    @Transactional
    public HardwareResponseDTO create(HardwareDTO hardwareDTO) throws ConstraintViolationException {
        validar(hardwareDTO);

        Hardware entity = new Hardware();
        entity.setModelo(hardwareDTO.modelo());
        entity.setLancamento(hardwareDTO.lancamento());
        entity.setNivel(Nivel.valueOf(hardwareDTO.nivel()));
        entity.setIntegridade(Integridade.valueOf(hardwareDTO.integridade()));
        entity.setFabricante(new Fabricante());
        entity.getFabricante().setId(hardwareDTO.idFabricante());

        hardwareRepository.persist(entity);
        
        return new HardwareResponseDTO(entity);
    }

    @Override
    @Transactional
    public HardwareResponseDTO update(Long id, HardwareDTO hardwareDTO) throws ConstraintViolationException {
        Hardware hardwareUpdate = hardwareRepository.findById(id);
        if (hardwareUpdate == null)
            throw new NotFoundException("Hardware não encontrado.");
        validar(hardwareDTO);
        hardwareUpdate.setModelo(hardwareDTO.modelo());
        hardwareUpdate.setLancamento(hardwareDTO.lancamento());
        hardwareUpdate.setNivel(Nivel.valueOf(hardwareDTO.nivel()));
        hardwareUpdate.setIntegridade(Integridade.valueOf(hardwareDTO.integridade()));
        hardwareUpdate.setFabricante(new Fabricante());
        hardwareUpdate.getFabricante().setId(hardwareDTO.idFabricante());
        hardwareRepository.persist(hardwareUpdate);
        
        return new HardwareResponseDTO(hardwareUpdate);
    }

    private void validar(HardwareDTO hardwareDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<HardwareDTO>> violations = validator.validate(hardwareDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        hardwareRepository.deleteById(id);
    }

    @Override
    public List<HardwareResponseDTO> findByMarca(String marca) {
        List<Hardware> list = hardwareRepository.findByMarca(marca);
        return list.stream().map(HardwareResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return hardwareRepository.count();
    }
}