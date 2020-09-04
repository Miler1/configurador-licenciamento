package com.configuradorlicenciamento.taxaadministrativa.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.interfaces.ITaxaAdministrativaService;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_taxa_administrativa_" + data + ".csv";

        CustomMappingStrategy<TaxaAdministrativaCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(TaxaAdministrativaCsv.class);

        downloadCsv(taxaAdministrativaService.listarTaxaAdministrativaParaCsv(), nome, mappingStrategy, response);

    }

}