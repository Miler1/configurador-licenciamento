package com.configuradorlicenciamento.potencialPoluidor.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.potencialPoluidor.interfaces.IPotencialPoluidorService;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/potencialPoluidor")
public class PotencialPoluidorController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IPotencialPoluidorService potencialPoluidorService;

    @GetMapping( value="/findAll")
    public ResponseEntity<List<PotencialPoluidor>> findAll(HttpServletRequest request) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<PotencialPoluidor> potenciaisPoluidores = potencialPoluidorService.findAll();

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(potenciaisPoluidores);

    }
}
