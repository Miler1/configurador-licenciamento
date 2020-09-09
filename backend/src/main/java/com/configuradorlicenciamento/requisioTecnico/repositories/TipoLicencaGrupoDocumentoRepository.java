package com.configuradorlicenciamento.requisioTecnico.repositories;

import com.configuradorlicenciamento.requisioTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisioTecnico.models.TipoLicencaGrupoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoLicencaGrupoDocumentoRepository extends JpaRepository<TipoLicencaGrupoDocumento, Integer>{

    List<TipoLicencaGrupoDocumento> findByRequisitoTecnico(RequisitoTecnico requisitoTecnico);

}
