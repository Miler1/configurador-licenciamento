package com.configuradorlicenciamento.historicoConfigurador.repositories;

import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoConfiguradorRepository extends JpaRepository<HistoricoConfigurador, Integer>, JpaSpecificationExecutor<HistoricoConfigurador> {

    List<HistoricoConfigurador> findByFuncionalidadeAndIdItemOrderByDataAcaoDesc(
            FuncionalidadeConfigurador funcionalidade, Integer idItem);

    List<HistoricoConfigurador> findByFuncionalidadeAndAcaoAndIdItemOrderByDataAcaoDesc(
            FuncionalidadeConfigurador funcionalidade, AcaoConfigurador acao, Integer idItem);

}