package com.configuradorlicenciamento.licenca.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.interfaces.ILicencaService;
import com.configuradorlicenciamento.licenca.models.Licenca;
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
@RequestMapping("/licenca")
public class LicencaController extends DefaultController {

    @Autowired
    ILicencaService iLicencaService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity<Licenca> salvar(HttpServletRequest request, @Valid @RequestBody LicencaDTO licencaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Licenca licenca = iLicencaService.salvar(request, licencaDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(licenca);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/lista")
    public ResponseEntity<Page<Licenca>> lista(HttpServletRequest request,
                                                     @PageableDefault(size = 20) Pageable pageable,
                                                     @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Licenca> licencas = iLicencaService.lista(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(licencas);

    }

    @GetMapping("/relatorio-licenca")
    public void relatorioCSV (HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Licenca_" + data + ".csv";

        downloadCsv(iLicencaService.listarLicencasParaCsv(), nome, response);
    }

}