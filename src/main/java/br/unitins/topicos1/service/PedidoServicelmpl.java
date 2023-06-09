package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.model.Pedido;
import br.unitins.topicos1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServicelmpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    Validator validator;

    @Override
    public List<PedidoResponseDTO> getAll() {
        List<Pedido> list = pedidoRepository.listAll();
        return list.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");
        return new PedidoResponseDTO(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO pedidoDTO) throws ConstraintViolationException {
        validar(pedidoDTO);

        Pedido entity = new Pedido();

        // Preencher as propriedades do objeto entity com base nos dados do pedidoDTO

        pedidoRepository.persist(entity);

        return new PedidoResponseDTO(entity);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) throws ConstraintViolationException {
        Pedido pedidoUpdate = pedidoRepository.findById(id);
        if (pedidoUpdate == null)
            throw new NotFoundException("Pedido não encontrado.");

        validar(pedidoDTO);

        // Atualizar as propriedades do objeto pedidoUpdate com base nos dados do pedidoDTO

        pedidoRepository.persist(pedidoUpdate);

        return new PedidoResponseDTO(pedidoUpdate);
    }

    private void validar(PedidoDTO pedidoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<PedidoDTO>> violations = validator.validate(pedidoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoResponseDTO> findByStatus(String status) {
        List<Pedido> list = pedidoRepository.findByStatus(status);
        return list.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return pedidoRepository.count();
    }
}
