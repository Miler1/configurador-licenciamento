package com.configuradorlicenciamento.configuracao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PemissionException extends RuntimeException{

    public PemissionException(String exception) {
        super(exception);
    }

}
