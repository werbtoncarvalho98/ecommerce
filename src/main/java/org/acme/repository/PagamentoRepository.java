package org.acme.repository;

import java.util.List;

import org.acme.model.Pagamento;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public List<Pagamento> findByValor(Double valor) {
        if (valor == null)
            return null;
        return find("UPPER(valor) LIKE ?1 ", "%" + valor + "%").list();
    }
}