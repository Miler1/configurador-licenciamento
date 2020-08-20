package com.configuradorlicenciamento.tipologia.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITipologiaService {

    Tipologia salvar(HttpServletRequest request, TipologiaDTO tipologiaDTO) throws Exception;

    Tipologia editar(HttpServletRequest request, TipologiaDTO tipologiaDTO);

    List<Tipologia> listarTipologia() throws Exception;

    List<TipologiaCsv> listarTipologiaParaCsv() throws Exception;

    Page<Tipologia> lista(Pageable pageable, FiltroPesquisa filtro);

}
