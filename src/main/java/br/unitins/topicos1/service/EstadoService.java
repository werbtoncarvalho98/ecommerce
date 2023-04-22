package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.EstadoDTO;
import br.unitins.topicos1.dto.EstadoResponseDTO;

public interface EstadoService {
    
    List<EstadoResponseDTO> getAll();

    EstadoResponseDTO findById(Long id);

    EstadoResponseDTO create(EstadoDTO estadoDTO);

    EstadoResponseDTO update(Long id, EstadoDTO estadoDTO);

    void delete(Long id);

    List<EstadoResponseDTO> findBySigla(String sigla);

    Long count();
}