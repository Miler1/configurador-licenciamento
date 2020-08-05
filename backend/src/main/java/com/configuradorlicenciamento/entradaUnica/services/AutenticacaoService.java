package com.configuradorlicenciamento.entradaUnica.services;

import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.entradaUnica.dtos.AutenticacaoDTO;
import com.configuradorlicenciamento.entradaUnica.interfaces.IAutenticacaoService;
import com.configuradorlicenciamento.seguranca.components.Autenticacao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements IAutenticacaoService {

//	@Autowired
//	IUsuarioService usuarioService;

	@Override
	public Authentication entradaUnica(String sessionKey) {

		var usuarioEntradaUnica = EntradaUnicaWS.ws.searchBySessionKey(sessionKey);

		if(usuarioEntradaUnica == null) {
			throw new ConfiguradorNotFoundException("A chave de sessão do Entrada Única não é valida.");
		}

//		usuarioService.cadastraOuAtualiza(usuarioEntradaUnica);

		usuarioEntradaUnica.sessionKeyEntradaUnica = sessionKey;

		return new Autenticacao(usuarioEntradaUnica);

	}

	@Override
	public Authentication login(AutenticacaoDTO autenticacao) {

		Usuario usuarioEntradaUnica;

		try {
			usuarioEntradaUnica = EntradaUnicaWS.ws.login(autenticacao.getLogin(), autenticacao.getPassword());
		} catch (Exception e){
			throw new ConfiguradorNotFoundException(e.getMessage());
		}

		if(usuarioEntradaUnica == null) {
			throw new ConfiguradorNotFoundException("Usuário não encontrado.");
		}

//		Usuario usuario = usuarioService.cadastraOuAtualiza(usuarioEntradaUnica);

//		usuarioEntradaUnica.id = usuario.getId();

		return new Autenticacao(usuarioEntradaUnica);

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
