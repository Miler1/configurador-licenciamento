package com.configuradorlicenciamento.licenca.repositories;

import com.configuradorlicenciamento.licenca.models.Licenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LicencaRepository extends JpaRepository<Licenca, Integer>, JpaSpecificationExecutor<Licenca> {

}
