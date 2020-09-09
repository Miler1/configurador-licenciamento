package com.configuradorlicenciamento.pergunta.interfaces;

import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;

import javax.servlet.http.HttpServletRequest;

public interface IPerguntaService {

    Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO);

}
