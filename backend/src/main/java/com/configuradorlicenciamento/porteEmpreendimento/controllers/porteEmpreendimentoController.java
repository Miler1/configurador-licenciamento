package com.configuradorlicenciamento.porteEmpreendimento.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.porteEmpreendimento.interfaces.IPorteEmpreendimentoService;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/porteEmpreendimento")
public class porteEmpreendimentoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IPorteEmpreendimentoService porteEmpreendimentoService;

    @GetMapping( value="/findAll")
    public ResponseEntity<List<PorteEmpreendimento>> findAll(HttpServletRequest request) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<PorteEmpreendimento> portesEmpreendimento = porteEmpreendimentoService.findAll();

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(portesEmpreendimento);

    }
}
