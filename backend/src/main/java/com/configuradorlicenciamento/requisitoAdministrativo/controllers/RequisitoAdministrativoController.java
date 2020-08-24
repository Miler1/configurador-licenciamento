package com.configuradorlicenciamento.requisitoAdministrativo.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;

import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoAdministrativo.interfaces.IRequisitoAdministrativoService;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/requisitoAdministrativo")
public class RequisitoAdministrativoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IRequisitoAdministrativoService requisitoAdministrativoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<RequisitoAdministrativo> salvar(HttpServletRequest request, @Valid @RequestBody RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoAdministrativo requisitoAdministrativo = requisitoAdministrativoService.salvar(request, requisitoAdministrativoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoAdministrativo);

    }

}
