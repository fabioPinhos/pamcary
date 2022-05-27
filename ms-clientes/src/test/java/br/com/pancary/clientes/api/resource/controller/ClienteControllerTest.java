package br.com.pancary.clientes.api.resource.controller;

import br.com.pancary.clientes.dto.ClienteDTO;
import br.com.pancary.clientes.model.entities.Cliente;
import br.com.pancary.clientes.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    static String CLIENTE_API = "/clientes";

    @Autowired
    MockMvc mvc;

    @MockBean
    ClienteService clienteService;

    @Test
    @DisplayName("Deve criar um cliente com sucesso.")
    public void createClienteTest() throws Exception {

        ClienteDTO dto = ClienteDTO.builder().id(1).nome("Fabio").cpf("19288461817").sexo("masculino").dataNascimento(LocalDate.now()).build();

        BDDMockito.given(clienteService.salvar(Mockito.any(ClienteDTO.class))).willReturn(dto);

        //Transforma um objeto em um JSON
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CLIENTE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect( status().is2xxSuccessful() )
                .andExpect( jsonPath("id").isNotEmpty() )
                .andExpect( jsonPath( "nome" ).value(dto.getNome()))
                .andExpect( jsonPath( "cpf" ).value(dto.getCpf()))
                .andExpect( jsonPath( "sexo" ).value(dto.getSexo()));

    }

//    @Test
//    @DisplayName("Deve lançar erro de validação quando não houver dados para criar um cliente.")
    public void createInvalidClientTest(){

    }
}
