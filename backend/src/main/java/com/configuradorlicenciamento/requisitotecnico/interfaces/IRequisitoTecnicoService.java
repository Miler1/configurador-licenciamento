package com.configuradorlicenciamento.requisitotecnico.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoDTO;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoCsv;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoEdicaoDTO;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface IRequisitoTecnicoService {

    RequisitoTecnico salvar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO);

    RequisitoTecnico editar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO);

    RequisitoTecnico ativarDesativar(Integer idRequisito);

    Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro);

    List<RequisitoTecnicoCsv> listarDocumentoParaCsv() throws Exception;

    RequisitoTecnicoEdicaoDTO findById(Integer idRequisito);
}
