package com.configuradorlicenciamento.pergunta.repositories;

import com.configuradorlicenciamento.pergunta.models.Pergunta;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Integer>, JpaSpecificationExecutor<Pergunta> {

    List<Pergunta> findByAtivo(Boolean ativo, Sort sort);

}