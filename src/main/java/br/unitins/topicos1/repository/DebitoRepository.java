package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Debito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DebitoRepository implements PanacheRepository<Debito> {

    public List<Debito> findByTitular(String titular) {
        if (titular == null)
            return null;
        return find("UPPER(titular) LIKE ?1 ", "%" + titular.toUpperCase() + "%").list();
    }
}