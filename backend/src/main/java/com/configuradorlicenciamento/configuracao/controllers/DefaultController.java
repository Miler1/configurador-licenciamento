package com.configuradorlicenciamento.configuracao.controllers;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

public class DefaultController {

	protected ResponseEntity<InputStreamResource> downloadDocumento(File arquivo, String nomeDocumento) throws FileNotFoundException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.parseMediaType("application/octet-stream"));
		httpHeaders.setContentDispositionFormData("attachment", nomeDocumento);

		InputStreamResource isr = new InputStreamResource(new FileInputStream(arquivo));

		return new ResponseEntity<>(isr, httpHeaders, HttpStatus.OK);

	}

	protected void downloadCsv(List lista, String nome, HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

		response.setContentType("application/octet-stream");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + nome + "\"");

		StatefulBeanToCsv<AtividadeCnae> writer = new StatefulBeanToCsvBuilder<AtividadeCnae>(response.getWriter())
				.withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.build();

		writer.write(lista);
	}

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
