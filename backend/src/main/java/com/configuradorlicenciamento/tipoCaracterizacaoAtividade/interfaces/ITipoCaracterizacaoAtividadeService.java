package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITipoCaracterizacaoAtividadeService {

    List<TipoCaracterizacaoAtividade> salvarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO);

    Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro);

    List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv();
}
