package com.configuradorlicenciamento.historicoConfigurador.repositories;

import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AcaoConfiguradorRepository extends JpaRepository<AcaoConfigurador, Integer>, JpaSpecificationExecutor<AcaoConfigurador> {

}