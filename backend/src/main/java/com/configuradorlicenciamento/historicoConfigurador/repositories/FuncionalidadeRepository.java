package com.configuradorlicenciamento.historicoConfigurador.repositories;

import com.configuradorlicenciamento.historicoConfigurador.models.Funcionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Integer>, JpaSpecificationExecutor<Funcionalidade> {
}
