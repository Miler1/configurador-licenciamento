package com.configuradorlicenciamento.entradaUnica.interfaces;

import com.configuradorlicenciamento.entradaUnica.dtos.AutenticacaoDTO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

public interface IAutenticacaoService extends AuthenticationProvider {

	Authentication entradaUnica(String sessionKey);

	Authentication login(AutenticacaoDTO autenticacao);

}
