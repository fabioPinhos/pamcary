package br.com.pancary.clientes.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "cpf", length = 30)
    private String cpf;

    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    private String sexo;




}
