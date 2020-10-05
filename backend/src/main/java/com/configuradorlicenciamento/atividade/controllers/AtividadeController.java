package com.configuradorlicenciamento.atividade.controllers;

import com.configuradorlicenciamento.atividade.interfaces.IAtividadeService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
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
@RequestMapping("/atividadeLicenciavel/")
public class AtividadeController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IAtividadeService atividadeService;

    @PostMapping(value = "listar")
    public ResponseEntity<Page<Atividade>> listarAtividadesLicenciaveis(HttpServletRequest request,
                                                                                          @PageableDefault(size = 20) Pageable pageable,
                                                                                          @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Atividade> atividades = atividadeService.listarAtividadesLicenciaveis(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividades);

    }
}
