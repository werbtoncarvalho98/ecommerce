package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CreditoDTO;
import br.unitins.topicos1.dto.CreditoResponseDTO;

public interface CreditoService {

    List<CreditoResponseDTO> getAll();

    CreditoResponseDTO findById(Long id);

    CreditoResponseDTO create(CreditoDTO creditoDTO);

    CreditoResponseDTO update(Long id, CreditoDTO creditoDTO);

    void delete(Long id);

    List<CreditoResponseDTO> findByTitular(String titular);

    Long count();
}