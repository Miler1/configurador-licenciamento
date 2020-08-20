package com.configuradorlicenciamento.configuracao.exceptions;

import com.configuradorlicenciamento.configuracao.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception {

//    @ExceptionHandler(HttpClientErrorException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse errorHandle(HttpClientErrorException ex) {
//
//        String responseBody = ex.getResponseBodyAsString(); // The response body structure is different, you should get the message and stacktrace separately and assign it to your own response object
//        ErrorResponse errorResponse = new ErrorResponse();
//        Error error = new Error();
//        error.setMessage(ex.getMessage());
//        error.setCode(HttpStatus.BAD_REQUEST);
//        errorResponse.setError(error);
//        return errorResponse;
//    }
}
