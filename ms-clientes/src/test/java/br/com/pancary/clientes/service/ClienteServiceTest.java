package br.com.pancary.clientes.service;

import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.model.entities.Cliente;
import br.com.pancary.clientes.model.repositories.ClientesRepository;
import br.com.pancary.clientes.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    @BeforeEach
    public void setUp(){
        this.clienteService =  new ClienteServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void saveClienteTest(){

        Cliente clienteBuild = Cliente.builder().id(1).nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();

        Cliente clienteSalvo = repository.save(clienteBuild);

        assertNotNull(clienteSalvo.getId());

    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void saveClienteServiceTest(){

        ClienteDTO dto = ClienteDTO.builder().nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        Cliente clienteBuild = Cliente.builder().id(1).nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();

        Cliente clienteSalvo = clienteService.salvar(clienteBuild);

        assertNotNull(clienteSalvo.getId());
        assertEquals(clienteSalvo.getNome(), "Fabio");
        assertEquals(clienteSalvo.getSexo(), "masculino");
        assertEquals(clienteSalvo.getCpf(), "19288461817");

    }

    @Test
    @DisplayName("Deve buscar um cliente por CPF")
    public void buscarClientePorCPFServiceTest(){

        Cliente clienteBuild = Cliente.builder().id(1).nome("Fabio").cpf("19288461815").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        Cliente clienteBuild1 = Cliente.builder().id(1).nome("Fabio").cpf("19288461816").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        Cliente clienteBuild2 = Cliente.builder().id(1).nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();


        Cliente clienteSalvo = clienteService.salvar(clienteBuild);
        Cliente clienteSalvo1 = clienteService.salvar(clienteBuild1);
        Cliente clienteSalvo2 = clienteService.salvar(clienteBuild2);


        Iterable<Cliente> clientes = clienteService.buscarClientePorCPF(clienteBuild2.getCpf());

        if(clientes.iterator().hasNext()){
            assertEquals(clientes.iterator().next().getCpf(), "19288461817");
        }else{
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Deve remover um cliente")
    public void removerClienteServiceTest() {

        Cliente clienteBuild = Cliente.builder().id(1).nome("Fabio").cpf("19288461815").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        Cliente clienteBuild1 = Cliente.builder().id(1).nome("Fabio").cpf("19288461816").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();
        Cliente clienteBuild2 = Cliente.builder().id(1).nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.parse("1977-08-10")).build();


        Cliente clienteSalvo = clienteService.salvar(clienteBuild);
        Cliente clienteSalvo1 = clienteService.salvar(clienteBuild1);
        Cliente clienteSalvo2 = clienteService.salvar(clienteBuild2);

        clienteService.remover(clienteBuild2.getId());

        Iterable<Cliente> clientes = clienteService.obterTodos(clienteBuild2, 1, 1);

        assertFalse(clientes.iterator().hasNext());

    }

}
