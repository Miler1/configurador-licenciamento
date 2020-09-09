package com.configuradorlicenciamento.requisitoTecnico.interfaces;

import com.configuradorlicenciamento.requisitoTecnico.dtos.TipoLicencaGrupoDocumentoDTO;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.models.TipoLicencaGrupoDocumento;

import java.util.List;

public interface ITipoLicencaGrupoDocumentoService {

    void salvar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico);

    void editar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico);

    List<TipoLicencaGrupoDocumento> findByRequisito(RequisitoTecnico requisitoTecnico);

}
