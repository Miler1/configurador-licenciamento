package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelEdicaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAtividadeDispensavelService {

    List<Atividade> salvarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO);

    Atividade editarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO);

    Atividade ativarDesativar(HttpServletRequest request, Integer idAtividadeDispensavel);

    Page<Atividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro);

    List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv();

    AtividadeDispensavelEdicaoDTO findById(Integer idAtividadeDispensavel);
}
