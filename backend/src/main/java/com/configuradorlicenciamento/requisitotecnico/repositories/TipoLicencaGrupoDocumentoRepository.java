package com.configuradorlicenciamento.requisitotecnico.repositories;

import com.configuradorlicenciamento.requisitotecnico.models.TipoLicencaGrupoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLicencaGrupoDocumentoRepository extends JpaRepository<TipoLicencaGrupoDocumento, Integer>{
}
