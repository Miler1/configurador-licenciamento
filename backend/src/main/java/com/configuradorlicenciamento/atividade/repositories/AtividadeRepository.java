package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Integer> , JpaSpecificationExecutor<Atividade> {

    Boolean existsByCodigo(String codigo);

    Atividade findByCodigoAndItemAntigo(String codigo, Boolean itemAntigo);

}