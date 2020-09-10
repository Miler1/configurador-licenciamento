package com.configuradorlicenciamento.taxaAdministrativa.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.taxaAdministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaAdministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaAdministrativa.interfaces.ITaxaAdministrativaService;
import com.configuradorlicenciamento.taxaAdministrativa.models.TaxaAdministrativa;
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

    @PostMapping(value = "/editar")
    public ResponseEntity<TaxaAdministrativa> editar(HttpServletRequest request, @Valid @RequestBody TaxaAdministrativaDTO taxaAdministrativaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        TaxaAdministrativa taxaAdministrativa = taxaAdministrativaService.editar(request, taxaAdministrativaDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(taxaAdministrativa);

    }

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<TaxaAdministrativa>> listar(HttpServletRequest request,
                                                           @PageableDefault(size = 20) Pageable pageable,
                                                           @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<TaxaAdministrativa> taxaAdministrativas = taxaAdministrativaService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(taxaAdministrativas);

    }

    @GetMapping(value = "/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Taxa_Administrativa_" + data + ".csv";

        CustomMappingStrategy<TaxaAdministrativaCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(TaxaAdministrativaCsv.class);

        downloadCsv(taxaAdministrativaService.listarTaxaAdministrativaParaCsv(), nome, mappingStrategy, response);

    }

}