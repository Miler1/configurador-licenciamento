package com.configuradorlicenciamento.parametro.controllers;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.interfaces.IParametroService;
import com.configuradorlicenciamento.parametro.models.Parametro;
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
@RequestMapping("/parametro")
public class ParametroController extends DefaultController {

    @Autowired
    IParametroService parametroService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<Parametro> salvar(HttpServletRequest request, @Valid @RequestBody ParametroDTO parametroDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Parametro parametro = parametroService.salvar(request, parametroDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(parametro);

    }

    @PostMapping(value="/listar")
    public ResponseEntity<Page<Parametro>> listar(HttpServletRequest request,
                                                     @PageableDefault(size = 20) Pageable pageable,
                                                     @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Parametro> parametros = parametroService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(parametros);

    }

    @GetMapping("/relatorio")
    public void relatorioCSV (HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_CNAE_" + data + ".csv";

        CustomMappingStrategy<AtividadeCnaeCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(AtividadeCnaeCsv.class);

        downloadCsv(parametroService.listarParametrosParaCsv(), nome, mappingStrategy, response);

    }

}