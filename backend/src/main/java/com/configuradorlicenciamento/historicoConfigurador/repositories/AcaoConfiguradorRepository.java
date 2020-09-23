package com.configuradorlicenciamento.historicoConfigurador.repositories;

import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaoConfiguradorRepository extends JpaRepository<AcaoConfigurador, Integer>, JpaSpecificationExecutor<AcaoConfigurador> {

    AcaoConfigurador findByAcao(String acao);

}