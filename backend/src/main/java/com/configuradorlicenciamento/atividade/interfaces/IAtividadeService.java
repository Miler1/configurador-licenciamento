package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;

public interface IAtividadeService {

    Atividade salvar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO);
}
