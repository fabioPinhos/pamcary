package br.com.pancary.clientes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "DTO responsável pelo retorno do Cliente")
@Builder
public class ClienteDTO {

    @ApiModelProperty(notes = "Id")
    private Integer id;

    @ApiModelProperty(notes = "Nome")
    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;

    @ApiModelProperty(notes = "CPF")
    @CPF(message = "Informe um CPF válido")
    private String cpf;

    @ApiModelProperty(notes = "Data de nascimento")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataNascimento;

    @ApiModelProperty(notes = "Sexo")
    private String sexo;

}
