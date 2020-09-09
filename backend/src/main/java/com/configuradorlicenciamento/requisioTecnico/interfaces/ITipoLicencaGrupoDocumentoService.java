package com.configuradorlicenciamento.requisioTecnico.interfaces;

import com.configuradorlicenciamento.requisioTecnico.dtos.TipoLicencaGrupoDocumentoDTO;
import com.configuradorlicenciamento.requisioTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisioTecnico.models.TipoLicencaGrupoDocumento;

import java.util.List;

public interface ITipoLicencaGrupoDocumentoService {

    void salvar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico);

    void editar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico);

    List<TipoLicencaGrupoDocumento> findByRequisito(RequisitoTecnico requisitoTecnico);

}
