package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories;

import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.RelAtividadePergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelAtividadePerguntaRepository extends JpaRepository<RelAtividadePergunta, Integer> {
}
