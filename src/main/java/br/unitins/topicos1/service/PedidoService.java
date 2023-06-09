package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;

public interface PedidoService {

    List<PedidoResponseDTO> getAll();

    PedidoResponseDTO findById(Long id);

    PedidoResponseDTO create(PedidoDTO pedidoDTO);

    PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO);

    void delete(Long id);

    List<PedidoResponseDTO> findByStatus(String status);

    Long count();
}