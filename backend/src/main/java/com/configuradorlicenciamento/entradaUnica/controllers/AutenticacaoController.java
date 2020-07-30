package com.configuradorlicenciamento.entradaUnica.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.entradaUnica.dtos.AutenticacaoDTO;
import com.configuradorlicenciamento.entradaUnica.interfaces.IAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

	@Autowired
	IAutenticacaoService autenticacaoService;

	@RequestMapping(method = RequestMethod.GET, value="/entradaUnica/{sessionKey}")
	public RedirectView entradaUnica(@PathVariable("sessionKey") String sessionKey) {

		autenticacaoService.entradaUnica(sessionKey);

		return new RedirectView(VariaveisAmbientes.baseUrlFrontend());

	}

	@RequestMapping(method = RequestMethod.POST, value="/login")
	public RedirectView login(@Valid @RequestBody AutenticacaoDTO autenticacao) {

		autenticacaoService.login(autenticacao);

		return new RedirectView(VariaveisAmbientes.baseUrlFrontend());

	}

	@RequestMapping(method = RequestMethod.GET, value="/usuario-logado")
	public ResponseEntity<Principal> usuarioLogado(HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		return ResponseEntity.ok()
				.header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
				.body(principal);

	}

}
