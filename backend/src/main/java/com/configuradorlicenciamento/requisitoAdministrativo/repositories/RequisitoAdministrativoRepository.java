package com.configuradorlicenciamento.requisitoAdministrativo.repositories;

import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoAdministrativoRepository extends JpaRepository<RequisitoAdministrativo, Integer>, JpaSpecificationExecutor<RequisitoAdministrativo> {
}
