package com.configuradorlicenciamento.configuracao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PemissionException extends RuntimeException{

    public PemissionException(String exception) {
        super(exception);
    }

}
