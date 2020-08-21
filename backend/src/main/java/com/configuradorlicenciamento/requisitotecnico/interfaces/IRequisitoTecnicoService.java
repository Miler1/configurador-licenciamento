package com.configuradorlicenciamento.requisitotecnico.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRequisitoTecnicoService {

    Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro);
}
