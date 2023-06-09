package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PixDTO;
import br.unitins.topicos1.dto.PixResponseDTO;

public interface PixService {

    List<PixResponseDTO> getAll();

    PixResponseDTO findById(Long id);

    PixResponseDTO create(PixDTO pagamentoDTO);

    PixResponseDTO update(Long id, PixDTO pagamentoDTO);

    void delete(Long id);

    List<PixResponseDTO> findByChave(String chave);

    Long count();
}