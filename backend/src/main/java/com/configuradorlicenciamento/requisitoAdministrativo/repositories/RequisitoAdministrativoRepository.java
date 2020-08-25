package com.configuradorlicenciamento.requisitoAdministrativo.repositories;

import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoAdministrativoRepository extends JpaRepository<RequisitoAdministrativo, Integer>, JpaSpecificationExecutor<RequisitoAdministrativo> {

    boolean existsByDocumentoAndLicenca(Documento documento, Licenca licenca);
}
