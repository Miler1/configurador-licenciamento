package com.configuradorlicenciamento.atividadeCnae.controllers;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/atividadeCnae")
public class AtividadeCnaeController extends DefaultController {

    @Autowired
    IAtividadeCnaeService atividadeCnaeService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity<AtividadeCnae> salvar (@Valid @RequestBody AtividadeCnaeDTO atividadeCnaeDTO) throws Exception {

        AtividadeCnae atividadeCnae = atividadeCnaeService.salvar(atividadeCnaeDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeCnae);

    }


}
