package com.configuradorlicenciamento.versao;


import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class Versao extends DefaultController {

    @Autowired
    ConfigProperties configProp;

    @GetMapping(value = "/versao")
    public String versao(Model model) {

        model.addAttribute("name", configProp.getConfigValue("spring.application.name"));
        model.addAttribute("release", "release");
        model.addAttribute("update", configProp.getConfigValue("server.update"));
        model.addAttribute("path", "path");
        model.addAttribute("spring", SpringVersion.getVersion());
        model.addAttribute("java", configProp.getConfigValue("app.java.version"));

        return "versao";

    }

}