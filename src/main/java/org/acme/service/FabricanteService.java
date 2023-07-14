package org.acme.service;

import java.util.List;

import org.acme.dto.FabricanteDTO;
import org.acme.dto.FabricanteResponseDTO;

public interface FabricanteService {
    
    List<FabricanteResponseDTO> getAll();

    FabricanteResponseDTO findById(Long id);

    FabricanteResponseDTO create(FabricanteDTO fabricanteDTO);

    FabricanteResponseDTO update(Long id, FabricanteDTO fabricanteDTO);

    void delete(Long id);

    List<FabricanteResponseDTO> findByNome(String nome);

    Long count();
}