package org.acme.repository;

import java.util.List;

import org.acme.model.Estado;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    public List<Estado> findBySigla(String sigla) {
        if (sigla == null)
            return null;
        return find("UPPER(sigla) LIKE ?1 ", "%" + sigla.toUpperCase() + "%").list();
    }
}