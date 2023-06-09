package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Boleto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BoletoRepository implements PanacheRepository<Boleto> {

    public List<Boleto> findByCodigo(String codigo) {
        if (codigo == null)
            return null;
        return find("UPPER(codigo) LIKE ?1 ", "%" + codigo.toUpperCase() + "%").list();
    }
}