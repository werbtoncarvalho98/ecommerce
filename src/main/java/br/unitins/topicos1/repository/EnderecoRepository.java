package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        if (cep == null)
            return null;
        return find("UPPER(cep) LIKE ?1 ", "%" + cep.toUpperCase() + "%").list();
    }
}
