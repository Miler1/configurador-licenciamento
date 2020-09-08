package com.configuradorlicenciamento.taxaLicenciamento.controllers;


import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ICodigoTaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/codigoTaxaLicenciamento")
public class CodigoTaxaLicenciamentoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    ICodigoTaxaLicenciamentoService codigoTaxaLicenciamentoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<CodigoTaxaLicenciamento> salvar(HttpServletRequest request, @Valid @RequestBody CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoService.salvar(request, codigoTaxaLicenciamentoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigoTaxaLicenciamento);

    }

    @PostMapping(value="/listar")
    public ResponseEntity<Page<CodigoTaxaLicenciamento>> listar(HttpServletRequest request,
                                                                @PageableDefault(size = 20) Pageable pageable,
                                                                @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<CodigoTaxaLicenciamento> codigosTaxa = codigoTaxaLicenciamentoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigosTaxa);

    }

}
