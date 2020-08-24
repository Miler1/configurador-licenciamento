package com.configuradorlicenciamento.requisitotecnico.controllers;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoDTO;
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
import javax.validation.Valid;

@RestController
@RequestMapping("/requisitoTecnico")
public class RequisitoTecnicoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IRequisitoTecnicoService requisitoTecnicoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<RequisitoTecnico> salvar(HttpServletRequest request, @Valid @RequestBody RequisitoTecnicoDTO requisitoTecnicoDTO) throws Exception {

//        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoTecnico requisitoTecnico = requisitoTecnicoService.salvar(request, requisitoTecnicoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoTecnico);

    }

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<RequisitoTecnico>> lista(HttpServletRequest request,
                                                        @PageableDefault(size = 20) Pageable pageable,
                                                        @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<RequisitoTecnico> requisitosTecnicos = requisitoTecnicoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitosTecnicos);

    }

}
