package com.configuradorlicenciamento.licenca.models;

import java.util.HashMap;
import java.util.Map;

public enum FinalidadeEnum {

    ATUALIZACAO("ATUALIZACAO", "Atualização"),
    CADASTRO("CADASTRO", "Cadastro"),
    DISPENSA("DISPENSA", "Dispensa"),
    RENOVACAO("RENOVACAO", "Renovação"),
    SOLICITACAO("SOLICITACAO", "Solicitação");

    private static final Map<String, String> LABEL = new HashMap<>();

    public final String key;
    public final String label;

    FinalidadeEnum(String key, String label) {
        this.key = key;
        this.label = label;
    }

    static {
        for (FinalidadeEnum finalidade: values()) {
            LABEL.put(finalidade.key, finalidade.label);
        }
    }

    public static String valueOfLabel(String key) {
        return LABEL.get(key);
    }

}
