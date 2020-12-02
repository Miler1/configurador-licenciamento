package com.configuradorlicenciamento.versao;


import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Versao extends DefaultController {

    @Autowired
    ConfigProperties configProp;

    @GetMapping(value = "/versao")
    public Map<String, String> versao() {

        HashMap<String, String> map = new HashMap<>();

        map.put("name", configProp.getConfigValue("spring.application.name"));
        map.put("release", "release");
        map.put("update", "bb");
        map.put("path", "path");
        map.put("spring", SpringVersion.getVersion());
        map.put("java", configProp.getConfigValue("app.java.version"));

        return map;

    }

}