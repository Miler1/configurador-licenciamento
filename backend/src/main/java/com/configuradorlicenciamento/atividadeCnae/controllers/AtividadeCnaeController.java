package com.configuradorlicenciamento.atividadeCnae.controllers;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/atividadeCnae")
public class AtividadeCnaeController extends DefaultController {

    @Autowired
    IAtividadeCnaeService atividadeCnaeService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity<AtividadeCnae> salvar (HttpServletRequest request, @Valid @RequestBody AtividadeCnaeDTO atividadeCnaeDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeCnae atividadeCnae = atividadeCnaeService.salvar(request, atividadeCnaeDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeCnae);

    }

    @GetMapping("/relatorio-cnae")
    public void relatorioCSV (HttpServletResponse response) throws Exception {

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_CNAE_" + data + ".csv";

        downloadCsv(atividadeCnaeService.listarCnaesParaCsv(), nome, response);
    }

}
