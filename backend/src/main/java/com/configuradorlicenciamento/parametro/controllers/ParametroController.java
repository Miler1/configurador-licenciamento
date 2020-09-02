package com.configuradorlicenciamento.parametro.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.parametro.dtos.ParametroCsv;
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

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IParametroService parametroService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<Parametro> salvar(HttpServletRequest request, @Valid @RequestBody ParametroDTO parametroDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Parametro parametro = parametroService.salvar(request, parametroDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(parametro);

    }

    @PostMapping(value = "/editar")
    public ResponseEntity<Parametro> editar(HttpServletRequest request, @Valid @RequestBody ParametroDTO parametroDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Parametro parametro = parametroService.editar(request, parametroDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(parametro);

    }

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<Parametro>> listar(HttpServletRequest request,
                                                  @PageableDefault(size = 20) Pageable pageable,
                                                  @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Parametro> parametros = parametroService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(parametros);

    }

    @GetMapping(value = "/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Parametros_" + data + ".csv";

        CustomMappingStrategy<ParametroCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(ParametroCsv.class);

        downloadCsv(parametroService.listarParametrosParaCsv(), nome, mappingStrategy, response);

    }

}