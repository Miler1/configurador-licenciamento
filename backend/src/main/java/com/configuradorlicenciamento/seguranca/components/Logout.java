package com.configuradorlicenciamento.seguranca.components;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class Logout implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

		HttpSession session = httpServletRequest.getSession();

		if(session != null) {
			session.removeAttribute(GlobalReferences.AUTENTICACAO_KEY_USUARIO);
		}

		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		httpServletResponse.sendRedirect(VariaveisAmbientes.baseUrlFrontend() + GlobalReferences.AUTENTICACAO_PATH_CONFIGURADOR);

	}

}
