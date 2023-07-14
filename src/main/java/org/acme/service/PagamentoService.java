package org.acme.service;

import java.util.List;

import org.acme.dto.PagamentoDTO;
import org.acme.dto.PagamentoResponseDTO;

public interface PagamentoService {
    
    List<PagamentoResponseDTO> getAll();

    PagamentoResponseDTO findById(Long id);

    PagamentoResponseDTO create(PagamentoDTO pagamentoDTO);

    PagamentoResponseDTO update(Long id, PagamentoDTO pagamentoDTO);

    void delete(Long id);

    List<PagamentoResponseDTO> findByValor(Double valor);

    long count();
}
