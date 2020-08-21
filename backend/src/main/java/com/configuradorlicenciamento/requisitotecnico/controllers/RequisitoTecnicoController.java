package com.configuradorlicenciamento.requisitotecnico.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.interfaces.IRequisitoTecnicoService;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
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

@RestController
@RequestMapping("/requisitoTecnico")
public class RequisitoTecnicoController extends DefaultController {

    @Autowired
    IRequisitoTecnicoService requisitoTecnicoService;

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<RequisitoTecnico>> lista(HttpServletRequest request,
                                                        @PageableDefault(size = 20) Pageable pageable,
                                                        @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<RequisitoTecnico> requisitosTecnicos = requisitoTecnicoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(requisitosTecnicos);

    }

}
