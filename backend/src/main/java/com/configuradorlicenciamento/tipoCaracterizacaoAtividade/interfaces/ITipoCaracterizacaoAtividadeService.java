package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelEdicaoDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITipoCaracterizacaoAtividadeService {

    List<TipoCaracterizacaoAtividade> salvarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO);

    void salvarAtividadeLicenciavel(List<AtividadeCnaeDTO> atividadesCnae, Atividade atividade);

    TipoCaracterizacaoAtividade editar(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO);

    TipoCaracterizacaoAtividade ativarDesativar(Integer idAtividadeDispensavel);

    Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro);

    List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv();

    AtividadeDispensavelEdicaoDTO findById(Integer idAtividadeDispensavel);

}