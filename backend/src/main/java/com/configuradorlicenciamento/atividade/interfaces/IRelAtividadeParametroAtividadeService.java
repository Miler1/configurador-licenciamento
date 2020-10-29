package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.dtos.PorteAtividadeDTO;
import com.configuradorlicenciamento.atividade.models.Atividade;

public interface IRelAtividadeParametroAtividadeService {

    void salvar(Atividade atividade, PorteAtividadeDTO parametro);

    void editar(Atividade atividade, PorteAtividadeDTO parametro);

    void excluir(Atividade atividade);
}
