package com.configuradorlicenciamento.entradaUnica.services;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import lombok.extern.slf4j.Slf4j;
import br.ufla.lemaf.httpClient.MGAHttpClient;
import br.ufla.lemaf.httpClient.WSHttpClientRequest;
import br.ufla.lemaf.httpClient.WSHttpResponse;
import br.ufla.lemaf.services.CadastroUnificadoPessoaService;
import org.springframework.http.HttpStatus;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class EntradaUnicaWS extends CadastroUnificadoPessoaService {

	private static final int TMR_INIT_CONNECTION = 5000; //5s
	private static final int TMR_PERIOD_CONNECTION = 30000; //30s

	private static final String LOG_PREFIX = "[GESTAO-ACESSO-WS]";

	private static TimerTask taskTryConnection = new TimerTask() {
		@Override
		public void run() {
		try {

			tryConnection();
			cancel();
			log.info(LOG_PREFIX + " - conexão estabelecida com sucesso.");

		} catch (Exception e) {
			ws = null;
			log.error(LOG_PREFIX + " - erro ao tentar estabelecer a conexão.");
		}
		}
	};

	public static EntradaUnicaWS ws = null;

	static {

		try {
			tryConnection();
		} catch (Exception ex) {

			ex.printStackTrace();
			ws = null;

			// Inicia o timer para renovar a conexão com o Entrada Única
			new Timer().schedule(taskTryConnection, TMR_INIT_CONNECTION, TMR_PERIOD_CONNECTION);
		}
	}

	private static synchronized void tryConnection() {

		ws = new EntradaUnicaWS(VariaveisAmbientes.entradaUnicaClientId(),
				VariaveisAmbientes.entradaUnicaClientSecret(),
				VariaveisAmbientes.entradaUnicaPortalSeguracao(),
				VariaveisAmbientes.entradaUnicaCadastroUnificado());

	}

	public EntradaUnicaWS(String clientId, String clientSecret, String urlPortal, String urlCadastro) {
		super(clientId, clientSecret, urlPortal, urlCadastro);
	}

	public static Boolean entradaUnicaDisponivel() {

		Boolean statusPortalSeguranca = statusAplicacao(VariaveisAmbientes.entradaUnicaPortalSeguracao() + "/versao");

		if(!statusPortalSeguranca) {
			return false;
		}

		return statusAplicacao(VariaveisAmbientes.entradaUnicaCadastroUnificado() + "/versao");

	}

	private static Boolean statusAplicacao(String urlAplicacao) {

		try {

			WSHttpClientRequest request = new WSHttpClientRequest(MGAHttpClient.TPRequest.GET, urlAplicacao, 10000);
			WSHttpResponse response = request.execute();

			return response.getStatus() == HttpStatus.OK.value();

		} catch(Exception e) {
			return false;
		}

	}

	public static String findSetores() {

		String urlFindSetor = VariaveisAmbientes.entradaUnicaCadastroUnificado() + "/public/setores/buscaSetores";

		try {

			WSHttpClientRequest request = new WSHttpClientRequest(MGAHttpClient.TPRequest.GET, urlFindSetor, 10000);

			WSHttpResponse response = request.execute();

			return response.getBody();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return "";

	}

	public boolean verificarCadastroUsuario(String login) {

		String urlVerificaCadastro = VariaveisAmbientes.entradaUnicaPortalSeguracao() + "/usuario/verificarCadastro/" + login;

		try {

			WSHttpClientRequest request = new WSHttpClientRequest(MGAHttpClient.TPRequest.GET, urlVerificaCadastro, 10000);

			WSHttpResponse response = request.execute();

			return response.getBody().equals("true");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return false;

	}
}
