package com.configuradorlicenciamento.entradaUnica.interfaces;

import com.configuradorlicenciamento.entradaUnica.dtos.AutenticacaoDTO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface IAutenticacaoService extends AuthenticationProvider {

	Authentication entradaUnica(HttpServletRequest request,  String sessionKey);

	Authentication login(HttpServletRequest request, AutenticacaoDTO autenticacao);

}
