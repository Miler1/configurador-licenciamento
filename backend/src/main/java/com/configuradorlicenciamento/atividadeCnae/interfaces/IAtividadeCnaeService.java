package com.configuradorlicenciamento.atividadeCnae.interfaces;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;

public interface IAtividadeCnaeService {

    AtividadeCnae salvar(AtividadeCnaeDTO atividadeCnaeDTO) throws Exception;

}
