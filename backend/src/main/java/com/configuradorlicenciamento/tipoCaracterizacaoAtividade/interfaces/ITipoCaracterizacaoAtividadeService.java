package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITipoCaracterizacaoAtividadeService {

    Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro);
}
