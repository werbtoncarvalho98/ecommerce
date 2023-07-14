package org.acme.service;

import java.util.List;

import org.acme.dto.HardwareDTO;
import org.acme.dto.HardwareResponseDTO;

public interface HardwareService {
    
    List<HardwareResponseDTO> getAll();

    HardwareResponseDTO findById(Long id);

    HardwareResponseDTO create(HardwareDTO hardwareDTO);

    HardwareResponseDTO update(Long id, HardwareDTO hardwareDTO);

    HardwareResponseDTO update(Long id, String imagem);

    List<HardwareResponseDTO> findByMarca(String marca);

    void delete(Long id);

    Long count();
}