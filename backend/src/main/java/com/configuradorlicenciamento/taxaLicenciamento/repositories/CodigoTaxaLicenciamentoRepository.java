package com.configuradorlicenciamento.taxaLicenciamento.repositories;

import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodigoTaxaLicenciamentoRepository extends JpaRepository<CodigoTaxaLicenciamento, Integer>, JpaSpecificationExecutor<CodigoTaxaLicenciamento> {

    Boolean existsByCodigo(String codigo);

    CodigoTaxaLicenciamento findByCodigo(String codigo);

    List<CodigoTaxaLicenciamento> findByAtivo (Boolean ativo);

}