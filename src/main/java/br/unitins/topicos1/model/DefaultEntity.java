package br.unitins.topicos1.model;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public class DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime inclusao;
    private LocalDateTime alteracao;

    @PrePersist
    private void gerarDataInclusao() {
        inclusao = LocalDateTime.now();
    }

    @PreUpdate
    private void gerarDataAlteracao() {
        alteracao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataInclusao() {
        return inclusao;
    }

    public void setInclusao(LocalDateTime inclusao) {
        this.inclusao = inclusao;
    }

    public LocalDateTime getAlteracao() {
        return alteracao;
    }

    public void setAteracao(LocalDateTime alteracao) {
        this.alteracao = alteracao;
    }
}