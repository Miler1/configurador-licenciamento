package com.configuradorlicenciamento.historicoConfigurador.interfaces;

import com.configuradorlicenciamento.historicoConfigurador.models.Historico;

import javax.servlet.http.HttpServletRequest;

public interface IHistoricoConfiguradorService {

    void salvar(HttpServletRequest request, Integer idTabela, Integer funcionalidade, Integer idAcaoConfigurador);

    void editar(HttpServletRequest request, Integer idTabela, String justificativa, Integer funcionalidade, Integer idAcaoConfigurador);

    Historico findbyId(Integer idTabela);

}