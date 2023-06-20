package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Hardware;
import br.unitins.topicos1.model.Pagamento;
import br.unitins.topicos1.model.Pedido;

public record PedidoResponseDTO(
        Long id,
        Pagamento pagamento,
        Hardware hardware) {

    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getPagamento(),
                pedido.getHardware());
    }
}
