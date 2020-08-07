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

import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.exceptions.PemissionException;
import com.configuradorlicenciamento.configuracao.interfaces.IDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	IDefaultService defaultService;

	protected void verificarPermissao(HttpServletRequest request, Acao... acoes) throws Exception {

		Boolean permitido = defaultService.verificaPermissao(request, acoes);

		if(!permitido) {

			throw new PemissionException("Usuário sem permissão para realizar a ação!");

		}

	}

}
