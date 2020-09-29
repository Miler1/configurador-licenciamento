package com.configuradorlicenciamento.configuracao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConfiguradorNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(ConfiguradorNotFoundException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ExceptionResponse> handleConflictException(ConflictException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PermissionException.class)
    public final ResponseEntity<ExceptionResponse> handleConflictException(PermissionException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public final ResponseEntity<ExceptionResponse> handleConflictException(UnauthenticatedException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ExceptionResponse> handleException(RuntimeException ex, WebRequest request, HttpStatus statusResponse) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false), statusResponse.getReasonPhrase(), statusResponse.value());

        return new ResponseEntity<>(exceptionResponse, statusResponse);
    }
}
