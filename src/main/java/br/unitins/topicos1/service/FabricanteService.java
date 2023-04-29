package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.FabricanteResponseDTO;

public interface FabricanteService {
    
    List<FabricanteResponseDTO> getAll();

    FabricanteResponseDTO findById(Long id);

    FabricanteResponseDTO create(FabricanteDTO fabricanteDTO);

    FabricanteResponseDTO update(Long id, FabricanteDTO fabricanteDTO);

    void delete(Long id);

    List<FabricanteResponseDTO> findByNome(String nome);

    Long count();
}