package br.unitins.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido extends DefaultEntity {

    @OneToOne
    Pagamento pagamento;
    @OneToOne
    Hardware hardware;

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
}