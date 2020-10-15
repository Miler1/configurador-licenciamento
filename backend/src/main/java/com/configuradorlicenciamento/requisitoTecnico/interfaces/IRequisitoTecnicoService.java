package com.configuradorlicenciamento.requisitoTecnico.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoCsv;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoDTO;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoEdicaoDTO;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface IRequisitoTecnicoService {

    RequisitoTecnico salvar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO);

    RequisitoTecnico editar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO);

    RequisitoTecnico ativarDesativar(Integer idRequisito);

    Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro);

    List<RequisitoTecnicoCsv> listarDocumentoParaCsv();

    RequisitoTecnicoEdicaoDTO findById(Integer idRequisito);

    List<RequisitoTecnico> findAtivos();

}
