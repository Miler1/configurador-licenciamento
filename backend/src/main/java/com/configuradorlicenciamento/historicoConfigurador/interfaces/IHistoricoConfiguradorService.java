package com.configuradorlicenciamento.historicoConfigurador.interfaces;

import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IHistoricoConfiguradorService {

    void salvar(HttpServletRequest request, Integer idTabela, String funcionalidade, String acao);

    void editar(HttpServletRequest request, Integer idTabela, String funcionalidade, String acao, String justificativa);

    List<HistoricoConfigurador> buscarHistoricoItem(String funcionalidade, Integer idItem);

}