package com.configuradorlicenciamento.historicoConfigurador.repositories;

import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionalidadeConfiguradorRepository extends JpaRepository<FuncionalidadeConfigurador, Integer>, JpaSpecificationExecutor<FuncionalidadeConfigurador> {

    FuncionalidadeConfigurador findByTipo(String tipo);

}