package com.configuradorlicenciamento.requisitotecnico.interfaces;

import com.configuradorlicenciamento.requisitotecnico.dtos.TipoLicencaGrupoDocumentoDTO;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitotecnico.models.TipoLicencaGrupoDocumento;

import java.util.List;

public interface ITipoLicencaGrupoDocumentoService {
    void salvar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico);

    void editar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico);

    List<TipoLicencaGrupoDocumento> findByRequisito(RequisitoTecnico requisitoTecnico);
}
