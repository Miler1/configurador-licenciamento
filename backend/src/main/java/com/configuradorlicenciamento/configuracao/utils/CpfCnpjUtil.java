package com.configuradorlicenciamento.configuracao.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.InputMismatchException;

public class CpfCnpjUtil {

	public static Boolean cpfCnpjValido(String cpfCnpj) {

		cpfCnpj = desformataCpfCnpj(cpfCnpj);

		if(cpfCnpj.length() > 11) {
			return isCnpj(cpfCnpj);
		} else {
			return isCpf(cpfCnpj);
		}

	}

	public static String desformataCpfCnpj(String cpfCnpj) {

		if(cpfCnpj != null && !StringUtils.isBlank(cpfCnpj)) {
			return cpfCnpj.replaceAll("[^a-zA-Z0-9\\s+]", "");
		}

		return null;

	}

	/**
	 * Realiza a validação do CPF.
	 *
	 * @param cpf número de CPF a ser validado pode ser passado no formado 999.999.999-99 ou 99999999999
	 * @return true se o CPF é válido e false se não é válido
	 */
	private static Boolean isCpf(String cpf) {

		cpf = desformataCpfCnpj(cpf);

		try {
			Long.parseLong(cpf);
		} catch(NumberFormatException e) {
			return false;
		}

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);

	}

	/**
	 * Realiza a validação de um cnpj
	 *
	 * @param cnpj String - o CNPJ pode ser passado no formato 99.999.999/9999-99 ou 99999999999999
	 * @return boolean
	 */
	private static Boolean isCnpj(String cnpj) {

		cnpj = desformataCpfCnpj(cnpj);

		try {
			Long.parseLong(cnpj);
		} catch(NumberFormatException e) {
			return false;
		}

		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
				|| cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
				|| cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
				|| cnpj.equals("88888888888888") || cnpj.equals("99999999999999") || (cnpj.length() != 14)) {

			return (false);
		}

		char dig13, dig14;
		int sm, i, r, num, peso; // "try" - protege o código para eventuais
		// erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;

			for (i = 11; i >= 0; i--) {

				// converte o i-ésimo caractere do CNPJ em um número: // por
				// exemplo, transforma o caractere '0' no inteiro 0 // (48 eh a
				// posição de '0' na tabela ASCII)
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;

				if (peso == 10) {
					peso = 2;
				}
			}

			r = sm % 11;

			if((r == 0) || (r == 1)) {
				dig13 = '0';
			} else {
				dig13 = (char) ((11 - r) + 48);
			}

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;

			if ((r == 0) || (r == 1)) {
				dig14 = '0';
			} else {
				dig14 = (char) ((11 - r) + 48);
			}

			// Verifica se os dígitos calculados conferem com os dígitos informados.
			if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
				return (true);
			} else {
				return (false);
			}

		} catch(InputMismatchException erro) {
			return (false);
		}
	}

	public static String formataCpfCnpj(String cpfCnpj) {

		cpfCnpj = desformataCpfCnpj(cpfCnpj);

		if(cpfCnpj.length() == 11) {
			cpfCnpj = cpfCnpj.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3-$4");
		} else {
			cpfCnpj = cpfCnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d+)", "$1.$2.$3/$4-$5");
		}

		return cpfCnpj;

	}

}