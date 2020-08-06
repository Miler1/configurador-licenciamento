package com.configuradorlicenciamento.atividadeCnae.repositories;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeCnaeRepository  extends JpaRepository<AtividadeCnae, Integer> {

}