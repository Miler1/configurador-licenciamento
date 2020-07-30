package com.configuradorlicenciamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableCaching
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguradorlicenciamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfiguradorlicenciamentoApplication.class, args);
    }

}
