package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.DebitoDTO;
import br.unitins.topicos1.dto.DebitoResponseDTO;

public interface DebitoService {

    List<DebitoResponseDTO> getAll();

    DebitoResponseDTO findById(Long id);

    DebitoResponseDTO create(DebitoDTO debitoDTO);

    DebitoResponseDTO update(Long id, DebitoDTO debitoDTO);

    void delete(Long id);

    List<DebitoResponseDTO> findByTitular(String titular);

    Long count();
}