package com.configuradorlicenciamento.parametro.repositories;

import com.configuradorlicenciamento.parametro.models.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer>, JpaSpecificationExecutor<Parametro> {

    Boolean existsByCodigo(String codigo);

    Parametro findByCodigo(String codigo);

}