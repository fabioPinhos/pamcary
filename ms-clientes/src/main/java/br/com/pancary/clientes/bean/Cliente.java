package br.com.pancary.clientes.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;

}
