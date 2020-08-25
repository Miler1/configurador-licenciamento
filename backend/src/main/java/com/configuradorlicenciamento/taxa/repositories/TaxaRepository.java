package com.configuradorlicenciamento.taxa.repositories;

import com.configuradorlicenciamento.taxa.models.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaRepository extends JpaRepository<Taxa, Integer>, JpaSpecificationExecutor<Taxa> {

}
