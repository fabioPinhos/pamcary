package br.com.pancary.clientes.service.impl;


import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.exception.ClienteNotFoundException;
import br.com.pancary.clientes.model.entities.Cliente;
import br.com.pancary.clientes.model.repositories.ClientesRepository;
import br.com.pancary.clientes.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    ClientesRepository repository;

    public ClienteServiceImpl(ClientesRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cliente salvar(Cliente cliente){

        if(cliente != null && !cliente.getCpf().isEmpty()){
            cliente.setCpf(cliente.getCpf().replaceAll("[^0-9]", ""));
        }

        Iterable<Cliente> byCpf = repository.findByCpf(cliente.getCpf());

        if(!byCpf.iterator().hasNext()){
            repository.save(cliente);
        }

        return cliente;
    }

    @Transactional
    public ResponseEntity alterar(Integer id, Cliente cliente) {

        Optional<Cliente> clienteResp = repository.findById(id);

        ResponseEntity objectResponseEntity = clienteResp
                .map(clienteExistente -> {

                    cliente.setId(clienteExistente.getId());

                    if (cliente.getCpf() == null) {
                        cliente.setCpf(clienteExistente.getCpf());
                    }

                    if (cliente.getNome() == null) {
                        cliente.setNome(clienteExistente.getNome());
                    }

                    if (cliente.getDataNascimento() == null) {
                        cliente.setDataNascimento(clienteExistente.getDataNascimento());
                    }

                    if (cliente.getSexo() == null) {
                        cliente.setSexo(clienteExistente.getSexo());
                    }

                    repository.save(cliente);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

        return objectResponseEntity;
    }

    @Override
    public ResponseEntity remover(Integer id) {

        Optional<Cliente> cliente = repository.findById(id);

        if(cliente.isPresent()){
            repository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public Iterable<Cliente> obterTodos(Cliente filtro, int numeroPagina, int qtdePagina) {

        //Filtro de pesquisa
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        //Paginação com ordenção pelo nome
        if(qtdePagina >= 5) qtdePagina = 5;
        Pageable page = PageRequest.of(numeroPagina,qtdePagina, Sort.by("nome"));

        return repository.findAll(example, page);
    }

    @Override
    public Iterable<Cliente> buscarClientePorCPF(String cpf){
        return repository.findByCpf(cpf);
    }

    @Override
    public List<Cliente> obterTodos() {

        List<Cliente> clientes = repository.findAll();

        return clientes;
    }}
