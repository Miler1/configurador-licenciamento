package com.configuradorlicenciamento.taxaLicenciamento.repositories;

import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxaLicenciamentoRepository extends JpaRepository<TaxaLicenciamento, Integer>, JpaSpecificationExecutor<TaxaLicenciamento> {

    List<TaxaLicenciamento> findByCodigo(CodigoTaxaLicenciamento codigoTaxaLicenciamento);


}
