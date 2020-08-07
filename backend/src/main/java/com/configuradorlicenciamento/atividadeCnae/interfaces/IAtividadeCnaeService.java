package com.configuradorlicenciamento.atividadeCnae.interfaces;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;

import javax.servlet.http.HttpServletRequest;

public interface IAtividadeCnaeService {

    AtividadeCnae salvar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO) throws Exception;

}
