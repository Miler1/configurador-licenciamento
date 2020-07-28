package com.configuradorlicencimento.configuracao.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DefaultController {

    protected ResponseEntity<InputStreamResource> download(File arquivo, String nomeDocumento) throws FileNotFoundException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/octet-stream"));
        httpHeaders.setContentDispositionFormData("attachment", nomeDocumento);

        InputStreamResource isr = new InputStreamResource(new FileInputStream(arquivo));

        return new ResponseEntity<>(isr, httpHeaders, HttpStatus.OK);

    }

}
