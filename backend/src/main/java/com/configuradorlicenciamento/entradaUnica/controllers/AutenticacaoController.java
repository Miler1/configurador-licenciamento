package com.configuradorlicenciamento.entradaUnica.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.entradaUnica.dtos.AutenticacaoDTO;
import com.configuradorlicenciamento.entradaUnica.interfaces.IAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public RedirectView entradaUnica(HttpServletRequest request, @PathVariable("sessionKey") String sessionKey) {

		autenticacaoService.entradaUnica(request, sessionKey);

		return new RedirectView(VariaveisAmbientes.baseUrlFrontend() + GlobalReferences.PATH_CONFIGURADOR);

	}

	@RequestMapping(method = RequestMethod.POST, value="/login")
	public ResponseEntity<Void> login(HttpServletRequest request, @Valid @RequestBody AutenticacaoDTO autenticacao) {

		autenticacaoService.login(request, autenticacao);

		return ResponseEntity.ok().build();

	}

	@RequestMapping(method = RequestMethod.GET, value="/usuario-logado")
	public ResponseEntity<Principal> usuarioLogado(HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		return ResponseEntity.ok()
				.header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
				.body(principal);

	}

}
