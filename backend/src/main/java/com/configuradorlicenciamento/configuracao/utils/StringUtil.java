package com.configuradorlicenciamento.configuracao.utils;

import java.text.Normalizer;
import java.util.ArrayList;

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

	public static String removeAccents(String str) {

		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");

		return str;

	}

	public static ArrayList<String> preposicoes() {

        String[] array = {" da ", " de ", " do ", " a ", " e ", " o "};
        ArrayList<String> preposicoes = new ArrayList<>();

        for (String item : array){
            preposicoes.add(item);
        }

	    return preposicoes;
    }

}
