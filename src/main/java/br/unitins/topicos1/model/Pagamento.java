package br.unitins.topicos1.model;

import jakarta.persistence.Entity;

@Entity
public class Pagamento extends DefaultEntity {

    private Double valor;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}