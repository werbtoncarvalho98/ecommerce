package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.BoletoDTO;
import br.unitins.topicos1.dto.BoletoResponseDTO;

public interface BoletoService {
    
    List<BoletoResponseDTO> getAll();

    BoletoResponseDTO findById(Long id);

    BoletoResponseDTO create(BoletoDTO boletoDTO);

    BoletoResponseDTO update(Long id, BoletoDTO boletoDTO);

    void delete(Long id);

    List<BoletoResponseDTO> findByCodigo(String codigo);

    Long count();
}