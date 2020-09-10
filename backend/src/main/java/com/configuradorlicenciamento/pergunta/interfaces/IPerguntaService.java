package com.configuradorlicenciamento.pergunta.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface IPerguntaService {

    Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO);

    Page<Pergunta> listar(Pageable pageable, FiltroPesquisa filtro);

}
