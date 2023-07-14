package org.acme.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.dto.PedidoDTO;
import org.acme.dto.PedidoResponseDTO;
import org.acme.model.Hardware;
import org.acme.model.Pagamento;
import org.acme.model.Pedido;
import org.acme.model.Usuario;
import org.acme.repository.HardwareRepository;
import org.acme.repository.PagamentoRepository;
import org.acme.repository.PedidoRepository;
import org.acme.repository.UsuarioRepository;

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
    UsuarioRepository usuarioRepository;

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
        Usuario usuario = usuarioRepository.findById(pedidoDTO.idUsuario());
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        entity.setUsuario(usuario);

        pedidoRepository.persist(entity);
        return new PedidoResponseDTO(entity);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido entityUpdate = pedidoRepository.findById(id);
        if (entityUpdate == null)
            throw new NotFoundException("Pedido não encontrada.");
        validar(pedidoDTO);

        Pagamento pagamento = pagamentoRepository.findById(pedidoDTO.idPagamento());
        entityUpdate.setPagamento(pagamento);
        Hardware hardware = hardwareRepository.findById(pedidoDTO.idHardware());
        entityUpdate.setHardware(hardware);

        pedidoRepository.persist(entityUpdate);
        return new PedidoResponseDTO(entityUpdate);
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