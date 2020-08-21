package com.configuradorlicenciamento.configuracao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConstraintUniqueViolationException extends RuntimeException {

    public ConstraintUniqueViolationException(String exception) {
        super(exception);
    }
}
