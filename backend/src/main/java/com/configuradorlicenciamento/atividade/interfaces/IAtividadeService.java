package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAtividadeService {

    Atividade salvar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO);

    Atividade editar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO, Atividade atividade);

    Page<Atividade> listarAtividadesLicenciaveis(Pageable pageable, FiltroPesquisa filtro);
}