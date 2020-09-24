package com.configuradorlicenciamento.taxaLicenciamento.controllers;


import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoEdicaoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ICodigoTaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
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

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<CodigoTaxaLicenciamento>> listar(HttpServletRequest request,
                                                                @PageableDefault(size = 20) Pageable pageable,
                                                                @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<CodigoTaxaLicenciamento> codigosTaxa = codigoTaxaLicenciamentoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigosTaxa);

    }

    @PostMapping(value = "/editar")
    public ResponseEntity<CodigoTaxaLicenciamento> editar(HttpServletRequest request, @Valid @RequestBody CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoService.editar(request, codigoTaxaLicenciamentoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigoTaxaLicenciamento);

    }

    @PostMapping(value = "/ativarDesativar/{idTaxaLicenciamento}")
    public ResponseEntity<CodigoTaxaLicenciamento> ativarDesativar(HttpServletRequest request, @PathVariable("idTaxaLicenciamento") Integer idTaxaLicenciamento) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoService.ativarDesativar(request, idTaxaLicenciamento);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigoTaxaLicenciamento);

    }

    @GetMapping(value = "/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);
        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_de_taxas_de_licenciamento_" + data + ".csv";

        CustomMappingStrategy<CodigoTaxaLicenciamentoCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(CodigoTaxaLicenciamentoCsv.class);

        downloadCsv(codigoTaxaLicenciamentoService.listarCodigoTaxaLicenciamentoParaCsv(), nome, mappingStrategy, response);

    }

    @GetMapping(value = "/findById/{idTaxaLicenciamento}")
    public ResponseEntity<CodigoTaxaLicenciamentoEdicaoDTO> findById(HttpServletRequest request,
                                                                     @PathVariable("idTaxaLicenciamento") Integer idTaxaLicenciamento) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        CodigoTaxaLicenciamentoEdicaoDTO codigoTaxaLicenciamentoEdicaoDTO = codigoTaxaLicenciamentoService.findById(idTaxaLicenciamento);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigoTaxaLicenciamentoEdicaoDTO);

    }

}