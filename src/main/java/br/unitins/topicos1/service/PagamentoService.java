package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PagamentoDTO;
import br.unitins.topicos1.dto.PagamentoResponseDTO;

public interface PagamentoService {
    
    List<PagamentoResponseDTO> getAll();

    PagamentoResponseDTO findById(Long id);

    PagamentoResponseDTO create(PagamentoDTO pagamentoDTO);

    PagamentoResponseDTO update(Long id, PagamentoDTO pagamentoDTO);

    void delete(Long id);

    List<PagamentoResponseDTO> findByValor(Double valor);

    long count();
}
