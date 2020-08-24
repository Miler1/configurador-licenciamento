package com.configuradorlicenciamento.requisitotecnico.interfaces;

import com.configuradorlicenciamento.requisitotecnico.dtos.TipoLicencaGrupoDocumentoDTO;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import java.util.List;

public interface ITipoLicencaGrupoDocumentoService {
    void salvar(List<TipoLicencaGrupoDocumentoDTO> listTipoLicencaGrupoDocumentoDTO, RequisitoTecnico requisitoTecnico);
}
