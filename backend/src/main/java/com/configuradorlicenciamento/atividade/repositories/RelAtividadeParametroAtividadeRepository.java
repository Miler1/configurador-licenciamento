package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelAtividadeParametroAtividadeRepository extends JpaRepository<RelAtividadeParametroAtividade, Integer> {
}
