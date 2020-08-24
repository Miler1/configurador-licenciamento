package com.configuradorlicenciamento.configuracao.exceptions;

import lombok.Getter;

import java.util.Date;

@Getter
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
    private String error;
    private Integer status;

    public ExceptionResponse(Date timestamp, String message, String details, String httpCodeMessage, Integer status) {

        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.error = httpCodeMessage;
        this.status = status;

    }
}