package br.com.pancary.clientes.controllers;


import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/clientes")
@Api("API Clientes")
public class    ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/filtro/{numeroPagina}/{qtdePagina}")
    @ApiOperation("Busca todos os clientes com filtro")
    public ResponseEntity obterClientes(ClienteDTO filtro,
                                        @PathVariable int numeroPagina,
                                        @PathVariable int qtdePagina){

        Iterable<ClienteDTO> listaResponseDTO = clienteService.obterTodos(filtro, numeroPagina, qtdePagina);

        return ResponseEntity.ok(listaResponseDTO);

    }

    @GetMapping
    @ApiOperation("Busca todos os clientes")
    public ResponseEntity find(){
        return ResponseEntity.ok(clienteService.obterTodos());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity buscarClientePorCPF(@PathVariable String cpf){

        Iterable<ClienteDTO> clientes = clienteService.buscarClientePorCPF(cpf);

        return clientes != null && clientes.iterator().hasNext() ? ResponseEntity.ok(clientes) : ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity salvar(@RequestBody @Valid ClienteDTO dto){

        ClienteDTO salvar = clienteService.salvar(dto);

        return salvar.getId() != null ? ResponseEntity.ok(salvar) : ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity alterar(@PathVariable Integer id,
                                  @RequestBody ClienteDTO cliente){

        return clienteService.alterar(id, cliente);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Integer id){
        return clienteService.remover(id);
    }

}
