package com.configuradorlicenciamento.taxaLicenciamento.repositories;

import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CodigoTaxaLicenciamentoRepository extends JpaRepository<CodigoTaxaLicenciamento, Integer>, JpaSpecificationExecutor<CodigoTaxaLicenciamento> {

    Boolean existsByCodigo(String codigo);

    CodigoTaxaLicenciamento findByCodigo(String codigo);

}