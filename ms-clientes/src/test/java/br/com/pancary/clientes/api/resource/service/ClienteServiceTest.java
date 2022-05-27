package br.com.pancary.clientes.api.resource.service;

import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.model.entities.Cliente;
import br.com.pancary.clientes.model.repositories.ClientesRepository;
import br.com.pancary.clientes.service.ClienteService;
import br.com.pancary.clientes.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ClienteServiceTest {

    ClienteService clienteService;

    @Autowired
    ClientesRepository repository;

    ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp(){
        this.clienteService =  new ClienteServiceImpl(repository, modelMapper);
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void saveClienteTest(){

        Cliente clienteBuild = Cliente.builder()
                .id(1)
                .nome("Fabio")
                .cpf("19288461817")
                .sexo("masculino")
                .dataNascimento(LocalDate.parse("1977-08-10"))
                .build();

        Cliente clienteSalvo = repository.save(clienteBuild);

        assertNotNull(clienteSalvo.getId());

    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void saveClienteServiceTest(){

        ClienteDTO dto = ClienteDTO.builder().nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();

        ClienteDTO clienteDTO = clienteService.salvar(dto);

        assertNotNull(clienteDTO.getId());
        assertEquals(clienteDTO.getNome(), "Fabio");
        assertEquals(clienteDTO.getSexo(), "masculino");
        assertEquals(clienteDTO.getCpf(), "19288461817");

    }

    @Test
    @DisplayName("Deve remover um cliente")
    public void removerClienteServiceTest() {

        ClienteDTO clienteBuild = ClienteDTO.builder().id(1).nome("Fabio").cpf("19288461815").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        ClienteDTO clienteBuild1 = ClienteDTO.builder().id(1).nome("Fabio").cpf("19288461816").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        ClienteDTO clienteBuild2 = ClienteDTO.builder().id(1).nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();


        ClienteDTO clienteSalvo = clienteService.salvar(clienteBuild);
        ClienteDTO clienteSalvo1 = clienteService.salvar(clienteBuild1);
        ClienteDTO clienteSalvo2 = clienteService.salvar(clienteBuild2);

        clienteService.remover(clienteBuild2.getId());

        Iterable<ClienteDTO> clientes = clienteService.obterTodos(clienteBuild2, 1, 1);

        assertNull(clientes);

    }

    @Test
    @DisplayName("Deve buscar um cliente por CPF")
    public void buscarClientePorCPFServiceTest(){

        ClienteDTO clienteBuild = ClienteDTO.builder().nome("Fabio").cpf("19288461815").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        ClienteDTO clienteBuild1 = ClienteDTO.builder().nome("Fabio").cpf("19288461816").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        ClienteDTO clienteBuild2 = ClienteDTO.builder().nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();

        ClienteDTO clienteSalvo = clienteService.salvar(clienteBuild);
        ClienteDTO clienteSalvo1 = clienteService.salvar(clienteBuild1);
        ClienteDTO clienteSalvo2 = clienteService.salvar(clienteBuild2);

        Iterable<ClienteDTO> clientes = clienteService.buscarClientePorCPF(clienteBuild2.getCpf());

        if(clientes.iterator().hasNext()){
            assertEquals(clientes.iterator().next().getCpf(), "19288461817");
        }else{
            assertTrue(false);
        }

    }

}
