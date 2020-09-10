package com.configuradorlicenciamento.pergunta.interfaces;

import com.configuradorlicenciamento.pergunta.dtos.PerguntaCsv;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPerguntaService {

    Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO);

    List<PerguntaCsv> listarPerguntaParaCsv() throws Exception;
}
