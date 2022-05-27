package br.com.pancary.clientes.service.impl;


import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.exception.ClienteNotFoundException;
import br.com.pancary.clientes.model.entities.Cliente;
import br.com.pancary.clientes.model.repositories.ClientesRepository;
import br.com.pancary.clientes.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    private ModelMapper modelMapper;

    public ClienteServiceImpl(ClientesRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ClienteDTO salvar(ClienteDTO cliente){

        ClienteDTO clienteResponse = ClienteDTO.builder().build();

        Cliente entity = modelMapper.map(cliente, Cliente.class);

        if(cliente != null && !cliente.getCpf().isEmpty()){
            cliente.setCpf(cliente.getCpf().replaceAll("[^0-9]", ""));
        }

        Iterable<Cliente> byCpf = repository.findByCpfContains(cliente.getCpf());

        Cliente save = null;

        if(!byCpf.iterator().hasNext()){
            save = repository.save(entity);
        }

        if (save != null && save.getId() != null) {
            clienteResponse = this.modelMapper.map(save, ClienteDTO.class);
        }

        return clienteResponse;
    }

    @Transactional
    public ResponseEntity alterar(Integer id, ClienteDTO cliente) {

        Cliente entity = modelMapper.map(cliente, Cliente.class);

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

                    repository.save(entity);

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
    public Iterable<ClienteDTO> obterTodos(ClienteDTO filtro, int numeroPagina, int qtdePagina) {

        Cliente entity = modelMapper.map(filtro, Cliente.class);

        //Filtro de pesquisa
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(entity, matcher);

        //Paginação com ordenção pelo nome
        if(qtdePagina >= 5) qtdePagina = 5;
        Pageable page = PageRequest.of(numeroPagina,qtdePagina, Sort.by("nome"));

        Page findAll = repository.findAll(example, page);
        Iterable<ClienteDTO> clienteDTOS = null;

        if(findAll != null && findAll.iterator().hasNext()){
            clienteDTOS = this.modelMapper.map(findAll, new TypeToken<Iterable<ClienteDTO>>() {}.getType());
        }

        return clienteDTOS;
    }

    @Override
    public Iterable<ClienteDTO> buscarClientePorCPF(String cpf){

        Iterable<Cliente> byCpf = repository.findByCpfContains(cpf);
        Iterable<ClienteDTO> clienteDTOS = null;


        if(byCpf != null && byCpf.iterator().hasNext()){
            clienteDTOS = this.modelMapper.map(byCpf, new TypeToken<Iterable<ClienteDTO>>() {}.getType());
        }


        return clienteDTOS;
    }

    @Override
    public List<ClienteDTO> obterTodos() {

        List<Cliente> clientes = repository.findAll();
        List<ClienteDTO> clientesDtos = null;

        if(clientes != null && clientes.iterator().hasNext()){
            clientesDtos = this.modelMapper.map(clientes, new TypeToken<Iterable<ClienteDTO>>() {}.getType());
        }

        return clientesDtos;
    }}
