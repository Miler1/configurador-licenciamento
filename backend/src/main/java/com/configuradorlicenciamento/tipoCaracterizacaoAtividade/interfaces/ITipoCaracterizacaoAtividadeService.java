package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import java.util.List;

public interface ITipoCaracterizacaoAtividadeService {

    void salvarAtividadeDispensavel(AtividadeCnaeDTO atividadeCnaeDTO, Atividade atividade);

    void salvarAtividadeLicenciavel(List<AtividadeCnaeDTO> atividadesCnae, Atividade atividade);

    void editarAtividadeLicenciavel(List<AtividadeCnaeDTO> atividadesCnae, Atividade atividade);

    void excluirAtividadeLicenciavel(Atividade atividade);

}