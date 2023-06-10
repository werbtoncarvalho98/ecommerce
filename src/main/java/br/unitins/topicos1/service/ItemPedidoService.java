package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ItemPedidoDTO;
import br.unitins.topicos1.dto.ItemPedidoResponseDTO;

public interface ItemPedidoService {

    List<ItemPedidoResponseDTO> getAll();

    ItemPedidoResponseDTO findById(Long id);

    ItemPedidoResponseDTO create(ItemPedidoDTO itemPedidoDTO);

    ItemPedidoResponseDTO update(Long id, ItemPedidoDTO itemPedidoDTO);

    void delete(Long id);

    List<ItemPedidoResponseDTO> findByNome(String nome);

    Long count();
}
