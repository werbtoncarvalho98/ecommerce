package br.unitins.topicos1.repository;

import java.util.List;
import br.unitins.topicos1.model.Fabricante;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

    public List<Fabricante> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }
}