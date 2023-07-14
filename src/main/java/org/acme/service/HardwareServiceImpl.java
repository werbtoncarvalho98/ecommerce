package org.acme.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.dto.HardwareDTO;
import org.acme.dto.HardwareResponseDTO;
import org.acme.model.Fabricante;
import org.acme.model.Hardware;
import org.acme.model.Integridade;
import org.acme.model.Nivel;
import org.acme.repository.HardwareRepository;

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
        entity.setNome(hardwareDTO.nome());
        entity.setDescricao(hardwareDTO.descricao());
        entity.setPreco(hardwareDTO.preco());
        entity.setEstoque(hardwareDTO.estoque());
        entity.setModelo(hardwareDTO.modelo());
        entity.setLancamento(hardwareDTO.lancamento());
        entity.setNivel(Nivel.valueOf(hardwareDTO.idNivel()));
        entity.setIntegridade(Integridade.valueOf(hardwareDTO.idIntegridade()));
        entity.setFabricante(new Fabricante());
        entity.getFabricante().setId(hardwareDTO.fabricante());

        hardwareRepository.persist(entity);
        
        return new HardwareResponseDTO(entity);
    }

    @Override
    @Transactional
    public HardwareResponseDTO update(Long id, HardwareDTO hardwareDTO) throws ConstraintViolationException {
        Hardware entityUpdate = hardwareRepository.findById(id);
        if (entityUpdate == null)
            throw new NotFoundException("Hardware não encontrado.");
        validar(hardwareDTO);
        
        entityUpdate.setNome(hardwareDTO.nome());
        entityUpdate.setDescricao(hardwareDTO.descricao());
        entityUpdate.setPreco(hardwareDTO.preco());
        entityUpdate.setEstoque(hardwareDTO.estoque());
        entityUpdate.setModelo(hardwareDTO.modelo());
        entityUpdate.setLancamento(hardwareDTO.lancamento());
        entityUpdate.setNivel(Nivel.valueOf(hardwareDTO.idNivel()));
        entityUpdate.setIntegridade(Integridade.valueOf(hardwareDTO.idIntegridade()));
        entityUpdate.setFabricante(new Fabricante());
        entityUpdate.getFabricante().setId(hardwareDTO.fabricante());
        hardwareRepository.persist(entityUpdate);
        
        return new HardwareResponseDTO(entityUpdate);
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

    @Override
    public HardwareResponseDTO update(Long id, String imagem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}