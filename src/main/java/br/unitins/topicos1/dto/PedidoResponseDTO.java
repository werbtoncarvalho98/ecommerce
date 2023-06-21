package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Hardware;
import br.unitins.topicos1.model.Pagamento;
import br.unitins.topicos1.model.Pedido;
import br.unitins.topicos1.model.Usuario;

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
