package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Credito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditoRepository implements PanacheRepository<Credito> {

    public List<Credito> findByTitular(String titular) {
        if (titular == null)
            return null;
        return find("UPPER(titular) LIKE ?1 ", "%" + titular.toUpperCase() + "%").list();
    }
}