package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
}
