package com.configuradorlicencimento.seguranca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @RequestMapping(method = RequestMethod.GET, value="/teste")
    public ResponseEntity<String> teste() {

        return new ResponseEntity<>("Teste OK!", HttpStatus.OK);

    }

}
