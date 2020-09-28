package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;

import java.util.List;

public interface IRelAtividadePerguntaService {

    void salvar(Atividade atividade, List<PerguntaDTO> perguntas);

    void editar(Atividade atividade, List<PerguntaDTO> perguntas);

}