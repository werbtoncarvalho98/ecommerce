package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.HardwareDTO;
import br.unitins.topicos1.dto.HardwareResponseDTO;

public interface HardwareService {
    
    List<HardwareResponseDTO> getAll();

    HardwareResponseDTO findById(Long id);

    HardwareResponseDTO create(HardwareDTO hardwareDTO);

    HardwareResponseDTO update(Long id, HardwareDTO hardwareDTO);

    void delete(Long id);

    List<HardwareResponseDTO> findByMarca(String marca);

    Long count();
}