package br.com.pancary.clientes.service;


import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.model.entities.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    //Obter Clientes
    public Iterable<ClienteDTO> obterTodos(ClienteDTO filtro, int numeroPagina, int qtdePagina);

    Iterable<ClienteDTO> buscarClientePorCPF(String cpf);

    //Obter Clientes
    List<ClienteDTO> obterTodos();

    //Salvar cliente
    ClienteDTO salvar(ClienteDTO cliente);

    ResponseEntity alterar(Integer id, ClienteDTO cliente);

    ResponseEntity remover(Integer id);

}
