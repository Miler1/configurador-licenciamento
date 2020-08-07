package com.configuradorlicenciamento.configuracao.interfaces;

import com.configuradorlicenciamento.configuracao.enums.Acao;
import javax.servlet.http.HttpServletRequest;

public interface IDefaultService {

    Boolean verificaPermissao(HttpServletRequest request, Acao... acoes);
}
