package com.configuradorlicenciamento.atividadeCnae.controllers;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.documento.models.Documento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atividadeCnae")
public class AtividadeCnaeController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IAtividadeCnaeService atividadeCnaeService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<AtividadeCnae> salvar(HttpServletRequest request, @Valid @RequestBody AtividadeCnaeDTO atividadeCnaeDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeCnae atividadeCnae = atividadeCnaeService.salvar(request, atividadeCnaeDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeCnae);

    }

    @PostMapping(value = "/editar")
    public ResponseEntity<AtividadeCnae> editar(HttpServletRequest request, @Valid @RequestBody AtividadeCnaeDTO atividadeCnaeDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeCnae atividadeCnae = atividadeCnaeService.editar(request, atividadeCnaeDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeCnae);

    }

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<AtividadeCnae>> listar(HttpServletRequest request,
                                                      @PageableDefault(size = 20) Pageable pageable,
                                                      @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<AtividadeCnae> atividadeCnaes = atividadeCnaeService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeCnaes);

    }

    @GetMapping(value = "/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_de_CNAEs_" + data + ".csv";

        CustomMappingStrategy<AtividadeCnaeCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(AtividadeCnaeCsv.class);

        downloadCsv(atividadeCnaeService.listarCnaesParaCsv(), nome, mappingStrategy, response);

    }

    @GetMapping(value = "/buscarCnaesAtivos")
    public ResponseEntity<List<AtividadeCnae>> buscarCnaesAtivos(HttpServletRequest request) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<AtividadeCnae> cnaeAtivos = atividadeCnaeService.findAtivos();

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(cnaeAtivos);

    }

}