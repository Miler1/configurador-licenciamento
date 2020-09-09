package com.configuradorlicenciamento.taxaLicenciamento.repositories;

import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaLicenciamentoRepository extends JpaRepository<TaxaLicenciamento, Integer>, JpaSpecificationExecutor<TaxaLicenciamento> {

}
