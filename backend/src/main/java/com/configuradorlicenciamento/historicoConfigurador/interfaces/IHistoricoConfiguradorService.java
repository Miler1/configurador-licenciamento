package com.configuradorlicenciamento.historicoConfigurador.interfaces;

import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;

import javax.servlet.http.HttpServletRequest;

public interface IHistoricoConfiguradorService {

    void salvar(HttpServletRequest request, Integer funcionalidade, Integer idAcaoConfigurador);

    void editar(HttpServletRequest request, Integer funcionalidade, Integer idAcaoConfigurador, String justificativa);

    HistoricoConfigurador findbyId(Integer idTabela);

}