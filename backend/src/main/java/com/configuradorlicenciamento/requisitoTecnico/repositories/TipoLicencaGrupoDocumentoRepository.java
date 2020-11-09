package com.configuradorlicenciamento.requisitoTecnico.repositories;

import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.models.TipoLicencaGrupoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoLicencaGrupoDocumentoRepository extends JpaRepository<TipoLicencaGrupoDocumento, Integer> {

    List<TipoLicencaGrupoDocumento> findByRequisitoTecnico(RequisitoTecnico requisitoTecnico);

    List<TipoLicencaGrupoDocumento> findByLicenca(Licenca licenca);

    List<TipoLicencaGrupoDocumento> findByDocumento(Documento documento);

}
