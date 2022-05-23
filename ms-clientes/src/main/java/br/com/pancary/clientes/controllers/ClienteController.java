package br.com.pancary.clientes.controllers;


import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.model.entities.Cliente;
import br.com.pancary.clientes.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/clientes")
@Api("API Clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/filtro/{numeroPagina}/{qtdePagina}")
    @ApiOperation("Busca todos os clientes com filtro")
    public ResponseEntity obterClientes(ClienteDTO filtro,
                                        @PathVariable int numeroPagina,
                                        @PathVariable int qtdePagina){

        Iterable<Cliente> listaResponse;
        Iterable<ClienteDTO> listaResponseDTO = null;

        Cliente entity = modelMapper.map(filtro, Cliente.class);

        listaResponse = clienteService.obterTodos(entity, numeroPagina, qtdePagina);

        if(listaResponse != null && listaResponse.iterator().hasNext()){
            listaResponseDTO = this.modelMapper.map(listaResponse, new TypeToken<Iterable<ClienteDTO>>() {}.getType());
        }

        return ResponseEntity.ok(listaResponseDTO);

    }

    @GetMapping
    @ApiOperation("Busca todos os clientes")
    public ResponseEntity find(){

        List<Cliente> listaResponse;
        List<ClienteDTO> listaResponseDTO = null;

        listaResponse = clienteService.obterTodos();

        if(listaResponse != null && listaResponse.iterator().hasNext()){
            listaResponseDTO = this.modelMapper.map(listaResponse, new TypeToken<Iterable<ClienteDTO>>() {}.getType());
        }

        return ResponseEntity.ok(listaResponseDTO);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity buscarClientePorCPF(@PathVariable String cpf){

        Iterable<Cliente> clientes = clienteService.buscarClientePorCPF(cpf);

        return clientes != null && clientes.iterator().hasNext() ? ResponseEntity.ok(clientes) : ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity salvar(@RequestBody @Valid ClienteDTO dto){

        Cliente clienteEntity = modelMapper.map(dto, Cliente.class);

        Cliente salvar = clienteService.salvar(clienteEntity);

        return salvar.getId() != null ? ResponseEntity.ok(salvar) : ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity alterar(@PathVariable Integer id,
                                  @RequestBody ClienteDTO cliente){

        Cliente clienteEntity = modelMapper.map(cliente, Cliente.class);

        return clienteService.alterar(id, clienteEntity);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Integer id){
        return clienteService.remover(id);
    }

}
