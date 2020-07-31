package com.configuradorlicenciamento.configuracao.utils;

import java.text.SimpleDateFormat;

public class GlobalReferences {

    public static final String ESQUEMA = "licenciamento";

    public static SimpleDateFormat DATA_FORMATO_BR_SIMPLES = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat DATA_FORMATO_BR_COM_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static SimpleDateFormat DATA_FORMATO_BR_COMPLETO = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat DATA_FORMATO_EUA_SIMPLES = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DATA_FORMATO_EUA_COM_HORA = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat DATA_FORMATO_EUA_COMPLETO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /** AUTENTICACAO*/
    public static final String AUTENTICACAO_KEY_USUARIO = "KEY_USUARIO_LOGADO";
    public static final String AUTENTICACAO_PATH_CONFIGURADOR = "/login";

}
