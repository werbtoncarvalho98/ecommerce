package br.unitins.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento extends DefaultEntity {

    private Double valor;

    @OneToOne
    private Pedido pedido;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}