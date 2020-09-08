package com.configuradorlicenciamento.taxaLicenciamento.controllers;


import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ICodigoTaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/codigoTaxalicenciamento")
public class CodigoTaxaLicenciamentoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    ICodigoTaxaLicenciamentoService codigoTaxaLicenciamentoService;

    @PostMapping(value="/listar")
    public ResponseEntity<Page<CodigoTaxaLicenciamento>> listar(HttpServletRequest request,
                                                                @PageableDefault(size = 20) Pageable pageable,
                                                                @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<CodigoTaxaLicenciamento> codigosTaxa = codigoTaxaLicenciamentoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(codigosTaxa);

    }

    @GetMapping(value = "/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);
        System.out.println("relatorio");
        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Taxa_Licenciamento_" + data + ".csv";

        CustomMappingStrategy<CodigoTaxaLicenciamentoCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(CodigoTaxaLicenciamentoCsv.class);

        downloadCsv(codigoTaxaLicenciamentoService.listarCodigoTaxaLicenciamentoParaCsv(), nome, mappingStrategy, response);

    }

}
