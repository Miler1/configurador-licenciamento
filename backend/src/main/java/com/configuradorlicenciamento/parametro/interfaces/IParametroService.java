package com.configuradorlicenciamento.parametro.interfaces;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.models.Parametro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface IParametroService {

   Parametro salvar(HttpServletRequest request, ParametroDTO parametroDTO) throws Exception;

   Page<Parametro> lista(Pageable pageable, FiltroPesquisa filtro);

}
