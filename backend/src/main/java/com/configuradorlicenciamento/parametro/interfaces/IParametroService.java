package com.configuradorlicenciamento.parametro.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.parametro.dtos.ParametroCsv;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.models.Parametro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IParametroService {

    Parametro salvar(HttpServletRequest request, ParametroDTO parametroDTO);

    Page<Parametro> listar(Pageable pageable, FiltroPesquisa filtro);

    List<ParametroCsv> listarParametrosParaCsv() throws Exception;

    List<Parametro> listarParametros() throws Exception;

    Parametro editar(HttpServletRequest request, ParametroDTO parametroDTO);

    Parametro ativarDesativar(HttpServletRequest request, Integer idParametro);

    List<Parametro> findAtivos();
}