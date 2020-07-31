package com.configuradorlicenciamento.configuracao.utils;

public class StringUtil {

	public static Boolean vaziaOuNula(String valor) {

		if(valor == null || valor.isEmpty() || valor.isBlank()) {
			return true;
		}

		return false;

	}

	public static String formataCEP(String cep) {

		cep = removeCaracteresEspeciais(cep);

		if(cep != null && !cep.isBlank()) {
			return cep.replaceFirst("(\\d{2})(\\d{3})(\\d+)", "$1.$2-$3");
		}

		return cep;

	}

	public static String removeCaracteresEspeciais(String valor) {

		if(valor != null && !valor.isBlank()) {
			valor = valor.replaceAll("[^a-zA-Z0-9\\s+]", "");
		}

		return valor;

	}

	public static String apenasNumeros(String valor) {

		if(valor != null && !valor.isBlank()) {

			valor = removeCaracteresEspeciais(valor);
			valor = valor.replaceAll("[a-zA-Z]+", "");

		}

		return valor;

	}

}
