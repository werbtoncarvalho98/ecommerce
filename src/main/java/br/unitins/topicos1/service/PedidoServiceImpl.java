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
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository compraRepository;

    @Inject
    Validator validator;

    @Override
    public List<PedidoResponseDTO> getAll() {
        List<Pedido> list = compraRepository.listAll();
        return list.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido compra = compraRepository.findById(id);
        if (compra == null)
            return null;
        return new PedidoResponseDTO(compra);
    }

    private void validar(PedidoDTO compradto) throws ConstraintViolationException {
        Set<ConstraintViolation<PedidoDTO>> violations = validator.validate(compradto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO compradto) throws ConstraintViolationException {
        validar(compradto);

        Pedido entity = new Pedido();
        entity.setData(compradto.data());
        entity.setStatus(compradto.status());

        compraRepository.persist(entity);
        return new PedidoResponseDTO(entity);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoDTO compradto) {
        Pedido compraUpdate = compraRepository.findById(id);
        if (compraUpdate == null)
            throw new NotFoundException("Pedido não encontrada.");
        validar(compradto);

        compraUpdate.setData(compradto.data());
        compraUpdate.setStatus(compradto.status());

        compraRepository.persist(compraUpdate);
        return new PedidoResponseDTO(compraUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        compraRepository.deleteById(id);
    }

    @Override
    public List<PedidoResponseDTO> findByNome(String nome) {
        List<Pedido> list = compraRepository.findByNome(nome);
        return list.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return compraRepository.count();
    }

}
