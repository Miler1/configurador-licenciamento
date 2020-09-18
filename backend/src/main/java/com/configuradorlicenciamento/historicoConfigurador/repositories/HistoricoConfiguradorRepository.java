package com.configuradorlicenciamento.historicoConfigurador.repositories;

import com.configuradorlicenciamento.historicoConfigurador.models.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistoricoConfiguradorRepository extends JpaRepository<Historico, Integer>, JpaSpecificationExecutor<Historico> {
}
