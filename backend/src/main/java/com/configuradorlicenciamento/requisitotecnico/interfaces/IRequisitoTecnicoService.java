package com.configuradorlicenciamento.requisitotecnico.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoCsv;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRequisitoTecnicoService {

    Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro);

    List<RequisitoTecnicoCsv> listarDocumentoParaCsv() throws Exception;
}
