package com.configuradorlicenciamento.resposta.repositories;

import com.configuradorlicenciamento.resposta.models.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Integer>, JpaSpecificationExecutor<Resposta> {

}