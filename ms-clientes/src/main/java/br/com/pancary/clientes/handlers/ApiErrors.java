package br.com.pancary.clientes.handlers;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String mensagemErro) {
        this(Arrays.asList(mensagemErro));
//        this.errors = Arrays.asList(mensagemErro);
    }
}
