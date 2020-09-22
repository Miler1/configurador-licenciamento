package com.configuradorlicenciamento.tipologia.repositories;

import com.configuradorlicenciamento.tipologia.models.Tipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipologiaRepository extends JpaRepository<Tipologia, Integer>, JpaSpecificationExecutor<Tipologia> {

    Boolean existsByCodigo(String codigo);

    Tipologia findByCodigo(String codigo);

    List<Tipologia> findByAtivo(Boolean ativo);

}