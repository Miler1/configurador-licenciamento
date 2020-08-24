package com.configuradorlicenciamento.requisitotecnico.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoDTO;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;


public interface IRequisitoTecnicoService {

    RequisitoTecnico salvar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO);

    Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro);

}
