package com.configuradorlicenciamento.pergunta.controller;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaCsv;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.interfaces.IPerguntaService;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/pergunta")
public class PerguntaController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IPerguntaService perguntaService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<Pergunta> salvar(HttpServletRequest request, @Valid @RequestBody PerguntaDTO perguntaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Pergunta pergunta = perguntaService.salvar(request, perguntaDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(pergunta);

    }

    @GetMapping(value = "/relatorio")
    public void relatorioCSV (HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Perguntas_" + data + ".csv";

        CustomMappingStrategy<PerguntaCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(PerguntaCsv.class);

        downloadCsv(perguntaService.listarPerguntaParaCsv(), nome, mappingStrategy, response);
    }

    @PostMapping(value = "/editar")
    public ResponseEntity<Pergunta> editar(HttpServletRequest request, @Valid @RequestBody PerguntaDTO perguntaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Pergunta pergunta = perguntaService.editar(request, perguntaDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(pergunta);

    }

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<Pergunta>> listar(HttpServletRequest request,
                                                           @PageableDefault(size = 20) Pageable pageable,
                                                           @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Pergunta> perguntas = perguntaService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(perguntas);

    }

}
