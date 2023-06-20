package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.model.Hardware;
import br.unitins.topicos1.model.Pagamento;
import br.unitins.topicos1.model.Pedido;
import br.unitins.topicos1.repository.HardwareRepository;
import br.unitins.topicos1.repository.PagamentoRepository;
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
    PedidoRepository pedidoRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    HardwareRepository hardwareRepository;

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
            return null;
        return new PedidoResponseDTO(pedido);
    }

    private void validar(PedidoDTO pedidoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<PedidoDTO>> violations = validator.validate(pedidoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO pedidoDTO) throws ConstraintViolationException {
        validar(pedidoDTO);

        Pedido entity = new Pedido();
        Pagamento pagamento = pagamentoRepository.findById(pedidoDTO.idPagamento());
        entity.setPagamento(pagamento);
        Hardware hardware = hardwareRepository.findById(pedidoDTO.idHardware());
        entity.setHardware(hardware);

        pedidoRepository.persist(entity);
        return new PedidoResponseDTO(entity);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedidoUpdate = pedidoRepository.findById(id);
        if (pedidoUpdate == null)
            throw new NotFoundException("Pedido não encontrada.");
        validar(pedidoDTO);

        Pagamento pagamento = pagamentoRepository.findById(pedidoDTO.idPagamento());
        pedidoUpdate.setPagamento(pagamento);
        Hardware hardware = hardwareRepository.findById(pedidoDTO.idHardware());
        pedidoUpdate.setHardware(hardware);

        pedidoRepository.persist(pedidoUpdate);
        return new PedidoResponseDTO(pedidoUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoResponseDTO> findByNome(String nome) {
        List<Pedido> list = pedidoRepository.findByNome(nome);
        return list.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return pedidoRepository.count();
    }
}