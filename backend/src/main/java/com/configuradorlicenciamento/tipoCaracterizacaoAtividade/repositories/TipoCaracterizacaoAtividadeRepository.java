package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories;

import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCaracterizacaoAtividadeRepository extends JpaRepository<TipoCaracterizacaoAtividade, Integer>, JpaSpecificationExecutor<TipoCaracterizacaoAtividade> {
}
