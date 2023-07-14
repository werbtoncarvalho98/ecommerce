package org.acme.service;

import java.util.List;

import org.acme.dto.ItemPedidoDTO;
import org.acme.dto.ItemPedidoResponseDTO;

public interface ItemPedidoService {

    List<ItemPedidoResponseDTO> getAll();

    ItemPedidoResponseDTO findById(Long id);

    ItemPedidoResponseDTO create(ItemPedidoDTO itemPedidoDTO);

    ItemPedidoResponseDTO update(Long id, ItemPedidoDTO itemPedidoDTO);

    void delete(Long id);

    List<ItemPedidoResponseDTO> findByNome(String nome);

    Long count();
}
