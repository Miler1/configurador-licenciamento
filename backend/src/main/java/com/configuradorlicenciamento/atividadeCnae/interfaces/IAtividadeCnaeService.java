package com.configuradorlicenciamento.atividadeCnae.interfaces;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public interface IAtividadeCnaeService {

    AtividadeCnae salvar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO);

    AtividadeCnae editar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO);

    List<AtividadeCnae> listarCnaes() throws Exception;

    List<AtividadeCnaeCsv> listarCnaesParaCsv() throws Exception;

    Page<AtividadeCnae> listar(Pageable pageable, FiltroPesquisa filtro);

    List<AtividadeCnae> findAtivosNaoVinculados();

}
