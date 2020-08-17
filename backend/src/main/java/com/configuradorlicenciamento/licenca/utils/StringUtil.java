package com.configuradorlicenciamento.licenca.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    private static Map<String, String> LABEL = new HashMap<>(){

    };

    static {
        LABEL.put("ATUALIZACAO", "Atualização");
        LABEL.put("CADASTRO", "Cadastro");
        LABEL.put("DISPENSA", "Dispensa");
        LABEL.put("RENOVACAO", "Renovação");
        LABEL.put("SOLICITACAO", "Solicitação");
    }


    public static String valueOfLabel(String finalidade) {

        return LABEL.get(finalidade);
    }





}
