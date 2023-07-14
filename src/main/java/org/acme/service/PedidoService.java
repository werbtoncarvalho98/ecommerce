package org.acme.service;

import java.util.List;

import org.acme.dto.PedidoDTO;
import org.acme.dto.PedidoResponseDTO;

public interface PedidoService {
    
    List<PedidoResponseDTO> getAll();

    PedidoResponseDTO findById(Long id);

    PedidoResponseDTO create(PedidoDTO compradto);

    PedidoResponseDTO update(Long id, PedidoDTO compradto);

    void delete(Long id);

    List<PedidoResponseDTO> findByNome(String nome);

    Long count();
}
