package br.unitins.topicos1.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Hardware extends Produto {

    private String modelo;
    private Date lancamento;
    private Nivel nivel;
    private Integridade integridade;
    
    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getLancamento() {
        return lancamento;
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Integridade getIntegridade() {
        return integridade;
    }

    public void setIntegridade(Integridade integridade) {
        this.integridade = integridade;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}