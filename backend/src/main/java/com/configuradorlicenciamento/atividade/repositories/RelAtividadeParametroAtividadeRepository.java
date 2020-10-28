package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import com.configuradorlicenciamento.parametro.models.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelAtividadeParametroAtividadeRepository extends JpaRepository<RelAtividadeParametroAtividade, Integer> {

    List<RelAtividadeParametroAtividade> findByAtividadeAndParametro(Atividade atividade, Parametro parametro);

    List<RelAtividadeParametroAtividade> findByAtividade(Atividade atividade);
}
