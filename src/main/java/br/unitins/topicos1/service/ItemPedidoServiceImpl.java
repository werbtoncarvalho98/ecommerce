package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.ItemPedidoDTO;
import br.unitins.topicos1.dto.ItemPedidoResponseDTO;
import br.unitins.topicos1.model.ItemPedido;
import br.unitins.topicos1.repository.ItemPedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService{

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    Validator validator;

    @Override
    public List<ItemPedidoResponseDTO> getAll() {
        List<ItemPedido> list = itemPedidoRepository.listAll();
        return list.stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ItemPedidoResponseDTO findById(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null)
            return null;
        return new ItemPedidoResponseDTO(itemPedido);
    }

    private void validar(ItemPedidoDTO itemPedidoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ItemPedidoDTO>> violations = validator.validate(itemPedidoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public ItemPedidoResponseDTO create(ItemPedidoDTO itemPedidoDTO) throws ConstraintViolationException{
        validar(itemPedidoDTO);

        ItemPedido entity = new ItemPedido();
        entity.setQuantidade(itemPedidoDTO.quantidade());
        entity.setPreco(itemPedidoDTO.preco());

        itemPedidoRepository.persist(entity);
        return new ItemPedidoResponseDTO(entity);
    }

    @Override
    @Transactional
    public ItemPedidoResponseDTO update(Long id, ItemPedidoDTO itemPedidoDTO) throws ConstraintViolationException{
        ItemPedido entityUpdate = itemPedidoRepository.findById(id);
        if (entityUpdate == null)
            throw new NotFoundException("Item Pedido não encontrada.");
        validar(itemPedidoDTO);

       entityUpdate.setQuantidade(itemPedidoDTO.quantidade());
       entityUpdate.setPreco(itemPedidoDTO.preco());

       itemPedidoRepository.persist(entityUpdate);
        return new ItemPedidoResponseDTO(entityUpdate);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    @Override
    public List<ItemPedidoResponseDTO> findByNome(String nome) {
        List<ItemPedido> list = itemPedidoRepository.findByNome(nome);
        return list.stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return itemPedidoRepository.count();
    }
    
}
