package org.acme.dto;

import org.acme.model.Hardware;
import org.acme.model.Pagamento;
import org.acme.model.Pedido;
import org.acme.model.Usuario;

public record PedidoResponseDTO(
        Long id,
        Usuario usuario,
        Pagamento pagamento,
        Hardware hardware) {

    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getUsuario(),
                pedido.getPagamento(),
                pedido.getHardware());
    }
}
