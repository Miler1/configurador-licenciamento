package com.configuradorlicenciamento.tipologia.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.interfaces.ITipologiaService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/tipologia")
public class TipologiaController extends DefaultController {

    @Autowired
    ITipologiaService tipologiaService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity<Tipologia> salvar (HttpServletRequest request, @Valid @RequestBody TipologiaDTO tipologiaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Tipologia tipologia = tipologiaService.salvar(request, tipologiaDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(tipologia);

    }

}
