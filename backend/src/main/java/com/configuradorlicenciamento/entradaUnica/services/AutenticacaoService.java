package com.configuradorlicenciamento.entradaUnica.services;

import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.entradaUnica.dtos.AutenticacaoDTO;
import com.configuradorlicenciamento.entradaUnica.interfaces.IAutenticacaoService;
import com.configuradorlicenciamento.seguranca.components.Autenticacao;
import com.configuradorlicenciamento.usuariolicenciamento.interfaces.IUsuarioLicenciamentoService;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AutenticacaoService implements IAutenticacaoService {

	@Autowired
	IUsuarioLicenciamentoService usuarioLicenciamentoService;

	@Override
	public Authentication entradaUnica(HttpServletRequest request, String sessionKey) {

		var usuarioEntradaUnica = EntradaUnicaWS.ws.searchBySessionKey(sessionKey);

		if(usuarioEntradaUnica == null) {
			throw new ConfiguradorNotFoundException("A chave de sessão do Entrada Única não é valida.");
		}

		usuarioLicenciamentoService.cadastraOuAtualiza(usuarioEntradaUnica);

		usuarioEntradaUnica.sessionKeyEntradaUnica = sessionKey;

		request.getSession().setAttribute("sessionKeyEntradaUnica", usuarioEntradaUnica.sessionKeyEntradaUnica);
		request.getSession().setAttribute("login", usuarioEntradaUnica.login);

		return new Autenticacao(usuarioEntradaUnica);

	}

	@Override
	public Authentication login(HttpServletRequest request, AutenticacaoDTO autenticacao) {

		Usuario usuarioEntradaUnica;

		try {
			this.verificarCadastro(autenticacao.getLogin());
			usuarioEntradaUnica = EntradaUnicaWS.ws.login(autenticacao.getLogin(), autenticacao.getPassword());
		} catch (Exception e){
			throw new ConfiguradorNotFoundException(e.getMessage());
		}

		if(usuarioEntradaUnica == null) {
			throw new ConfiguradorNotFoundException("Usuário não encontrado.");
		}

		UsuarioLicenciamento usuario = usuarioLicenciamentoService.cadastraOuAtualiza(usuarioEntradaUnica);

		usuarioEntradaUnica.id = usuario.getId();

		//Como existe só um perfil pro módulo, vamos setar ele aqui.
		usuarioEntradaUnica.perfilSelecionado = usuarioEntradaUnica.perfis.get(0);

		request.getSession().setAttribute("sessionKeyEntradaUnica", usuarioEntradaUnica.sessionKeyEntradaUnica);
		request.getSession().setAttribute("login", usuarioEntradaUnica.login);

		return new Autenticacao(usuarioEntradaUnica);

	}

	private void verificarCadastro(String login) throws Exception {
		login = login.replace(".", "").replace("-", "").replace("/", "");
		boolean cadastroRealizado = EntradaUnicaWS.ws.verificarCadastroUsuario(login);
		if (!cadastroRealizado) {
			String urlCadastroUnificado = VariaveisAmbientes.entradaUnicaCadastroUnificado();
			throw new Exception("Usuário não encontrado, favor finalizar o cadastro em: " + urlCadastroUnificado);
		}
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return false;
	}
}
