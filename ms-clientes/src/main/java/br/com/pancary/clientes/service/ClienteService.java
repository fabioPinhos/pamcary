package br.com.pancary.clientes.service;


import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.model.entities.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    //Obter Clientes
    public Iterable<Cliente> obterTodos(Cliente filtro, int numeroPagina, int qtdePagina);

    Iterable<Cliente> buscarClientePorCPF(String cpf);

    //Obter Clientes
    List<Cliente> obterTodos();

    //Salvar cliente
    Cliente salvar(Cliente cliente);

    ResponseEntity alterar(Integer id, Cliente cliente);

    ResponseEntity remover(Integer id);

}
