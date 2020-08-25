package com.configuradorlicenciamento.requisitoadministrativo.repositories;

import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoadministrativo.models.RequisitoAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoAdministrativoRepository extends JpaRepository<RequisitoAdministrativo, Integer>, JpaSpecificationExecutor<RequisitoAdministrativo> {

    Boolean existsByLicencaAndDocumento(Licenca licenca, Documento documento);

}
