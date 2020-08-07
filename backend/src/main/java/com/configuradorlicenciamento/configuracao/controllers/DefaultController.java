package com.configuradorlicenciamento.configuracao.controllers;

//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;

import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;

import java.security.Principal;

public class DefaultController {

//	protected ResponseEntity<InputStreamResource> downloadDocumento(File arquivo, String nomeDocumento) throws FileNotFoundException {
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.parseMediaType("application/octet-stream"));
//		httpHeaders.setContentDispositionFormData("attachment", nomeDocumento);
//
//		InputStreamResource isr = new InputStreamResource(new FileInputStream(arquivo));
//
//		return new ResponseEntity<>(isr, httpHeaders, HttpStatus.OK);
//
//	}

	protected void verificarPermissao(Acao... acoes) {

//		Usuario usuarioSessao = EntradaUnicaWS.ws.buscarUsuarioPorLogin(Principal.class.);
//
//		boolean permitido = false;
//
//		for (Acao acao : acoes)
//			permitido = permitido || (usuarioSessao != null && usuarioSessao.hasPermissao(acao.codigo));
//
//		if (!permitido) {
//
//			response.status = Http.StatusCode.FORBIDDEN;
//			renderMensagem(Mensagem.PERMISSAO_NEGADA);
//		}
	}

}
