package br.unitins.topicos1.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.topicos1.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    public List<Estado> findBySigla(String sigla) {
        if (sigla == null)
            return null;
        return find("UPPER(sigla) LIKE ?1 ", "%" + sigla.toUpperCase() + "%").list();
    }
}