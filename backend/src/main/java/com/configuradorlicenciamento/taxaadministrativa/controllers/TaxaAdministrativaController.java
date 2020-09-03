package com.configuradorlicenciamento.taxaadministrativa.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.interfaces.ITaxaAdministrativaService;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/taxaAdministrativa")
public class TaxaAdministrativaController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    ITaxaAdministrativaService taxaAdministrativaService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<TaxaAdministrativa> salvar(HttpServletRequest request, @Valid @RequestBody TaxaAdministrativaDTO taxaAdministrativaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        TaxaAdministrativa taxaAdministrativa = taxaAdministrativaService.salvar(request, taxaAdministrativaDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(taxaAdministrativa);

    }

}