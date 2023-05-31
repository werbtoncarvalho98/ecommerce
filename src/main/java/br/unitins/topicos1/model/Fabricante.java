package br.unitins.topicos1.model;

import jakarta.persistence.Entity;

@Entity
public class Fabricante extends DefaultEntity {

    private String nome;
    private String website;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}