package com.configuradorlicenciamento.atividadeCnae.interfaces;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;

import java.util.List;

public interface IAtividadeCnaeService {

    AtividadeCnae salvar(AtividadeCnaeDTO atividadeCnaeDTO) throws Exception;
    List<AtividadeCnae> listarCnaes() throws Exception;
    List<AtividadeCnaeCsv> listarCnaesParaCsv() throws Exception;

}
