package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Pix;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixRepository implements PanacheRepository<Pix> {

    public List<Pix> findByChave(String chave) {
        if (chave == null)
            return null;
        return find("UPPER(chave) LIKE ?1 ", "%" + chave.toUpperCase() + "%").list();
    }
}