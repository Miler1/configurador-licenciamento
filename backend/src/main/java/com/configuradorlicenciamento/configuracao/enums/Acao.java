package com.configuradorlicenciamento.configuracao.enums;

import lombok.Getter;

@Getter
public enum Acao {

    GERENCIAR_LICENCIAMENTO("GERENCIAR_LICENCIAMENTO");

    private String codigo;

    Acao(String codigo) {

        this.codigo = codigo;
    }

}
