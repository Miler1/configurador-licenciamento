package com.configuradorlicenciamento.pergunta.interfaces;

import com.configuradorlicenciamento.pergunta.dtos.PerguntaCsv;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPerguntaService {

    Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO);

    Pergunta editar(HttpServletRequest request, PerguntaDTO perguntaDTO);

    List<PerguntaCsv> listarPerguntaParaCsv();

    Page<Pergunta> listar(Pageable pageable, FiltroPesquisa filtro);

}
