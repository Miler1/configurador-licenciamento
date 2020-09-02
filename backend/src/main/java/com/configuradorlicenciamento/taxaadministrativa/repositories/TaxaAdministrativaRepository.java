package com.configuradorlicenciamento.taxaadministrativa.repositories;

import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaAdministrativaRepository extends JpaRepository<TaxaAdministrativa, Integer>, JpaSpecificationExecutor<TaxaAdministrativa> {

    Boolean existsByAno(Integer ano);

    TaxaAdministrativa findByAno(Integer ano);

}
