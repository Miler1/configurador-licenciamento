package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.PorteAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PorteAtividadeRepository extends JpaRepository<PorteAtividade, Integer> {

    @Query(value = "SELECT max(codigo) FROM PorteAtividade")
    Integer max();

}
