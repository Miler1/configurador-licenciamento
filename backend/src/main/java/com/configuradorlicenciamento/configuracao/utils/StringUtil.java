package com.configuradorlicenciamento.configuracao.utils;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StringUtil {

    private StringUtil(){}

    public static Boolean vaziaOuNula(String valor) {

        return valor == null || valor.isEmpty() || valor.isBlank();

    }

    public static String formataCEP(String cep) {

        cep = removeCaracteresEspeciais(cep);

        if (cep != null && !cep.isBlank()) {
            return cep.replaceFirst("(\\d{2})(\\d{3})(\\d+)", "$1.$2-$3");
        }

        return cep;

    }

    public static String removeCaracteresEspeciais(String valor) {

        if (valor != null && !valor.isBlank()) {
            valor = valor.replaceAll("[^a-zA-Z0-9\\s+]", "");
        }

        return valor;

    }

    public static String apenasNumeros(String valor) {

        if (valor != null && !valor.isBlank()) {

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

    public static List<String> preposicoes() {

        String[] array = {" da ", " de ", " do ", " a ", " e ", " o ", " para "};
        ArrayList<String> preposicoes = new ArrayList<>();

        Collections.addAll(preposicoes, array);

        return preposicoes;

    }

    /**
     * O método toma uma string como parâmetro e remove espaços sobressalentes.
     * Sejam eles repetidos dentro da frase, ou no início e fim
     *
     * @param string A string a ser tratada
     * @return A string tratada sem os espaços desnecessários
     */
    public static String tratarEspacos(String string) {

        String referencia = string;
        String tratada = string;

        boolean complete = false;

        while (!complete) {

            tratada = tratada.replace("  ", " ");

            if (tratada.equals(referencia)) {
                complete = true;
            } else {
                referencia = tratada;
            }
        }

        if (tratada.charAt(tratada.length() - 1) == ' ') {
            tratada = tratada.substring(0, tratada.length() - 1);
        }

        if (tratada.charAt(0) == ' ') {
            tratada = tratada.substring(1, tratada.length());
        }

        return tratada;

    }

    public static String formatarParaMoeda(Float valor) {

        String valorFormatado = "-";

        if (valor != 0) {

            Locale locale = new Locale("pt", "BR");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

            valorFormatado = currencyFormatter.format(valor);

        }

        return valorFormatado;

    }

}