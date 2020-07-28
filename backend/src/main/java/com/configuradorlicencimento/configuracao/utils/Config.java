package com.configuradorlicencimento.configuracao.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Component
@PropertySource("classpath:application.properties")
public class Config {

    private Environment env;

    public static String PATH_ARQUIVO;
    public static String PATH_ARQUIVO_ORDEM_SERVICO;
    public static String PATH_ARQUIVO_INCONSISTENCIAS;

    @Autowired
    public Config(Environment env) {

        this.env = env;
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        PATH_ARQUIVO = env.getProperty("path.arquivos.salvar");
        PATH_ARQUIVO_ORDEM_SERVICO = PATH_ARQUIVO + "/ordem-servico";
        PATH_ARQUIVO_INCONSISTENCIAS = PATH_ARQUIVO + "/inconsistencias-analise";

    }

}
