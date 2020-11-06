package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoCaracterizacaoAtividadeRepository extends JpaRepository<TipoCaracterizacaoAtividade, Integer>, JpaSpecificationExecutor<TipoCaracterizacaoAtividade> {

    List<TipoCaracterizacaoAtividade> findByAtividade(Atividade atividade);

    List<TipoCaracterizacaoAtividade> findByAtividadeCnae(AtividadeCnae atividadeCnae);

}
