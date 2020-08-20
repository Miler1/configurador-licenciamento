package com.configuradorlicenciamento.parametro.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.interfaces.IParametroService;
import com.configuradorlicenciamento.parametro.models.Parametro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/parametro")
public class ParametroController extends DefaultController {

    @Autowired
    IParametroService parametroService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity<Parametro> salvar(HttpServletRequest request, @Valid @RequestBody ParametroDTO parametroDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Parametro parametro = parametroService.salvar(request, parametroDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(parametro);

    }
}