package org.acme.model;

import jakarta.persistence.Entity;

@Entity
public class ItemPedido extends DefaultEntity {

    private Integer quantidade;
    private float preco;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}