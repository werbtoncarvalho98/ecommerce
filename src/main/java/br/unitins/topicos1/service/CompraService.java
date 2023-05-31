package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CompraDTO;
import br.unitins.topicos1.dto.CompraResponseDTO;

public interface CompraService {

    List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(CompraDTO compraDTO);

    CompraResponseDTO update(Long id, CompraDTO compraDTO);

    void delete(Long id);

    List<CompraResponseDTO> findByNome(String nome);

    long count();
}