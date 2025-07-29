package com.servico.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VeiculoCadastradoException extends RuntimeException {
    public VeiculoCadastradoException(String message) {
        super(message);
    }
}
