package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Hardware;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HardwareRepository implements PanacheRepository<Hardware> {

    public List<Hardware> findByMarca(String marca) {
        if (marca == null)
            return null;
        return find("UPPER(marca) LIKE ?1 ", "%" + marca.toUpperCase() + "%").list();
    }
}