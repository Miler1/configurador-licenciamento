package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.models.Atividade;

import javax.servlet.http.HttpServletRequest;

public interface IRascunhoService {

    Atividade finalizarCadastro(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Atividade salvar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Atividade editar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    void excluir(HttpServletRequest request, Integer idAtividade) throws Exception;

}