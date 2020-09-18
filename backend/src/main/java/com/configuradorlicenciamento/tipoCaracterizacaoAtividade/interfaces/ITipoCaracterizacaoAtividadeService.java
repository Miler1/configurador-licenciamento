package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITipoCaracterizacaoAtividadeService {

    Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro);

    List<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis();

    List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv();
}
