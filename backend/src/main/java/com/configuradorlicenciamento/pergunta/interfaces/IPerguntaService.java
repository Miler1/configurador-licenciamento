package com.configuradorlicenciamento.pergunta.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaCsv;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import com.configuradorlicenciamento.resposta.models.Resposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPerguntaService {

    Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO);

    Pergunta editar(HttpServletRequest request, PerguntaDTO perguntaDTO);

    Pergunta ativarDesativar(HttpServletRequest request, Integer idPergunta);

    List<PerguntaCsv> listarPerguntaParaCsv();

    List<Pergunta> listarPerguntas();

    Page<Pergunta> listar(Pageable pageable, FiltroPesquisa filtro);

    List<Resposta> getRespostas (HttpServletRequest request, List<RespostaDTO> respostas);

}
