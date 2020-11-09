package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadePergunta;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelAtividadePerguntaRepository extends JpaRepository<RelAtividadePergunta, Integer> {

    List<RelAtividadePergunta> findByAtividade(Atividade atividade);

    List<RelAtividadePergunta> findByPergunta(Pergunta pergunta);

}
