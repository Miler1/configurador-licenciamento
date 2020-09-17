package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.ITipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
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
@RequestMapping("/tipoCaracterizacaoAtividade")
public class TipoCaracterizacaoAtividadeController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    ITipoCaracterizacaoAtividadeService tipoCaracterizacaoAtividadeService;

    @PostMapping(value = "atividadeDispensavel/listar")
    public ResponseEntity<Page<TipoCaracterizacaoAtividade>> listarAtividadesDispensaveis(HttpServletRequest request,
                                                                    @PageableDefault(size = 20) Pageable pageable,
                                                                    @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<TipoCaracterizacaoAtividade> tiposCaracterizacaoAtividade = tipoCaracterizacaoAtividadeService.listarAtividadesDispensaveis(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(tiposCaracterizacaoAtividade);

    }
}
