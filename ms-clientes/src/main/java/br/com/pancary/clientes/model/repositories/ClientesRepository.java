package br.com.pancary.clientes.model.repositories;

import br.com.pancary.clientes.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    public Iterable<Cliente> findByCpf(String cpf);

}
